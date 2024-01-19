package com.remedy.iarlen.course.infra;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handler404() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conteúdo não encontrado.");
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorDate>> handler400(MethodArgumentNotValidException exception) {

        List<FieldError> errors = exception.getFieldErrors();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(errors.stream()
                .map(ErrorDate::new)
                .toList());
    }

    public record ErrorDate (String Code, String field, String message) {
        public ErrorDate (FieldError error) {
            this(error.getCode(), error.getField(), error.getDefaultMessage());

        }
    }
}
