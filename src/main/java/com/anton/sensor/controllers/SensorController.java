package com.anton.sensor.controllers;

import com.anton.sensor.dto.SensorDto;
import com.anton.sensor.exceptions.ErrorResponse;

import com.anton.sensor.models.Sensor;
import com.anton.sensor.services.SensorService;
import com.anton.sensor.exceptions.DuplicateValueException;
import com.anton.sensor.util.SensorValidator;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    private final SensorService sensorService;
    private final SensorValidator sensorValidator;

    public SensorController(SensorService sensorService, SensorValidator sensorDtoValidator) {
        this.sensorService = sensorService;
        this.sensorValidator = sensorDtoValidator;
    }

    @GetMapping()
    public List<SensorDto> getAll() {
        return sensorService.findAll()
                .stream()
                .map(sensorService::convertToSensorDto)
                .collect(Collectors.toList());
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody @Valid SensorDto sensorDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) return getErrorFieldsResponse(bindingResult);

        Sensor sensor = dtoToSensor(sensorDto);

        try {
            sensorService.register(sensor);
        } catch (DuplicateValueException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

        return ResponseEntity.ok("Sensor registered successfully");
    }

    public Sensor dtoToSensor(SensorDto sensorDto) {
        Sensor sensor = new Sensor();
        sensor.setName(sensorDto.getName());

        return sensor;
    }

    public SensorDto sensorToDto(Sensor sensor) {
        SensorDto sensorDto = new SensorDto();
        sensorDto.setName(sensor.getName());
        return sensorDto;
    }

    public ResponseEntity<String> getErrorFieldsResponse(BindingResult bindingResult) {

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


}
