package com.example.currencyConverter.advice;

import lombok.Data;

import java.util.Map;

@Data
public class ApiErrorResponse {

    private String message;
    private Map<String, Object> errors;
}
