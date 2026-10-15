package edu.pe.uls.con.biblioteca.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.uls.con.biblioteca.dominio.entity.Tesis;

public interface RepoTesis extends JpaRepository<Tesis, Integer> {
}
