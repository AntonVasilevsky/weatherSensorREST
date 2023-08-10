package com.anton.sensor.services;

import com.anton.sensor.models.Measurement;
import com.anton.sensor.repositories.MeasurementRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)

public class MeasurementService {
    private final MeasurementRepository measurementRepository;

    public MeasurementService(MeasurementRepository measurementRepository) {
        this.measurementRepository = measurementRepository;
    }
    public Optional<Measurement> findById(int id) {
        return measurementRepository.findById(id);
    }
    public Optional<Measurement> findBySensorName(String name) {
        return measurementRepository.findBySensorName(name);
    }
    public List<Measurement> findAll() {
        return measurementRepository.findAll();
    }
    @Transactional
    public void add(Measurement measurement) {
        measurementRepository.save(measurement);
    }

}
