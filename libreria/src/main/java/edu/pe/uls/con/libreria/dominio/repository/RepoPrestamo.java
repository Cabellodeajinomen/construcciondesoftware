package edu.pe.uls.con.libreria.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.uls.con.libreria.dominio.entity.Prestamo;

public interface RepoPrestamo extends JpaRepository<Prestamo, Integer> {
}