package com.example.currencyConverter.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConversionResponse {

    private String fromCurrency;
    private String toCurrency;
    private double units;
    private double exchangeRate;
    private double convertedAmount;
}
