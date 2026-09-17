package edu.pe.uls.con.libreria;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCategoria extends JpaRepository<Categoria, Integer> {

    Optional<Categoria> findByNombreIgnoreCase(String nombre);

    List<Categoria> findByNombreContainingIgnoreCase(String texto);
}
