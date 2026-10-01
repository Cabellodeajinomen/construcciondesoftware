package edu.pe.uls.con.libreria.dominio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pe.uls.con.libreria.dominio.entity.PrestamoItem;
import edu.pe.uls.con.libreria.dominio.projection.LibroPrestado;

public interface RepoPrestamoItem extends JpaRepository<PrestamoItem, Integer> {

    // Query 4: PrestamoItem + Ejemplar + Libro
    @Query("""
        SELECT new edu.pe.uls.con.libreria.dominio.projection.LibroPrestado(
            l.id, l.titulo, COUNT(i))
        FROM PrestamoItem i
        JOIN i.ejemplar e
        JOIN e.libro l
        GROUP BY l.id, l.titulo
        HAVING COUNT(i) >= :minimo
        ORDER BY COUNT(i) DESC, l.titulo
    """)
    List<LibroPrestado> buscarLibrosPrestadosAlMenos(@Param("minimo") long minimo);
}
