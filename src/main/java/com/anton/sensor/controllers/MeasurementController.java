package com.anton.sensor.controllers;

import com.anton.sensor.dto.MeasurementDto;
import com.anton.sensor.dto.SensorDto;
import com.anton.sensor.models.Measurement;
import com.anton.sensor.models.Sensor;
import com.anton.sensor.services.MeasurementService;
import com.anton.sensor.services.SensorService;
import com.anton.sensor.util.DuplicateValueException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    public ResponseEntity<String> add(@RequestBody @Valid MeasurementDto measurementDto,
                    BindingResult bindingResult) {
        if(bindingResult.hasFieldErrors()) {
            StringBuilder errorMessage = new StringBuilder();
            for(FieldError e : bindingResult.getFieldErrors()) {
                errorMessage
                        .append(e.getField())
                        .append(" - ")
                        .append(e.getDefaultMessage())
                        .append(";");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.toString());
        }
        Sensor sensor = null;
        String inJsonName = measurementDto.getSensor().getName();
        Sensor sensorFromDb = sensorList.stream().filter(s -> s.getName().equals(inJsonName))
                .findAny().orElse(new Sensor());
        String fromDbName = sensorFromDb.getName();

        if(inJsonName.equals(fromDbName)) {
            sensor = sensorFromDb;
            measurementDto.setSensor(sensor);
        }else {
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
