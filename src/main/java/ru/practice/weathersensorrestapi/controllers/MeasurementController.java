package ru.practice.weathersensorrestapi.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.practice.weathersensorrestapi.dto.MeasurementDTO;
import ru.practice.weathersensorrestapi.dto.SensorDTO;
import ru.practice.weathersensorrestapi.models.Measurement;
import ru.practice.weathersensorrestapi.services.MeasurementService;
import ru.practice.weathersensorrestapi.services.SensorService;
import ru.practice.weathersensorrestapi.util.InvalidMeasurementException;
import ru.practice.weathersensorrestapi.util.MeasurementDTOValidator;
import ru.practice.weathersensorrestapi.util.SensorRegistrationDeniedException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measurements")
public class MeasurementController {

    private final MeasurementService measurementService;
    private final MeasurementDTOValidator measurementDTOValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public MeasurementController(MeasurementService measurementService, MeasurementDTOValidator measurementDTOValidator, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.measurementDTOValidator = measurementDTOValidator;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid MeasurementDTO measurementDTO,
                                          BindingResult bindingResult) {
        measurementDTOValidator.validate(measurementDTO, bindingResult);

        if (bindingResult.hasErrors()) {
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
            throw new InvalidMeasurementException(errorResponse.toString());
        }

        Measurement measurement = modelMapper.map(measurementDTO, Measurement.class);
        measurementService.add(measurement);
        return null;
    }

    @GetMapping()
    public List<MeasurementDTO> getAll() {
        return measurementService.getAll()
                .stream()
                .map(m -> modelMapper.map(m, MeasurementDTO.class))
                .toList();
    }

    @GetMapping("/rainyDaysCount")
    public HttpEntity<Map<String, Integer>> getRainyDaysCount() {
        Map<String, Integer> response = new HashMap<>();
        response.put("rainy_days", measurementService.countRainyDays());
        return new HttpEntity<>(response);
    }

    @ExceptionHandler
    private HttpEntity<Map<String, String>> errorHandling(InvalidMeasurementException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error_message: ", e.getMessage());
        return new HttpEntity<>(response);
    }
}
