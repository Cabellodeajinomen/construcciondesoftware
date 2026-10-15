package edu.pe.uls.con.biblioteca.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.uls.con.biblioteca.dominio.entity.Ubicacion;

public interface RepoUbicacion extends JpaRepository<Ubicacion, Integer> {
}
