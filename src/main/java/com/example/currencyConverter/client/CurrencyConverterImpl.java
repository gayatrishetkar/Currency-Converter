package com.example.currencyConverter.client;

import com.example.currencyConverter.Dto.ConversionResponse;
import com.example.currencyConverter.advice.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
@RequiredArgsConstructor
public class CurrencyConverterImpl implements CurrencyConverter {

    private final RestClient restClient;

    @Value("${freecurrency.api.key}")
    private String apiKey;

    @Override
    public ConversionResponse convert(String fromCurrency, String toCurrency, double units) {

        ApiResponse response = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("apikey", apiKey)
                        .queryParam("base_currency", fromCurrency.toUpperCase())
                        .queryParam("currencies", toCurrency.toUpperCase())
                        .build())
                .retrieve()
                .body(ApiResponse.class);

        double exchangeRate = response.getData().get(toCurrency.toUpperCase());
        double convertedAmount = Math.round(units * exchangeRate * 10000.0) / 10000.0;

        return new ConversionResponse(
                fromCurrency.toUpperCase(),
                toCurrency.toUpperCase(),
                units,
                exchangeRate,
                convertedAmount
        );
    }
}
