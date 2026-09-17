package edu.pe.uls.con.libreria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServiceAutor {

    @Autowired
    RepositoryAutor repoAutor;

    @Transactional
    public Autor registrarAutor(Autor nuevo) {
        repoAutor.findByNombresIgnoreCaseAndApellidosIgnoreCase(nuevo.getNombres(), nuevo.getApellidos()).ifPresent(a -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un autor registrado con el nombre: " + nuevo.getNombres() + " " + nuevo.getApellidos());
        });
        return repoAutor.save(nuevo);
    }

    public Autor consultarAutor(int id) {
        return repoAutor.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado: " + id));
    }

    public List<Autor> consultarPorNacionalidad(String nacionalidad) {
        return repoAutor.findByNacionalidadIgnoreCase(nacionalidad);
    }
   
    public List<Autor> consultarPorNombre(String nombres) {
        return repoAutor.findByNombresContainingIgnoreCase(nombres);
    }
}

