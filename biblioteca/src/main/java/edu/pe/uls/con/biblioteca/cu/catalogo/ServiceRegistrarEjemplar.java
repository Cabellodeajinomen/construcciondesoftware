package edu.pe.uls.con.biblioteca.cu.catalogo;



import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import edu.pe.uls.con.biblioteca.dominio.entity.*;
import edu.pe.uls.con.biblioteca.dominio.repository.*;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEjemplar;

@Service
public class ServiceRegistrarEjemplar {

    RepoPublicacion repoPublicacion;

    RepoEjemplar repoEjemplar;

    RepoUbicacion repoUbicacion;

    RepoEstadoFisico repoEstadoFisico;

    RepoEstadoDisponibilidad repoEstadoDisponibilidad;

    public ServiceRegistrarEjemplar(RepoPublicacion repoPublicacion, RepoEjemplar repoEjemplar, RepoUbicacion repoUbicacion,
            RepoEstadoFisico repoEstadoFisico, RepoEstadoDisponibilidad repoEstadoDisponibilidad) {
        this.repoPublicacion = repoPublicacion;
        this.repoEjemplar = repoEjemplar;
        this.repoUbicacion = repoUbicacion;
        this.repoEstadoFisico = repoEstadoFisico;
        this.repoEstadoDisponibilidad = repoEstadoDisponibilidad;
    }

    @Transactional
    public ResponseEjemplar registrarEjemplar(RequestEjemplar request) {
        Publicacion publicacion = repoPublicacion.findById(request.idPublicacion())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Publicación no encontrada: " + request.idPublicacion()));
        Ubicacion ubicacion = repoUbicacion.findById(request.idUbicacion())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Ubicación no encontrada: " + request.idUbicacion()));

        if (repoEjemplar.existsByCodigoBarras(request.codigoBarras())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Ya existe un ejemplar con el código: " + request.codigoBarras());
        }

        // todo ejemplar nuevo entra en buen estado y disponible
        Ejemplar e = new Ejemplar();
        e.setPublicacion(publicacion);
        e.setCodigoBarras(request.codigoBarras());
        e.setUbicacion(ubicacion);
        e.setNota(request.nota());
        e.setEstadoFisico(repoEstadoFisico.findById(EstadoFisico.BUENO).orElseThrow());
        e.setEstadoDisponibilidad(repoEstadoDisponibilidad.findById(EstadoDisponibilidad.DISPONIBLE).orElseThrow());
        repoEjemplar.save(e);

        return new ResponseEjemplar(e.getId(), publicacion.getTitulo(), e.getCodigoBarras(), ubicacion.getCodigo(),
            e.getEstadoFisico().getNombre(), e.getEstadoDisponibilidad().getNombre(), e.getNota());
    }
}
