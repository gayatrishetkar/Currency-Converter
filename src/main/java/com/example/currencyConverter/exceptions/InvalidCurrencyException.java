package com.example.currencyConverter.exceptions;

public class InvalidCurrencyException extends RuntimeException {
    public InvalidCurrencyException(String currencyCode) {
        super("Invalid currency code: " + currencyCode);
    }
}
