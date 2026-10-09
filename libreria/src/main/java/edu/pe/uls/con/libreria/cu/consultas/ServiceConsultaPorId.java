package edu.pe.uls.con.libreria.cu.consultas;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import edu.pe.uls.con.libreria.dominio.entity.Ejemplar;
import edu.pe.uls.con.libreria.cu.consultas.response.ResponseConsultaDevolucion;
import edu.pe.uls.con.libreria.cu.consultas.response.ResponseConsultaDevolucionItem;
import edu.pe.uls.con.libreria.cu.consultas.response.ResponseConsultaEjemplar;
import edu.pe.uls.con.libreria.cu.consultas.response.ResponseConsultaPrestamo;
import edu.pe.uls.con.libreria.cu.consultas.response.ResponseConsultaPrestamoItem;
import edu.pe.uls.con.libreria.dominio.entity.Devolucion;
import edu.pe.uls.con.libreria.dominio.entity.DevolucionItem;
import edu.pe.uls.con.libreria.dominio.entity.Prestamo;
import edu.pe.uls.con.libreria.dominio.entity.PrestamoItem;
import edu.pe.uls.con.libreria.dominio.repository.RepoDevolucion;
import edu.pe.uls.con.libreria.dominio.repository.RepoDevolucionItem;
import edu.pe.uls.con.libreria.dominio.repository.RepoEjemplar;
import edu.pe.uls.con.libreria.dominio.repository.RepoPrestamo;
import edu.pe.uls.con.libreria.dominio.repository.RepoPrestamoItem;

@Service
@Transactional(readOnly = true)
public class ServiceConsultaPorId {

    private final RepoEjemplar repoEjemplar;
    private final RepoPrestamo repoPrestamo;
    private final RepoPrestamoItem repoPrestamoItem;
    private final RepoDevolucion repoDevolucion;
    private final RepoDevolucionItem repoDevolucionItem;

    public ServiceConsultaPorId(RepoEjemplar repoEjemplar, RepoPrestamo repoPrestamo,
            RepoPrestamoItem repoPrestamoItem, RepoDevolucion repoDevolucion,
            RepoDevolucionItem repoDevolucionItem) {
        this.repoEjemplar = repoEjemplar;
        this.repoPrestamo = repoPrestamo;
        this.repoPrestamoItem = repoPrestamoItem;
        this.repoDevolucion = repoDevolucion;
        this.repoDevolucionItem = repoDevolucionItem;
    }

    public ResponseConsultaEjemplar consultarEjemplar(int id) {
        Ejemplar ejemplar = repoEjemplar.findById(id).orElseThrow(() -> noEncontrado("Ejemplar", id));
        return new ResponseConsultaEjemplar(ejemplar.getId(), ejemplar.getLibro().getId(),
                ejemplar.getCodigoBarras(), ejemplar.getEstado().name());
    }

    public ResponseConsultaPrestamo consultarPrestamo(int id) {
        Prestamo prestamo = repoPrestamo.findById(id).orElseThrow(() -> noEncontrado("Préstamo", id));
        return new ResponseConsultaPrestamo(prestamo.getId(), prestamo.getCliente().getId(),
                prestamo.getFechaPrestamo(), prestamo.getFechaLimite(), prestamo.getItems().size());
    }

    public ResponseConsultaPrestamoItem consultarPrestamoItem(int id) {
        PrestamoItem item = repoPrestamoItem.findById(id)
                .orElseThrow(() -> noEncontrado("Ítem de préstamo", id));
        return new ResponseConsultaPrestamoItem(item.getId(), item.getPrestamo().getId(),
                item.getEjemplar().getId(), item.isDevuelto());
    }

    public ResponseConsultaDevolucion consultarDevolucion(int id) {
        Devolucion devolucion = repoDevolucion.findById(id)
                .orElseThrow(() -> noEncontrado("Devolución", id));
        return new ResponseConsultaDevolucion(devolucion.getId(), devolucion.getPrestamo().getId(),
                devolucion.getFechaDevolucion(), devolucion.getTotalMulta(), devolucion.getItems().size());
    }

    public ResponseConsultaDevolucionItem consultarDevolucionItem(int id) {
        DevolucionItem item = repoDevolucionItem.findById(id)
                .orElseThrow(() -> noEncontrado("Ítem de devolución", id));
        return new ResponseConsultaDevolucionItem(item.getId(), item.getDevolucion().getId(),
                item.getPrestamoItem().getId(), item.isDanado(), item.getDiasRetraso(), item.getMulta());
    }

    private ResponseStatusException noEncontrado(String entidad, int id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, entidad + " no encontrado: " + id);
    }
}