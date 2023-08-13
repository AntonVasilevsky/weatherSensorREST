package com.anton.sensor.services;

import com.anton.sensor.dto.MeasurementDto;
import com.anton.sensor.models.Measurement;
import com.anton.sensor.models.Sensor;
import com.anton.sensor.repositories.MeasurementRepository;
import com.anton.sensor.repositories.SensorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)

public class MeasurementService {
    private final MeasurementRepository measurementRepository;
    private final SensorRepository sensorRepository;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    private List<Sensor> sensorList;

    public MeasurementService(MeasurementRepository measurementRepository, SensorRepository sensorRepository, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementRepository = measurementRepository;
        this.sensorRepository = sensorRepository;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        sensorList = sensorService.findAll();
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
        enrichMeasurement(measurement);
        measurementRepository.save(measurement);
    }

    public Measurement convertToMeasurement(MeasurementDto measurementDto) {
        return modelMapper.map(measurementDto, Measurement.class);
    }
    public MeasurementDto convertToMeasurementDto(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDto.class);
    }
    public void enrichMeasurement(Measurement measurement) {
        measurement.setCreatedAt(LocalDateTime.now());
    }

}
