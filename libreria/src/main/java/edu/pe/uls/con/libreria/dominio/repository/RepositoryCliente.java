package edu.pe.uls.con.libreria.dominio.repository;

import edu.pe.uls.con.libreria.dominio.entity.Cliente;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCliente extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByDni(String dni);

    Optional<Cliente> findByEmailIgnoreCase(String email);
}
