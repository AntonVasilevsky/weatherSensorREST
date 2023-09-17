package com.anton.sensor.repositories;

import com.anton.sensor.models.Measurement;
import com.anton.sensor.models.Sensor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;
@DataJpaTest

class SensorRepositoryTest {
    @Autowired
    private SensorRepository sensorRepository;

    @Test
    void sensorRepository_saveAll_returnSavedSensor() {
        //Arrange
        String name = "Sensor for Test";
        Sensor sensor = Sensor.builder().name(name).build();
        sensorRepository.save(sensor);
        //Act
        Sensor sensorFromDb = sensorRepository.findByName(name).orElseThrow();
        //Assert
        Assertions.assertEquals(sensor.getId(), sensorFromDb.getId());
        Assertions.assertEquals(sensor.getName(), sensorFromDb.getName());
    }
}