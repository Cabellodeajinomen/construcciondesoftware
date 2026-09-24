package edu.pe.uls.con.libreria.dominio.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.uls.con.libreria.Ejemplar;

public interface RepoEjemplar extends JpaRepository<Ejemplar, Integer> {

    boolean existsByCodigoBarras(String codigoBarras);
}
