package ru.practice.weathersensorrestapi.dto;

import jakarta.validation.constraints.Size;

public class SensorDTO {

    @Size(min = 3, max = 30, message = "Sensor name should be between 3 and 30 characters.")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
