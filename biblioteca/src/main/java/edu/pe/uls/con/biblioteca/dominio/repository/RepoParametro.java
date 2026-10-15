package edu.pe.uls.con.biblioteca.dominio.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.pe.uls.con.biblioteca.dominio.entity.Parametro;

public interface RepoParametro extends JpaRepository<Parametro, Integer> {

    Optional<Parametro> findByCodigo(String codigo);

    // atajo para los servicios: si falta el parámetro es un error de configuración
    default Parametro obtener(String codigo) {
        return findByCodigo(codigo)
            .orElseThrow(() -> new IllegalStateException("Falta configurar el parámetro: " + codigo));
    }
}
