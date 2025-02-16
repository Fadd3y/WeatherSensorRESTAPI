package ru.practice.weathersensorrestapi.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practice.weathersensorrestapi.models.Measurement;
import ru.practice.weathersensorrestapi.models.Sensor;
import ru.practice.weathersensorrestapi.repositories.MeasurementRepository;
import ru.practice.weathersensorrestapi.repositories.SensorRepository;
import ru.practice.weathersensorrestapi.util.InvalidMeasurementException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class MeasurementService {

    private final MeasurementRepository measurementRepository;
    private final SensorService sensorService;

    @Autowired
    public MeasurementService(MeasurementRepository measurementRepository, SensorService sensorService) {
        this.measurementRepository = measurementRepository;
        this.sensorService = sensorService;
    }


    @Transactional
    public void add(Measurement measurement) {
        Optional<Sensor> sensor = sensorService.findByName(measurement.getSensor().getName());
        if (sensor.isPresent()) {
            measurement.setSensor(sensor.get());
            enrichMeasurement(measurement);
            measurementRepository.save(measurement);
        }
    }

    public List<Measurement> getAll() {
        return measurementRepository.findAll();
    }

    public int countRainyDays() {
        return measurementRepository.countByRainingIsTrue();
    }

    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }

    private void enrichMeasurement(Measurement measurement) {
        measurement.setDateTime(LocalDateTime.now());
    }

}
