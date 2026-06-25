package com.example.currencyConverter.advice;

import com.example.currencyConverter.exceptions.InvalidCurrencyException;
import com.example.currencyConverter.exceptions.InvalidUnitsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<?> handleMissingParams(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                        "error", "Missing required parameter",
                        "param", ex.getParameterName()
                ));
    }

    @ExceptionHandler(InvalidCurrencyException.class)
    public ResponseEntity<?> handleInvalidCurrency(InvalidCurrencyException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(InvalidUnitsException.class)
    public ResponseEntity<?> handleInvalidUnits(InvalidUnitsException ex) {
        return ResponseEntity.badRequest()
                .body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return ResponseEntity.internalServerError()
                .body(Map.of("error", "Something went wrong: " + ex.getMessage()));
    }
}
