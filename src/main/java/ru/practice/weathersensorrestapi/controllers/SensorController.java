package ru.practice.weathersensorrestapi.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.practice.weathersensorrestapi.dto.SensorDTO;
import ru.practice.weathersensorrestapi.models.Sensor;
import ru.practice.weathersensorrestapi.services.SensorService;
import ru.practice.weathersensorrestapi.util.SensorDTOValidator;
import ru.practice.weathersensorrestapi.util.SensorRegistrationDeniedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sensors")
public class SensorController {

    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private final SensorDTOValidator sensorValidator;

    public SensorController(SensorService sensorService, ModelMapper modelMapper, SensorDTOValidator sensorDTOValidator) {
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        this.sensorValidator = sensorDTOValidator;
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> register(@RequestBody @Valid SensorDTO sensorDTO,
                                               BindingResult bindingResult) {
        //Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        sensorValidator.validate(sensorDTO, bindingResult);

        if(bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            StringBuilder errorResponse = new StringBuilder();

            for (FieldError error : errors) {
                errorResponse.append("field: ")
                        .append(error.getField())
                        .append(", error: ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }
            System.out.println(errorResponse); //TODO логирование
            throw new SensorRegistrationDeniedException(errorResponse.toString());
        }

        Sensor sensor = modelMapper.map(sensorDTO, Sensor.class);
        sensorService.save(sensor);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    private HttpEntity<Map<String, String>> errorHandling(SensorRegistrationDeniedException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error_message: ", e.getMessage());
        return new HttpEntity<>(response);
    }
}
