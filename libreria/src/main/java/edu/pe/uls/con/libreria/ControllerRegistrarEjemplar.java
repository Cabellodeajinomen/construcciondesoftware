package edu.pe.uls.con.libreria;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerRegistrarEjemplar {

    ServiceRegistrarEjemplar serviceRegistrarEjemplar;

    public ControllerRegistrarEjemplar(ServiceRegistrarEjemplar serviceRegistrarEjemplar) {
        this.serviceRegistrarEjemplar = serviceRegistrarEjemplar;
    }

    @PostMapping("/ejemplar/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEjemplar guardarEjemplar(@RequestBody RequestEjemplar ejemplar) {
        return serviceRegistrarEjemplar.registrarEjemplar(ejemplar);
    }
}