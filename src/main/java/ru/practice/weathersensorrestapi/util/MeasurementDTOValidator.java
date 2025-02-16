package ru.practice.weathersensorrestapi.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.practice.weathersensorrestapi.dto.MeasurementDTO;
import ru.practice.weathersensorrestapi.models.Measurement;
import ru.practice.weathersensorrestapi.services.SensorService;

@Component
public class MeasurementDTOValidator implements Validator {
    private final SensorService sensorService;

    public MeasurementDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return MeasurementDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        MeasurementDTO measurement = (MeasurementDTO) target;

        if (measurement.getSensor() == null) {
            errors.rejectValue("sensor", "", "Name should not be empty.");
            return;
        }

        if (measurement.getSensor() == null) {
            errors.rejectValue("sensor", "", "Name should not be empty.");
            return;
        }

        if (measurement.getSensor() != null && sensorService.findByName(measurement.getSensor().getName()).isEmpty()) {
            errors.rejectValue("sensor", "", "There is no such sensor in database. Register a new one.");
        }
    }
}
