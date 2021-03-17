package com.yousset.rentcar.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Value("${xclient.header:rent}")
    private String xClientHeader;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yousset.rentcar.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(this.getApiInfo())
                .securitySchemes(this.getSecuritySchemes());
    }

    private List<SecurityScheme> getSecuritySchemes() {

        return Arrays.asList(
                new BasicAuth("basicAuth"), new ApiKey("X-Client", this.xClientHeader, "header"));
    }

    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("Yousset Rent Car API")
                .description("Rent Car Test projects")
                .contact(
                        new springfox.documentation.service.Contact(
                                "Yousset Chacon",
                                "https://www.google.com",
                                "davidbelandria126@gmail.com"))
                .license("Apache License Version 2.0")
                .licenseUrl("https://github.com/IBM-Bluemix/news-aggregator/blob/master/LICENSE")
                .version("1.0")
                .build();
    }
}

