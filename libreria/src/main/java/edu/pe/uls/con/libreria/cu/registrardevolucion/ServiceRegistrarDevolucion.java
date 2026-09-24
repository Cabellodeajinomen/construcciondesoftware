package edu.pe.uls.con.libreria.cu.registrardevolucion;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import edu.pe.uls.con.libreria.cu.registrardevolucion.request.RequestDevolucion;
import edu.pe.uls.con.libreria.cu.registrardevolucion.request.RequestDevolucion.RequestDevolucionItem;
import edu.pe.uls.con.libreria.cu.registrardevolucion.response.ResponseDevolucion;
import edu.pe.uls.con.libreria.dominio.entity.Devolucion;
import edu.pe.uls.con.libreria.dominio.entity.Prestamo;
import edu.pe.uls.con.libreria.dominio.entity.PrestamoItem;
import edu.pe.uls.con.libreria.dominio.repository.RepoDevolucion;
import edu.pe.uls.con.libreria.dominio.repository.RepoPrestamo;

@Service
public class ServiceRegistrarDevolucion {

    RepoPrestamo repoPrestamo;

    RepoDevolucion repoDevolucion;

    public ServiceRegistrarDevolucion(RepoPrestamo repoPrestamo, RepoDevolucion repoDevolucion) {
        this.repoPrestamo = repoPrestamo;
        this.repoDevolucion = repoDevolucion;
    }

    // Devolución parcial o total de un préstamo, en una sola transacción
    @Transactional
    public ResponseDevolucion registrarDevolucion(RequestDevolucion request) {
        if (request.items() == null || request.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La devolución debe tener al menos un ítem");
        }

        Prestamo prestamo = repoPrestamo.findById(request.idPrestamo())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Préstamo no encontrado: " + request.idPrestamo()));

        Devolucion d = new Devolucion();
        d.setPrestamo(prestamo);
        for (RequestDevolucionItem item : request.items()) {
            PrestamoItem prestamoItem = prestamo.buscarItemPendiente(item.idEjemplar())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "El ejemplar " + item.idEjemplar() + " no está pendiente en el préstamo " + prestamo.getId()));
            d.agregarItem(prestamoItem, item.danado());
        }
        repoDevolucion.save(d);

        List<ResponseDevolucion.ResponseDevolucionItem> lst = new ArrayList<>();
        d.getItems().forEach(i -> lst.add(new ResponseDevolucion.ResponseDevolucionItem(
            i.getPrestamoItem().getEjemplar().getLibro().getTitulo(),
            i.getPrestamoItem().getEjemplar().getCodigoBarras(),
            i.getDiasRetraso(),
            i.getMulta(),
            i.isDanado())));

        return new ResponseDevolucion(d.getId(), prestamo.getId(), d.getTotalMulta(), lst);
    }
}

