package com.anton.sensor.util;

import com.anton.sensor.dto.SensorDto;
import com.anton.sensor.models.Sensor;
import com.anton.sensor.services.SensorService;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
@Component
public class SensorDtoValidator implements Validator {
    private final SensorService sensorService;

    public SensorDtoValidator(SensorService sensorService) {
        this.sensorService = sensorService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return SensorDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        SensorDto sensorDto = (SensorDto) target;
        if(sensorService.findByName(sensorDto.getName()).isPresent()) {
            errors.rejectValue("name", "", "sensor already registered");
        }

    }
}
