package edu.pe.uls.con.libreria.dominio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.uls.con.libreria.dominio.entity.Libro;

public interface RepositoryLibro extends JpaRepository<Libro, Integer> {

    Optional<Libro> findByIsbn(String isbn);

    List<Libro> findByAutorIgnoreCase(String autor);

    List<Libro> findByTituloContainingIgnoreCase(String texto);
}