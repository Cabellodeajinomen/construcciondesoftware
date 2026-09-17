package edu.pe.uls.con.libreria;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryCliente extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByDni(String dni);

    Optional<Cliente> findByEmailIgnoreCase(String email);
}
