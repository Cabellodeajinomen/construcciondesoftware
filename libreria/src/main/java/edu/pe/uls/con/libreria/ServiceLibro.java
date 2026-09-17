package edu.pe.uls.con.libreria;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceLibro {

    @Autowired
    RepositoryLibro repoLibro;

    public Libro registrarLibro(Libro nuevo) {
        repoLibro.findByIsbn(nuevo.getIsbn()).ifPresent(l -> {
            throw new RuntimeException("Ya existe un libro registrado con el ISBN: " + nuevo.getIsbn());
        });
        return repoLibro.save(nuevo);
    }

    public Libro consultarLibro(int id) {
        return repoLibro.findById(id).get();
    }

    public List<Libro> consultarPorAutor(String autor) {
        return repoLibro.findByAutorIgnoreCase(autor);
    }

    public List<Libro> consultarPorTitulo(String texto) {
        return repoLibro.findByTituloContainingIgnoreCase(texto);
    }
}