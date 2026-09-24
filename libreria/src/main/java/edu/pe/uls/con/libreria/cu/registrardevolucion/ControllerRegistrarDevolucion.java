package edu.pe.uls.con.libreria.cu.registrardevolucion;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.uls.con.libreria.cu.registrardevolucion.request.RequestDevolucion;
import edu.pe.uls.con.libreria.cu.registrardevolucion.response.ResponseDevolucion;

@RestController
public class ControllerRegistrarDevolucion {

    ServiceRegistrarDevolucion serviceRegistrarDevolucion;

    public ControllerRegistrarDevolucion(ServiceRegistrarDevolucion serviceRegistrarDevolucion) {
        this.serviceRegistrarDevolucion = serviceRegistrarDevolucion;
    }

    @PostMapping("/devolucion/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDevolucion guardarDevolucion(@RequestBody RequestDevolucion devolucion) {
        return serviceRegistrarDevolucion.registrarDevolucion(devolucion);
    }

}
