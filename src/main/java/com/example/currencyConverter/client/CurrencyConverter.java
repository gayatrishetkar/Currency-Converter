package com.example.currencyConverter.client;

import com.example.currencyConverter.Dto.ConversionResponse;

public interface CurrencyConverter {

    ConversionResponse convert(String fromCurrency, String toCurrency, double units);
}
