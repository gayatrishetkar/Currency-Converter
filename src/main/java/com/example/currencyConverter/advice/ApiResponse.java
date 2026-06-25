package com.example.currencyConverter.advice;

import lombok.Data;

import java.util.Map;

@Data
public class ApiResponse {
    private Map<String, Double> data;
}
