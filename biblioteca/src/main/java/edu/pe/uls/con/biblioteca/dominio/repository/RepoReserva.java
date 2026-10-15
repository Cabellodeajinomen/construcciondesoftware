package edu.pe.uls.con.biblioteca.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.uls.con.biblioteca.dominio.entity.Reserva;

public interface RepoReserva extends JpaRepository<Reserva, Integer> {
}
