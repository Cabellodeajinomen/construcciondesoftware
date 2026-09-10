package edu.pe.uls.cos.falabella;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    public record ResponseError(String error) {}

    @ExceptionHandler(RecursoInvalidoException.class)
    public ResponseEntity<ResponseError> manejarRecursoInvalido(RecursoInvalidoException ex) {
        return ResponseEntity.badRequest().body(new ResponseError(ex.getMessage()));
    }
}