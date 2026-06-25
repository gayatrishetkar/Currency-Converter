package com.example.currencyConverter.controller;

import com.example.currencyConverter.Dto.ConversionResponse;
import com.example.currencyConverter.client.CurrencyConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyController {

    private final CurrencyConverter currencyConverter;

    @GetMapping("/convertCurrency")
    public ResponseEntity<?> convertCurrency(
            @RequestParam String fromCurrency,
            @RequestParam String toCurrency,
            @RequestParam double units) {

        ConversionResponse result = currencyConverter.convert(fromCurrency, toCurrency, units);
        return ResponseEntity.ok(result);
    }
}
