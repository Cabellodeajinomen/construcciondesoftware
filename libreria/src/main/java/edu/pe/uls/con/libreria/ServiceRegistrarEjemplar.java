package edu.pe.uls.con.libreria;



import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import edu.pe.uls.con.libreria.Ejemplar;
import edu.pe.uls.con.libreria.dominio.repository.RepoEjemplar;

@Service
public class ServiceRegistrarEjemplar {

    RepositoryLibro repoLibro;

    RepoEjemplar repoEjemplar;

    public ServiceRegistrarEjemplar(RepositoryLibro repoLibro, RepoEjemplar repoEjemplar) {
        this.repoLibro = repoLibro;
        this.repoEjemplar = repoEjemplar;
    }

    @Transactional
    public ResponseEjemplar registrarEjemplar(RequestEjemplar request) {
        Libro libro = repoLibro.findById(request.idLibro())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Libro no encontrado: " + request.idLibro()));

        if (repoEjemplar.existsByCodigoBarras(request.codigoBarras())) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT, "Ya existe un ejemplar con el código: " + request.codigoBarras());
        }

        Ejemplar e = new Ejemplar();
        e.setLibro(libro);
        e.setCodigoBarras(request.codigoBarras());
        repoEjemplar.save(e);

        return new ResponseEjemplar(e.getId(), libro.getTitulo(), e.getCodigoBarras(), e.getEstado().name());
    }
}
