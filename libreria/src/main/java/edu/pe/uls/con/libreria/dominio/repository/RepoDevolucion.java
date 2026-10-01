package edu.pe.uls.con.libreria.dominio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.pe.uls.con.libreria.dominio.entity.Devolucion;
import edu.pe.uls.con.libreria.dominio.projection.ItemDanado;
import edu.pe.uls.con.libreria.dominio.projection.MultaCliente;

public interface RepoDevolucion extends JpaRepository<Devolucion, Integer> {

    // Query 5 (JPQL): Devolucion + Prestamo + Cliente
    @Query("""
        SELECT new edu.pe.uls.con.libreria.dominio.projection.MultaCliente(
            c.dni, c.nombreCompleto, SUM(d.totalMulta))
        FROM Devolucion d
        JOIN d.prestamo p
        JOIN p.cliente c
        GROUP BY c.dni, c.nombreCompleto
        HAVING SUM(d.totalMulta) > 0
        ORDER BY SUM(d.totalMulta) DESC
    """)
    List<MultaCliente> obtenerTotalMultasPorCliente();

    // Query 6 (JPQL): Devolucion + DevolucionItem + PrestamoItem + Ejemplar + Libro
    @Query("""
        SELECT new edu.pe.uls.con.libreria.dominio.projection.ItemDanado(
            d.id, l.titulo, e.codigoBarras, d.fechaDevolucion)
        FROM Devolucion d
        JOIN d.items di
        JOIN di.prestamoItem pi
        JOIN pi.ejemplar e
        JOIN e.libro l
        WHERE di.danado = true
        ORDER BY d.fechaDevolucion DESC
    """)
    List<ItemDanado> buscarItemsDevueltosDanados();
}
