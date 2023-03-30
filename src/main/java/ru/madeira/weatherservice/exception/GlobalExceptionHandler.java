package ru.madeira.weatherservice.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<String> handleConstraintViolationException(ConstraintViolationException exception) {
        List<String> constraintViolationsMessages = new ArrayList<>();
        for (ConstraintViolation<?> constraintViolation : exception.getConstraintViolations()) {
            constraintViolationsMessages.add(constraintViolation.getMessage());
        }
        return constraintViolationsMessages;
    }

    @ExceptionHandler(value = {WeatherApiConnectException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleHttpClientErrorException(WeatherApiConnectException exception) {
        return "Internal Server Error. Please, reload or wait...";
    }

}
