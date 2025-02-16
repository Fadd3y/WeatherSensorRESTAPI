package ru.practice.weathersensorrestapi.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import ru.practice.weathersensorrestapi.models.Sensor;

public class MeasurementDTO {

    @Min(value = -273 , message = "Temperature can't be less than -273.15")
    private double value;

    private boolean raining;

    //@NotEmpty(message = "Measurement cant be without sensor name.")
    private SensorDTO sensor;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public SensorDTO getSensor() {
        return sensor;
    }

    public void setSensor(SensorDTO sensor) {
        this.sensor = sensor;
    }
}
