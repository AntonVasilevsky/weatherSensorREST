package com.anton.sensor.exceptions;


import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({ConstraintViolationException.class, DataIntegrityViolationException.class,
            DuplicateValueException.class})
    public ResponseEntity<ErrorResponse> handleValidationException(Exception e) {
        StringBuilder errors = new StringBuilder();
        if (e instanceof ConstraintViolationException) {
            ConstraintViolationException cve = (ConstraintViolationException) e;
            cve.getConstraintViolations().forEach(cv -> {
                errors.append(cv.getPropertyPath().toString())
                        .append(": ")
                        .append(cv.getMessage())
                        .append("; ");
            });
        } else {
            errors.append(e.getMessage()).append(System.lineSeparator());
        }

        ErrorResponse errorResponse = new ErrorResponse(
                errors.toString(),
                System.currentTimeMillis()
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
