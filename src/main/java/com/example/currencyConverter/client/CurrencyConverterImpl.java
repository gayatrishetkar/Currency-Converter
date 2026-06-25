package com.example.currencyConverter.client;

import com.example.currencyConverter.Dto.ConversionResponse;
import com.example.currencyConverter.advice.ApiErrorResponse;
import com.example.currencyConverter.advice.ApiResponse;
import com.example.currencyConverter.exceptions.InvalidCurrencyException;
import com.example.currencyConverter.exceptions.InvalidUnitsException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

@Service
@RequiredArgsConstructor
public class CurrencyConverterImpl implements CurrencyConverter {

    private final RestClient restClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${freecurrency.api.key}")
    private String apiKey;

    @Override
    public ConversionResponse convert(String fromCurrency, String toCurrency, double units) {

        // Validate units
        if (units <= 0) {
            throw new InvalidUnitsException();
        }

        try {
            ApiResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .queryParam("apikey", apiKey)
                            .queryParam("base_currency", fromCurrency.toUpperCase())
                            .queryParam("currencies", toCurrency.toUpperCase())
                            .build())
                    .retrieve()
                    .body(ApiResponse.class);

            Double exchangeRate = response.getData().get(toCurrency.toUpperCase());
            if (exchangeRate == null) {
                throw new InvalidCurrencyException(toCurrency);
            }

            double convertedAmount = Math.round(units * exchangeRate * 10000.0) / 10000.0;

            return new ConversionResponse(
                    fromCurrency.toUpperCase(),
                    toCurrency.toUpperCase(),
                    units,
                    exchangeRate,
                    convertedAmount
            );

        } catch (InvalidCurrencyException e) {
            throw e;
        } catch (RestClientResponseException e) {
            String body = e.getResponseBodyAsString();
            if (body.contains("base_currency")) {
                throw new InvalidCurrencyException(fromCurrency);
            } else if (body.contains("currencies")) {
                throw new InvalidCurrencyException(toCurrency);
            }
            throw new RuntimeException("API error: " + body);
        }
    }
}
