package edu.pe.uls.con.libreria.dominio.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pe.uls.con.libreria.dominio.entity.Ejemplar;

public interface RepoEjemplar extends JpaRepository<Ejemplar, Integer> {

    boolean existsByCodigoBarras(String codigoBarras);

    // Query nativo 1  ejemplar + libro
    @Query(value = """
        SELECT l.titulo,
               e.codigo_barras,
               e.estado
        FROM ejemplar e
        JOIN libro l ON l.id = e.id_libro
        WHERE e.estado = :estado
        ORDER BY l.titulo, e.codigo_barras
    """, nativeQuery = true)
    List<Object[]> buscarEjemplaresPorEstado(@Param("estado") String estado);
}
