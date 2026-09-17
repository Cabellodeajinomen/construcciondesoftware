package edu.pe.uls.con.libreria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServiceEditorial {

    @Autowired
    RepositoryEditorial repoEditorial;

    @Transactional
    public Editorial registrarEditorial(Editorial nuevo) {
        repoEditorial.findByNombreIgnoreCase(nuevo.getNombre()).ifPresent(e -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una editorial registrada con el nombre: " + nuevo.getNombre());
        });
        return repoEditorial.save(nuevo);
    }

    public Editorial consultarEditorial(int id) {
        return repoEditorial.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editorial no encontrada: " + id));
    }

    public List<Editorial> consultarPorPais(String paisOrigen) {
        return repoEditorial.findByPaisOrigenIgnoreCase(paisOrigen);
    }
}
