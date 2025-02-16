package ru.practice.weathersensorrestapi.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.practice.weathersensorrestapi.dto.SensorDTO;
import ru.practice.weathersensorrestapi.models.Sensor;
import ru.practice.weathersensorrestapi.services.SensorService;

@Controller
public class SensorDTOValidator implements Validator {

    private final SensorService sensorService;

    @Autowired
    public SensorDTOValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDTO sensorDTO = (SensorDTO) target;

        if (sensorDTO.getName() != null && sensorService.findByName(sensorDTO.getName()).isPresent()) {
            errors.rejectValue("name", "", "Sensor already exist. Choose another name.");
        }
    }
}
