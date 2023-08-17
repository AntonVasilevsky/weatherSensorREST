package com.anton.sensor.controllers;

import com.anton.sensor.dto.MeasurementDto;

import com.anton.sensor.models.Measurement;
import com.anton.sensor.models.Sensor;
import com.anton.sensor.services.MeasurementService;
import com.anton.sensor.services.SensorService;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/measure")
public class MeasurementController {
    private final MeasurementService measurementService;
    private final SensorService sensorService;
    private final ModelMapper modelMapper;
    // getting when program starts and use instead db
    // also puts sensors in context, its needed when adding measurement
    private List<Sensor> sensorList;


    public MeasurementController(MeasurementService measurementService, SensorService sensorService, ModelMapper modelMapper) {
        this.measurementService = measurementService;
        this.sensorService = sensorService;
        this.modelMapper = modelMapper;
        sensorList = sensorService.findAll();
    }

    @GetMapping
    public List<MeasurementDto> findAll() {
        return measurementService.findAll()
                .stream()
                .map(this::convertToMeasurementDto)
                .collect(Collectors.toList());
    }
    @GetMapping("/rain")
    public long findRain() {
        return measurementService.findAllByRaining()
                .size();
    }

    @PostMapping("/add")

    public ResponseEntity<String> add(@RequestBody @Valid MeasurementDto measurementDto) {

        Sensor sensor = null;
        String inJsonName = measurementDto.getSensor().getName();
        Sensor sensorFromDb = sensorList.stream().filter(s -> s.getName().equals(inJsonName))
                .findAny().orElse(new Sensor());
        String fromDbName = sensorFromDb.getName();

        if (inJsonName.equals(fromDbName)) {
            sensor = sensorFromDb;
            measurementDto.setSensor(sensor);
        } else {
            sensor = measurementDto.getSensor();


            sensorService.register(sensor);
            sensorList.add(sensor);


        }

        measurementService.add(convertToMeasurement(measurementDto));

        return ResponseEntity.ok("Measurement added successfully");
    }

    public Measurement convertToMeasurement(MeasurementDto measurementDto) {
        return modelMapper.map(measurementDto, Measurement.class);
    }

    public MeasurementDto convertToMeasurementDto(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDto.class);
    }


}
