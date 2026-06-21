package com.kitchen.tbrm.restaurante.kitchen.controller.handler;

import com.kitchen.tbrm.restaurante.kitchen.dto.ExceptionDto;
import feign.RetryableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ExceptionDto>> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex
    ) {
        List<ExceptionDto> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((org.springframework.validation.FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();

            errors.add(new ExceptionDto(fieldName, errorMessage));
        });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ExceptionDto> handlerRetryableException(RetryableException ex) {
        ExceptionDto error = new ExceptionDto();

        error.setMessage("Error comunicando con otra API");
        error.setDescription("Una de las APIs que necesitamos, está abajo. Súbela y vuelve a intentar.");

        log.error(ex.getMessage());

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
        ExceptionDto error = new ExceptionDto();

        error.setMessage("Ocurrió un error");
        error.setDescription(ex.getMessage());

        log.error(ex.getMessage());

        return ResponseEntity.badRequest().body(error);
    }
}