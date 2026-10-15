package edu.pe.uls.con.biblioteca.cu.consultas;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaCatalogo;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaDevolucion;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaDevolucionItem;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaEjemplar;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaIdioma;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaParametro;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaPrestamo;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaPrestamoItem;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaPublicacion;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaReserva;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaRevista;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaTesis;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaUbicacion;

@RestController
public class ControllerConsultaPorId {

    private final ServiceConsultaPorId service;

    public ControllerConsultaPorId(ServiceConsultaPorId service) {
        this.service = service;
    }

    @GetMapping("/ejemplar/{id}")
    public ResponseConsultaEjemplar consultarEjemplar(@PathVariable int id) {
        return service.consultarEjemplar(id);
    }

    @GetMapping("/prestamo/{id}")
    public ResponseConsultaPrestamo consultarPrestamo(@PathVariable int id) {
        return service.consultarPrestamo(id);
    }

    @GetMapping("/prestamo-item/{id}")
    public ResponseConsultaPrestamoItem consultarPrestamoItem(@PathVariable int id) {
        return service.consultarPrestamoItem(id);
    }

    @GetMapping("/devolucion/{id}")
    public ResponseConsultaDevolucion consultarDevolucion(@PathVariable int id) {
        return service.consultarDevolucion(id);
    }

    @GetMapping("/devolucion-item/{id}")
    public ResponseConsultaDevolucionItem consultarDevolucionItem(@PathVariable int id) {
        return service.consultarDevolucionItem(id);
    }

    @GetMapping("/tipo-publicacion/{id}")
    public ResponseConsultaCatalogo consultarTipoPublicacion(@PathVariable int id) {
        return service.consultarTipoPublicacion(id);
    }

    @GetMapping("/idioma/{id}")
    public ResponseConsultaIdioma consultarIdioma(@PathVariable int id) {
        return service.consultarIdioma(id);
    }

    @GetMapping("/publicacion/{id}")
    public ResponseConsultaPublicacion consultarPublicacion(@PathVariable int id) {
        return service.consultarPublicacion(id);
    }

    @GetMapping("/revista/{id}")
    public ResponseConsultaRevista consultarRevista(@PathVariable int id) {
        return service.consultarRevista(id);
    }

    @GetMapping("/tesis/{id}")
    public ResponseConsultaTesis consultarTesis(@PathVariable int id) {
        return service.consultarTesis(id);
    }

    @GetMapping("/tipo-cliente/{id}")
    public ResponseConsultaCatalogo consultarTipoCliente(@PathVariable int id) {
        return service.consultarTipoCliente(id);
    }

    @GetMapping("/ubicacion/{id}")
    public ResponseConsultaUbicacion consultarUbicacion(@PathVariable int id) {
        return service.consultarUbicacion(id);
    }

    @GetMapping("/estado-fisico/{id}")
    public ResponseConsultaCatalogo consultarEstadoFisico(@PathVariable int id) {
        return service.consultarEstadoFisico(id);
    }

    @GetMapping("/estado-disponibilidad/{id}")
    public ResponseConsultaCatalogo consultarEstadoDisponibilidad(@PathVariable int id) {
        return service.consultarEstadoDisponibilidad(id);
    }

    @GetMapping("/reserva/{id}")
    public ResponseConsultaReserva consultarReserva(@PathVariable int id) {
        return service.consultarReserva(id);
    }

    @GetMapping("/parametro/{id}")
    public ResponseConsultaParametro consultarParametro(@PathVariable int id) {
        return service.consultarParametro(id);
    }
}
