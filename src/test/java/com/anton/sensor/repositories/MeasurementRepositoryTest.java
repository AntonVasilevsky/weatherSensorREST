package com.anton.sensor.repositories;


import com.anton.sensor.models.Measurement;
import com.anton.sensor.models.Sensor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.Optional;



@DataJpaTest
class MeasurementRepositoryTest {
    @Autowired
    private MeasurementRepository measurementRepository;
    @Autowired
    private SensorRepository sensorRepository;

    @Test
    void measurementRepository_saveAll_returnSavedMeasurement() {
        //Arrange
        Sensor sensor =new Sensor();
        sensor.setName("today");
        sensorRepository.save(sensor);

        LocalDateTime ldt = LocalDateTime.now();
        Measurement measurement = Measurement.builder().value(11f).raining(true)
                .createdAt(ldt).sensor(sensor).build();
        measurementRepository.save(measurement);
        //Act
        Optional<Measurement> optional = measurementRepository.findBySensorName("today");

        //Assert
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(11f, optional.orElseThrow().getValue(), 0.1);

    }


}