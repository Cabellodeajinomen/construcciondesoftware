package edu.pe.uls.con.libreria.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.uls.con.libreria.dominio.entity.Devolucion;

public interface RepoDevolucion extends JpaRepository<Devolucion, Integer> {

}
