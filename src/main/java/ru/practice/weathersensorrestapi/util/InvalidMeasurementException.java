package ru.practice.weathersensorrestapi.util;

public class InvalidMeasurementException extends RuntimeException {
    public InvalidMeasurementException(String message) {
        super(message);
    }
}
