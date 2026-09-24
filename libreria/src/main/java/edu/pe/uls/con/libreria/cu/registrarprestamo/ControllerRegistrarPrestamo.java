package edu.pe.uls.con.libreria.cu.registrarprestamo;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.uls.con.libreria.cu.registrarprestamo.request.RequestPrestamo;
import edu.pe.uls.con.libreria.cu.registrarprestamo.response.ResponsePrestamo;

@RestController
public class ControllerRegistrarPrestamo {

    ServiceRegistrarPrestamo serviceRegistrarPrestamo;

    public ControllerRegistrarPrestamo(ServiceRegistrarPrestamo serviceRegistrarPrestamo) {
        this.serviceRegistrarPrestamo = serviceRegistrarPrestamo;
    }

    @PostMapping("/prestamo/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsePrestamo guardarPrestamo(@RequestBody RequestPrestamo prestamo) {
        return serviceRegistrarPrestamo.registrarPrestamo(prestamo);
    }

}