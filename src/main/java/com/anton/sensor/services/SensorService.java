package com.anton.sensor.services;

import com.anton.sensor.dto.SensorDto;
import com.anton.sensor.exceptions.ErrorResponse;
import com.anton.sensor.models.Sensor;
import com.anton.sensor.repositories.SensorRepository;
import com.anton.sensor.exceptions.DuplicateValueException;
import jakarta.validation.ConstraintViolationException;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;
    private final ModelMapper modelMapper;

    public SensorService(SensorRepository sensorRepository, ModelMapper modelMapper) {
        this.sensorRepository = sensorRepository;
        this.modelMapper = modelMapper;
    }
    public Optional<Sensor> findByName(String name) {
        return sensorRepository.findByName(name);
    }
    public Optional<Sensor> findById(int id) {
        return sensorRepository.findById(id);
    }
    public List<Sensor> findAll() {
        return sensorRepository.findAll();
    }
    @Transactional
    public void register(Sensor sensor) {
        try {
            sensorRepository.save(sensor);
        }catch (DataIntegrityViolationException e) {
            throw new DuplicateValueException("Sensor already exists: " + e.getMessage());
        }
    }
    public  SensorDto convertToSensorDto(Sensor sensor) {
        return modelMapper.map(sensor, SensorDto.class);
    }
    public Sensor convertToSensor(SensorDto sensorDto) {
        return modelMapper.map(sensorDto, Sensor.class);
    }

}
