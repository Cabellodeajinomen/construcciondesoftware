package edu.pe.uls.con.libreria;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryLibro extends JpaRepository<Libro, Integer> {

    Optional<Libro> findByIsbn(String isbn);

    List<Libro> findByAutorIgnoreCase(String autor);

    List<Libro> findByTituloContainingIgnoreCase(String texto);
}