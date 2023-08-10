package com.anton.sensor.controllers;

import com.anton.sensor.models.Sensor;
import com.anton.sensor.services.SensorService;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sensor")
public class SensorController {
    private final SensorService sensorService;

    public SensorController(SensorService sensorService) {
        this.sensorService = sensorService;
    }
    @GetMapping()
    public List<Sensor> getAll() {
        return sensorService.findAll();
    }
    @PostMapping("/register")
    public void register(@RequestBody @Valid Sensor sensor, BindingResult bindingResult) {
        if(!bindingResult.hasErrors()){
            sensorService.register(sensor);
        }
    }


}
