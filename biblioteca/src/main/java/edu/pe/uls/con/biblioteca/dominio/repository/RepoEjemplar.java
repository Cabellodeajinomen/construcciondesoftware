package edu.pe.uls.con.biblioteca.dominio.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pe.uls.con.biblioteca.dominio.entity.Ejemplar;

public interface RepoEjemplar extends JpaRepository<Ejemplar, Integer> {

    boolean existsByCodigoBarras(String codigoBarras);

    // Query nativo 1  ejemplar + publicacion + estado_disponibilidad
    @Query(value = """
        SELECT p.titulo,
               e.codigo_barras,
               ed.nombre
        FROM ejemplar e
        JOIN publicacion p ON p.id = e.id_publicacion
        JOIN estado_disponibilidad ed ON ed.id = e.id_estado_disponibilidad
        WHERE e.id_estado_disponibilidad = :idEstado
        ORDER BY p.titulo, e.codigo_barras
    """, nativeQuery = true)
    List<Object[]> buscarEjemplaresPorEstado(@Param("idEstado") int idEstado);
}
