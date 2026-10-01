package edu.pe.uls.con.libreria.dominio.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pe.uls.con.libreria.dominio.entity.Prestamo;
import edu.pe.uls.con.libreria.dominio.projection.ItemPendiente;
import edu.pe.uls.con.libreria.dominio.projection.PrestamoResumen;
import edu.pe.uls.con.libreria.dominio.projection.PrestamoVencido;

public interface RepoPrestamo extends JpaRepository<Prestamo, Integer> {

    // Prestamo + Cliente
    @Query("""
        SELECT new edu.pe.uls.con.libreria.dominio.projection.PrestamoResumen(
            p.id, c.nombreCompleto, p.fechaPrestamo, p.fechaLimite)
        FROM Prestamo p
        JOIN p.cliente c
        WHERE c.dni = :dni
        ORDER BY p.fechaPrestamo DESC
    """)
    List<PrestamoResumen> buscarPorDniCliente(@Param("dni") String dni);

    //  Prestamo + PrestamoItem + Ejemplar + Libro
    @Query("""
        SELECT new edu.pe.uls.con.libreria.dominio.projection.ItemPendiente(
            p.id, l.titulo, e.codigoBarras, p.fechaLimite)
        FROM Prestamo p
        JOIN p.items i
        JOIN i.ejemplar e
        JOIN e.libro l
        WHERE p.cliente.id = :idCliente
          AND i.devuelto = false
        ORDER BY p.fechaLimite, l.titulo
    """)
    List<ItemPendiente> buscarItemsPendientesPorCliente(@Param("idCliente") int idCliente);

    // Query 3 (JPQL, con parámetro): Prestamo + Cliente + PrestamoItem
    @Query("""
        SELECT new edu.pe.uls.con.libreria.dominio.projection.PrestamoVencido(
            p.id, c.nombreCompleto, c.dni, p.fechaLimite, COUNT(i))
        FROM Prestamo p
        JOIN p.cliente c
        JOIN p.items i
        WHERE i.devuelto = false
          AND p.fechaLimite < :hoy
        GROUP BY p.id, c.nombreCompleto, c.dni, p.fechaLimite
        ORDER BY p.fechaLimite
    """)
    List<PrestamoVencido> buscarVencidosConItemsPendientes(@Param("hoy") LocalDate hoy);

    //  cliente + prestamo + prestamo_item
    @Query(value = """
        SELECT c.id,
               c.nombre_completo,
               COUNT(DISTINCT p.id) AS total_prestamos,
               COUNT(pi.id)         AS total_libros
        FROM cliente c
        LEFT JOIN prestamo p       ON p.id_cliente = c.id
        LEFT JOIN prestamo_item pi ON pi.id_prestamo = p.id
        GROUP BY c.id, c.nombre_completo
        ORDER BY total_libros DESC, c.nombre_completo
    """, nativeQuery = true)
    List<Object[]> obtenerActividadPorCliente();
}
