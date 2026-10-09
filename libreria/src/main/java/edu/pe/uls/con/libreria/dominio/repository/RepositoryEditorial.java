package edu.pe.uls.con.libreria.dominio.repository;

import edu.pe.uls.con.libreria.dominio.entity.Editorial;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryEditorial extends JpaRepository<Editorial, Integer> {

    Optional<Editorial> findByNombreIgnoreCase(String nombre);

    List<Editorial> findByPaisOrigenIgnoreCase(String paisOrigen);
}

