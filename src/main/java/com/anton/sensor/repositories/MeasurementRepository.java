package com.anton.sensor.repositories;

import com.anton.sensor.models.Measurement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MeasurementRepository extends JpaRepository<Measurement, Integer> {
    Optional<Measurement> findBySensorName(String name);
}
