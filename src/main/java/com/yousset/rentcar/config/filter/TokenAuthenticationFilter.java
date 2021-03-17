package com.yousset.rentcar.config.filter;

import com.yousset.rentcar.config.security.TokenProvider;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private TokenProvider tokenProvider;
    private String principalRequestHeader;
    private String principalRequestHeaderValue;

    public TokenAuthenticationFilter(TokenProvider tokenProvider, String principalRequestHeader, String principalRequestHeaderValue) {
        this.tokenProvider = tokenProvider;
        this.principalRequestHeader = principalRequestHeader;
        this.principalRequestHeaderValue = principalRequestHeaderValue;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            if (!StringUtils.isEmpty(principalRequestHeader)) {
                if (request.getHeader(principalRequestHeader) == null || !request.getHeader(principalRequestHeader).equals(principalRequestHeaderValue)) {
                    throw new BadCredentialsException("The API key was not found or not the expected value.");
                }
            }

            Claims claims = authenticUser(request, tokenProvider, null);
            if (claims != null) {
                UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) tokenProvider.getAuthentication(getJwtFromRequest(request));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                throw new BadCredentialsException("The API Authentication was not found or not the expected value.");
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context");
        }
        filterChain.doFilter(request, response);
    }

    public Claims authenticUser(HttpServletRequest request, TokenProvider tokenProvider, Claims claims) {
        if (claims != null) {
            return claims;
        }

        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
                claims = tokenProvider.getUserIdFromToken(jwt);
                return claims;
            }
        } catch (Exception ex) {
            log.error("Could not set user authentication in security context", ex);
        }

        return null;
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
