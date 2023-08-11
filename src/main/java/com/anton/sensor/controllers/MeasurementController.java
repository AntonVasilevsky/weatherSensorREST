package com.anton.sensor.controllers;

import com.anton.sensor.dto.MeasurementDto;
import com.anton.sensor.models.Measurement;
import com.anton.sensor.models.Sensor;
import com.anton.sensor.services.MeasurementService;
import com.anton.sensor.services.SensorService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    @PostMapping("/add")

    public void add(@RequestBody @Valid MeasurementDto measurementDto,
                    BindingResult bindingResult) {
        Sensor sensor = null;
        String jsonName = measurementDto.getSensor().getName();
        Sensor sensorFromDb = sensorList.stream().filter(s -> s.getName().equals(jsonName))
                .findAny().orElse(new Sensor());
        String dbName = sensorFromDb.getName();

        if(jsonName.equals(dbName)) {
            sensor = sensorFromDb;
            measurementDto.setSensor(sensor);
        }else {
            sensor = measurementDto.getSensor();
            sensorService.register(sensor);
            sensorList.add(sensor);
        }

        measurementService.add(convertToMeasurement(measurementDto));

    }
    public Measurement convertToMeasurement(MeasurementDto measurementDto) {
        return modelMapper.map(measurementDto, Measurement.class);
    }
    public MeasurementDto convertToMeasurementDto(Measurement measurement) {
        return modelMapper.map(measurement, MeasurementDto.class);
    }


}
