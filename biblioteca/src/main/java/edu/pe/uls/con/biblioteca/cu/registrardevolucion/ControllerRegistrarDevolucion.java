package edu.pe.uls.con.biblioteca.cu.registrardevolucion;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.uls.con.biblioteca.cu.registrardevolucion.request.RequestDevolucion;
import edu.pe.uls.con.biblioteca.cu.registrardevolucion.response.ResponseDevolucion;
import edu.pe.uls.con.biblioteca.cu.registrardevolucion.response.ResponseItemPorDevolver;
import edu.pe.uls.con.biblioteca.dominio.projection.DevolucionResumen;

@RestController
public class ControllerRegistrarDevolucion {

    ServiceRegistrarDevolucion serviceRegistrarDevolucion;

    public ControllerRegistrarDevolucion(ServiceRegistrarDevolucion serviceRegistrarDevolucion) {
        this.serviceRegistrarDevolucion = serviceRegistrarDevolucion;
    }

    // Paso 1: ejemplares del préstamo que faltan devolver, con su multa estimada
    @GetMapping("/devolucion/prestamo/{idPrestamo}/pendientes")
    public List<ResponseItemPorDevolver> consultarPendientes(@PathVariable("idPrestamo") int idPrestamo) {
        return serviceRegistrarDevolucion.consultarPendientes(idPrestamo);
    }

    // Paso 2: registrar la devolución (cabecera) con sus ejemplares (detalle)
    @PostMapping("/devolucion/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseDevolucion guardarDevolucion(@RequestBody RequestDevolucion devolucion) {
        return serviceRegistrarDevolucion.registrarDevolucion(devolucion);
    }

    // Paso 3: historial de devoluciones del préstamo
    @GetMapping("/devolucion/prestamo/{idPrestamo}")
    public List<DevolucionResumen> consultarDevolucionesDePrestamo(@PathVariable("idPrestamo") int idPrestamo) {
        return serviceRegistrarDevolucion.consultarDevolucionesDePrestamo(idPrestamo);
    }

}
