package edu.pe.uls.con.libreria.cu.consultas;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.uls.con.libreria.cu.consultas.response.ResponseConsultaDevolucion;
import edu.pe.uls.con.libreria.cu.consultas.response.ResponseConsultaDevolucionItem;
import edu.pe.uls.con.libreria.cu.consultas.response.ResponseConsultaEjemplar;
import edu.pe.uls.con.libreria.cu.consultas.response.ResponseConsultaPrestamo;
import edu.pe.uls.con.libreria.cu.consultas.response.ResponseConsultaPrestamoItem;

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
}