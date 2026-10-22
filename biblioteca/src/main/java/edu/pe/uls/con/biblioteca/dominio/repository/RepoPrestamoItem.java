package edu.pe.uls.con.biblioteca.dominio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pe.uls.con.biblioteca.dominio.entity.PrestamoItem;
import edu.pe.uls.con.biblioteca.dominio.projection.ItemPorDevolver;
import edu.pe.uls.con.biblioteca.dominio.projection.LibroPrestado;

public interface RepoPrestamoItem extends JpaRepository<PrestamoItem, Integer> {

    // Query 4: PrestamoItem + Ejemplar + Publicacion
    @Query("""
        SELECT new edu.pe.uls.con.biblioteca.dominio.projection.LibroPrestado(
            l.id, l.titulo, COUNT(i))
        FROM PrestamoItem i
        JOIN i.ejemplar e
        JOIN e.publicacion l
        GROUP BY l.id, l.titulo
        HAVING COUNT(i) >= :minimo
        ORDER BY COUNT(i) DESC, l.titulo
    """)
    List<LibroPrestado> buscarLibrosPrestadosAlMenos(@Param("minimo") long minimo);

    // CU Registrar devolución: PrestamoItem + Prestamo + Ejemplar + Publicacion
    @Query("""
        SELECT new edu.pe.uls.con.biblioteca.dominio.projection.ItemPorDevolver(
            e.id, e.codigoBarras, l.titulo, p.fechaLimite)
        FROM PrestamoItem i
        JOIN i.prestamo p
        JOIN i.ejemplar e
        JOIN e.publicacion l
        WHERE p.id = :idPrestamo
          AND i.devuelto = false
        ORDER BY e.codigoBarras
    """)
    List<ItemPorDevolver> buscarPendientesDePrestamo(@Param("idPrestamo") int idPrestamo);
}
