package edu.pe.uls.con.biblioteca.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.uls.con.biblioteca.dominio.entity.EstadoDisponibilidad;

public interface RepoEstadoDisponibilidad extends JpaRepository<EstadoDisponibilidad, Integer> {
}
