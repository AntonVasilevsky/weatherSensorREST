package com.anton.sensor.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;

public class SensorDto {

    @NotEmpty(message = "Our message")

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
