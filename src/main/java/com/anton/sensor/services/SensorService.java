package com.anton.sensor.services;

import com.anton.sensor.models.Sensor;
import com.anton.sensor.repositories.SensorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SensorService {
    private final SensorRepository sensorRepository;

    public SensorService(SensorRepository sensorRepository) {
        this.sensorRepository = sensorRepository;
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
        sensorRepository.save(sensor);
    }
}
