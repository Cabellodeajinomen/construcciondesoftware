package edu.pe.uls.con.biblioteca.cu.registrarprestamo;



import org.springframework.http.HttpStatus;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.response.ResponseClientePrestamo;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.response.ResponsePrestamo;
import edu.pe.uls.con.biblioteca.dominio.projection.EjemplarDisponible;

@RestController
public class ControllerRegistrarPrestamo {

    ServiceRegistrarPrestamo serviceRegistrarPrestamo;

    public ControllerRegistrarPrestamo(ServiceRegistrarPrestamo serviceRegistrarPrestamo) {
        this.serviceRegistrarPrestamo = serviceRegistrarPrestamo;
    }

    // Paso 1: identificar al cliente y ver si puede prestarse ejemplares
    @GetMapping("/prestamo/cliente/{dni}")
    public ResponseClientePrestamo consultarCliente(@PathVariable("dni") String dni) {
        return serviceRegistrarPrestamo.consultarCliente(dni);
    }

    // Paso 2: ejemplares disponibles de la publicación que pide el cliente
    @GetMapping("/prestamo/ejemplares-disponibles/{idPublicacion}")
    public List<EjemplarDisponible> consultarEjemplaresDisponibles(@PathVariable("idPublicacion") int idPublicacion) {
        return serviceRegistrarPrestamo.consultarEjemplaresDisponibles(idPublicacion);
    }

    // Paso 3: registrar el préstamo (cabecera) con sus ejemplares (detalle), con bloqueo
    @PostMapping("/prestamo/nuevo")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsePrestamo guardarPrestamo(@RequestBody RequestPrestamo prestamo) {
        return serviceRegistrarPrestamo.registrarPrestamo(prestamo);
    }

    // solo para demostrar el problema de concurrencia: lee el ejemplar sin bloqueo
    @PostMapping("/prestamo/nuevo/sinlock")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponsePrestamo guardarPrestamoSinLock(@RequestBody RequestPrestamo prestamo) {
        return serviceRegistrarPrestamo.registrarPrestamoSinLock(prestamo);
    }

}