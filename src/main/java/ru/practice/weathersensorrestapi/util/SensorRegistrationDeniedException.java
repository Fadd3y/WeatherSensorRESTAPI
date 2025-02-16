package ru.practice.weathersensorrestapi.util;

public class SensorRegistrationDeniedException extends RuntimeException {
    public SensorRegistrationDeniedException(String message) {
        super(message);
    }
}
