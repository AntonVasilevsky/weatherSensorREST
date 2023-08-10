package com.anton.sensor.controllers;

import com.anton.sensor.models.Measurement;
import com.anton.sensor.services.MeasurementService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/measure")
public class MeasurementController {
    private final MeasurementService measurementService;

    public MeasurementController(MeasurementService measurementService) {
        this.measurementService = measurementService;
    }
    @GetMapping
    public List<Measurement> findAll() {
        return measurementService.findAll();
    }
    @PostMapping("/add")
    public void add(@RequestBody @Valid Measurement measurement,
                    BindingResult bindingResult) {
        measurement.setCreatedAt(LocalDateTime.now());

        if(!bindingResult.hasErrors()) {
            measurementService.add(measurement);
        }

    }
}
