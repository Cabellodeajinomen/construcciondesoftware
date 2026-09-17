package edu.pe.uls.con.libreria;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryAutor extends JpaRepository<Autor, Integer> {

    Optional<Autor> findByNombresIgnoreCaseAndApellidosIgnoreCase(String nombres, String apellidos);

    List<Autor> findByNacionalidadIgnoreCase(String nacionalidad);
    
    List<Autor> findByNombresContainingIgnoreCase(String nombres);
}

