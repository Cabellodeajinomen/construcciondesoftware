package pe.edu.uls.falabella.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import pe.edu.uls.falabella.ClienteInvalidoException;
import pe.edu.uls.falabella.PedidoInvalidoException;
import pe.edu.uls.falabella.ProductoInvalidoException;

@RestControllerAdvice
public class ManejadorGlobalAdvice {

    @ExceptionHandler(ProductoInvalidoException.class)
    public ResponseEntity<String> manejarProductoInvalido(ProductoInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(PedidoInvalidoException.class)
    public ResponseEntity<String> manejarPedidoInvalido(PedidoInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ClienteInvalidoException.class)
    public ResponseEntity<String> manejarClienteInvalido(ClienteInvalidoException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> manejarGenerico(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
