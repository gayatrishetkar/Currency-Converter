package com.example.currencyConverter.exceptions;

public class InvalidUnitsException extends RuntimeException {
    public InvalidUnitsException() {
        super("Units must be a positive number greater than 0");
    }
}
