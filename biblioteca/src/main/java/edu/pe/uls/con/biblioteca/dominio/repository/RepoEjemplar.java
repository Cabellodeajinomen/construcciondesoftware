package edu.pe.uls.con.biblioteca.dominio.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pe.uls.con.biblioteca.dominio.entity.Ejemplar;
import edu.pe.uls.con.biblioteca.dominio.projection.EjemplarDisponible;
import jakarta.persistence.LockModeType;

public interface RepoEjemplar extends JpaRepository<Ejemplar, Integer> {

    boolean existsByCodigoBarras(String codigoBarras);

    // SELECT ... FOR UPDATE: bloquea la fila del ejemplar hasta que termine la transacción,
    // así dos préstamos simultáneos del mismo ejemplar no pueden leerlo "Disponible" a la vez
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Ejemplar e WHERE e.id = :id")
    Optional<Ejemplar> findByIdForUpdate(@Param("id") int id);

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

    // CU Registrar préstamo: Ejemplar + Publicacion + Ubicacion + EstadoFisico + EstadoDisponibilidad
    @Query("""
        SELECT new edu.pe.uls.con.biblioteca.dominio.projection.EjemplarDisponible(
            e.id, e.codigoBarras, p.titulo, u.codigo, u.sala, u.estante, ef.nombre, e.nota)
        FROM Ejemplar e
        JOIN e.publicacion p
        JOIN e.ubicacion u
        JOIN e.estadoFisico ef
        JOIN e.estadoDisponibilidad ed
        WHERE p.id = :idPublicacion
          AND ed.id = :idEstado
        ORDER BY e.codigoBarras
    """)
    List<EjemplarDisponible> buscarPorPublicacionYEstado(@Param("idPublicacion") int idPublicacion,
            @Param("idEstado") int idEstado);
}
