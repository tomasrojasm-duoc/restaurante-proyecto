package com.notification.tbrm.restaurante.notification.controller.handler;

import com.notification.tbrm.restaurante.notification.dto.ExceptionDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

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

        logger.warn("Error de validación en notification. totalErrores={}", errors.size());

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
        ExceptionDto error = new ExceptionDto();

        error.setMessage("Ocurrió un error");
        error.setDescription(ex.getMessage());

        logger.error("Error general capturado en ApiExceptionHandler de notification. Motivo={}",
                ex.getMessage(),
                ex);

        return ResponseEntity.badRequest().body(error);
    }
}