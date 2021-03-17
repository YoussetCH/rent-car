package com.yousset.rentcar.config;

import com.yousset.rentcar.config.filter.APIKeyAuthFilter;
import com.yousset.rentcar.config.filter.TokenAuthenticationFilter;
import com.yousset.rentcar.config.security.RestAuthenticationEntryPoint;
import com.yousset.rentcar.config.security.TokenProvider;
import com.yousset.rentcar.controllers.MainController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.GenericFilterBean;

import java.util.Arrays;
import java.util.Collections;

/**
 * Global security configuration
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${xclient.header:rent}")
    private String xClientHeader;

    @Value("${xclient.header_value:yousset}")
    private String xClientHeaderValue;

    @Value("${xclient.enable:false}")
    private boolean xClientEnable;

    @Value("${jwt.filter.enable:false}")
    private boolean jwtEnable;

    @Autowired
    private TokenProvider tokenProvider;

    /**
     * URL paths to consider when working on interceptors or security configurations
     */
    public static final String[] ALLOWED_PATH_PATTERNS = {
            "/resources/**",
            "/static/**",
            "/js/**",
            "/css/**",
            "/scss/**",
            "/img/**",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/health-check/**",
            "/h2/**",
            "/"
    };

    @Autowired
    public WebSecurityConfig() {
    }

    @Override
    public void configure(WebSecurity web) {
        /* Ignoring static resources typically used by swagger */
        web.ignoring().antMatchers(HttpMethod.OPTIONS).antMatchers(ALLOWED_PATH_PATTERNS);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry exp = http
                .cors()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(new RestAuthenticationEntryPoint())
                .and()
                // no sessions
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(MainController.ENDPOINT, "/actuator/**", "/login", "/register", "/graphiql", "/graphql")
                .permitAll()
                .antMatchers(HttpMethod.OPTIONS)
                .permitAll()//allow CORS option calls
                .antMatchers(ALLOWED_PATH_PATTERNS)
                .permitAll();


        if (xClientEnable && !jwtEnable) {
            APIKeyAuthFilter filter = new APIKeyAuthFilter(xClientHeader);
            filter.setAuthenticationManager(authentication -> {
                String principal = (String) authentication.getPrincipal();
                if (!xClientHeaderValue.equals(principal)) {
                    throw new BadCredentialsException("The API key was not found or not the expected value.");
                }
                authentication.setAuthenticated(true);
                return authentication;
            });
            applyFilter(exp, filter);
        }

        if (jwtEnable) {
            TokenAuthenticationFilter filterJwt = new TokenAuthenticationFilter(tokenProvider, xClientEnable ? xClientHeader : null, xClientEnable ? xClientHeaderValue : null);
            applyFilter(exp, filterJwt);
        }
        exp.and().httpBasic();
    }

    private void applyFilter(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry exp, GenericFilterBean filter) throws Exception {
        addFilter(exp, filter).authorizeRequests().antMatchers(HttpMethod.POST).authenticated();

        addFilter(exp, filter).authorizeRequests().antMatchers(HttpMethod.PUT).authenticated();

        addFilter(exp, filter).authorizeRequests().antMatchers(HttpMethod.DELETE).authenticated();

        addFilter(exp, filter).authorizeRequests().antMatchers(HttpMethod.GET, "/api/users/**", "/api/reserve/**").authenticated();
    }

    private HttpSecurity addFilter(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry exp, GenericFilterBean filter) {
        if (filter instanceof TokenAuthenticationFilter) {
            return exp.and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
        } else {
            return exp.and().addFilter(filter);
        }
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList(HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(), HttpMethod.DELETE.name(), HttpMethod.OPTIONS.name()));
        configuration.setAllowedHeaders(Arrays.asList("X-Requested-With", "Content-Type", "X-Codingpedia", "Authorization", "localTime", xClientHeader));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
