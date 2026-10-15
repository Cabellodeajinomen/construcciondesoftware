package edu.pe.uls.con.biblioteca.cu.catalogo;

import edu.pe.uls.con.biblioteca.dominio.entity.*;
import edu.pe.uls.con.biblioteca.dominio.repository.*;

import java.util.HashSet;
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

    @Autowired
    RepoIdioma repoIdioma;

    @Autowired
    RepoTipoPublicacion repoTipoPublicacion;

    @Transactional
    public Libro registrarLibro(Libro nuevo, int idIdioma, int idEditorial, List<Integer> idAutores, List<Integer> idCategorias) {
        repoLibro.findByIsbn(nuevo.getIsbn()).ifPresent(l -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un libro registrado con el ISBN: " + nuevo.getIsbn());
        });
        if (idAutores == null || idAutores.isEmpty() || idCategorias == null || idCategorias.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El libro debe tener al menos un autor y una categoria");
        }
        nuevo.setTipoPublicacion(repoTipoPublicacion.getReferenceById(TipoPublicacion.LIBRO));
        nuevo.setIdioma(repoIdioma.findById(idIdioma)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Idioma no encontrado: " + idIdioma)));
        nuevo.setEditorial(repoEditorial.findById(idEditorial)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Editorial no encontrada: " + idEditorial)));

        List<Autor> autores = repoAutor.findAllById(idAutores);
        if (autores.size() != new HashSet<>(idAutores).size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Algún autor no existe: " + idAutores);
        }
        List<Categoria> categorias = repoCategoria.findAllById(idCategorias);
        if (categorias.size() != new HashSet<>(idCategorias).size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Alguna categoria no existe: " + idCategorias);
        }
        nuevo.getAutores().addAll(autores);
        nuevo.getCategorias().addAll(categorias);
        return repoLibro.save(nuevo);
    }

    public Libro consultarLibro(int id) {
        return repoLibro.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Libro no encontrado: " + id));
    }

    public List<Libro> consultarPorAutor(String apellido) {
        return repoLibro.findDistinctByAutoresApellidosContainingIgnoreCase(apellido);
    }

    public List<Libro> consultarPorTitulo(String texto) {
        return repoLibro.findByTituloContainingIgnoreCase(texto);
    }
}