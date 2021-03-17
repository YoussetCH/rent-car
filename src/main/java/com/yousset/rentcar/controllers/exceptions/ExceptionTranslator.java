package com.yousset.rentcar.controllers.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ExceptionTranslator {

    @ExceptionHandler
    protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException exception) {

        List<ApiError> apiErrors = new ArrayList<>();

        for (ConstraintViolation<?> violation : exception.getConstraintViolations()) {
            String value = (violation.getInvalidValue() == null ? null : violation.getInvalidValue().toString());
            apiErrors.add(new ApiError(violation.getPropertyPath().toString(), value, violation.getMessage()));
        }

        return ResponseEntity.badRequest().body(apiErrors);
    }
}
