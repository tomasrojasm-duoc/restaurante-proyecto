package com.order.tbrm.restaurante.order.controller.handler;

import com.order.tbrm.restaurante.order.dto.ExceptionDto;
import feign.RetryableException;
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

        logger.warn("Error de validación en request. totalErrores={}", errors.size());

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(RetryableException.class)
    public ResponseEntity<ExceptionDto> handlerRetryableException(RetryableException ex) {
        ExceptionDto error = new ExceptionDto();

        error.setMessage("Error comunicando con otra API");
        error.setDescription("Una de las APIs que necesitamos, está abajo. Súbela y vuelve a intentar.");

        logger.error("Error de comunicación con otro microservicio. Motivo={}", ex.getMessage(), ex);

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleException(Exception ex) {
        ExceptionDto error = new ExceptionDto();

        error.setMessage("Ocurrió un error");
        error.setDescription(ex.getMessage());

        logger.error("Error general capturado en ApiExceptionHandler. Motivo={}", ex.getMessage(), ex);

        return ResponseEntity.badRequest().body(error);
    }
}