package edu.pe.uls.con.libreria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServiceCategoria {

    @Autowired
    RepositoryCategoria repoCategoria;

    @Transactional
    public Categoria registrarCategoria(Categoria nuevo) {
        repoCategoria.findByNombreIgnoreCase(nuevo.getNombre()).ifPresent(c -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe una categoria registrada con el nombre: " + nuevo.getNombre());
        });
        return repoCategoria.save(nuevo);
    }

    public Categoria consultarCategoria(int id) {
        return repoCategoria.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada: " + id));
    }

    public List<Categoria> consultarPorNombre(String texto) {
        return repoCategoria.findByNombreContainingIgnoreCase(texto);
    }
}
