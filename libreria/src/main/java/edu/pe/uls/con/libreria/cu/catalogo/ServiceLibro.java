package edu.pe.uls.con.libreria.cu.catalogo;

import edu.pe.uls.con.libreria.dominio.entity.*;
import edu.pe.uls.con.libreria.dominio.repository.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServiceLibro {

    @Autowired
    RepositoryLibro repoLibro;

    @Autowired
    RepositoryAutor repoAutor;

    @Autowired
    RepositoryEditorial repoEditorial;

    @Autowired
    RepositoryCategoria repoCategoria;

    @Transactional
    public Libro registrarLibro(Libro nuevo, int idAutor, int idEditorial, int idCategoria) {
        repoLibro.findByIsbn(nuevo.getIsbn()).ifPresent(l -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un libro registrado con el ISBN: " + nuevo.getIsbn());
        });
        nuevo.setAutor(repoAutor.findById(idAutor)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Autor no encontrado: " + idAutor)));
        nuevo.setEditorial(repoEditorial.findById(idEditorial)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editorial no encontrada: " + idEditorial)));
        nuevo.setCategoria(repoCategoria.findById(idCategoria)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoria no encontrada: " + idCategoria)));
        return repoLibro.save(nuevo);
    }

    public Libro consultarLibro(int id) {
        return repoLibro.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado: " + id));
    }

    public List<Libro> consultarPorAutor(String apellido) {
        return repoLibro.findByAutorApellidosContainingIgnoreCase(apellido);
    }

    public List<Libro> consultarPorTitulo(String texto) {
        return repoLibro.findByTituloContainingIgnoreCase(texto);
    }
}