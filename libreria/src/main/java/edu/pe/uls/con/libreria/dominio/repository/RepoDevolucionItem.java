package edu.pe.uls.con.libreria.dominio.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.uls.con.libreria.dominio.entity.DevolucionItem;

public interface RepoDevolucionItem extends JpaRepository<DevolucionItem, Integer> {
}