package com.anton.sensor.dto;

import com.anton.sensor.models.Sensor;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public class MeasurementDto {
    @DecimalMin(value = "-100.0", message = "value should be between -100 and 100")
    @DecimalMax(value = "100.0", message = "value should be between -100 and 100")

    private float value;
    @NotNull(message = "Field raining should be set")
    private Boolean raining;

    @NotNull(message = "Measurement should be assigned to sensor")
    private Sensor sensor;
    private LocalDateTime createdAt;



    public MeasurementDto(float value,  Boolean raining, Sensor sensor, LocalDateTime createdAt) {
        this.value = value;
        this.raining = raining;
        this.sensor = sensor;
        this.createdAt = createdAt;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public boolean isRaining() {
        return raining;
    }

    public void setRaining(boolean raining) {
        this.raining = raining;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MeasurementDto{" +
                "value=" + value +
                ", raining=" + raining +
                ", sensorName=" + sensor.getName() +
                ", createdAt=" + createdAt +
                '}';
    }
}
