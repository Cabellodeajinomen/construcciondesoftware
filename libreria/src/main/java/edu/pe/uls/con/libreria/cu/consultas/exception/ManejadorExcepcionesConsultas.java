package edu.pe.uls.con.libreria.cu.consultas.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ManejadorExcepcionesConsultas {
     // dni <3digitos, id negativo, estado PERDIDO
    @ExceptionHandler(ParametroInvalidoException.class)
    public ProblemDetail parametroInvalido(ParametroInvalidoException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    // 
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail tipoIncorrecto(MethodArgumentTypeMismatchException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
            "El valor '" + ex.getValue() + "' no es válido para el parámetro '" + ex.getName() + "'");
    }
    //cuando lo que se busca no
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ProblemDetail noEncontrado(RecursoNoEncontradoException ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
