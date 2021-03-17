package com.yousset.rentcar.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.yousset.rentcar.utils.UncheckedObjectMapper;
import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;

import java.util.TimeZone;

@Configuration
@EnableRetry
@Slf4j
public class SpringConfig {

    @Bean
    public UncheckedObjectMapper getMapper() {
        return (UncheckedObjectMapper) new UncheckedObjectMapper()
                .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .registerModule(new JavaTimeModule())
                .setTimeZone(TimeZone.getDefault());
    }

    @Bean
    public GraphQLScalarType date() {
        return ExtendedScalars.Date;
    }
}

