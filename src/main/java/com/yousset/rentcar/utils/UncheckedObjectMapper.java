package com.yousset.rentcar.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.CompletionException;

@Slf4j
public class UncheckedObjectMapper extends com.fasterxml.jackson.databind.ObjectMapper {

    public <T> T readValueUnFailed(String content, Class<T> valueType) {
        try {
            return super.readValue(content, valueType);
        } catch (IOException ioe) {
            log.error("Error Parsing Data", ioe);
            throw new CompletionException(ioe);
        }
    }

    public <T> T readValueUnFailed(String content, com.fasterxml.jackson.core.type.TypeReference<T> valueTypeRef) {
        try {
            return super.readValue(content, valueTypeRef);
        } catch (IOException ioe) {
            log.error("Error Parsing Data", ioe);
            throw new CompletionException(ioe);
        }
    }
}
