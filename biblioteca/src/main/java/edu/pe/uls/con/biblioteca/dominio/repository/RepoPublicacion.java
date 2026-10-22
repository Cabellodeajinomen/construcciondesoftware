package edu.pe.uls.con.biblioteca.dominio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import edu.pe.uls.con.biblioteca.dominio.entity.Publicacion;
import edu.pe.uls.con.biblioteca.dominio.projection.PublicacionResumen;

public interface RepoPublicacion extends JpaRepository<Publicacion, Integer> {

    // Publicacion + TipoPublicacion: publicaciones (libros, revistas y tesis) entre dos años
    @Query("""
        SELECT new edu.pe.uls.con.biblioteca.dominio.projection.PublicacionResumen(
            p.id, p.titulo, p.anioPublicacion, t.nombre)
        FROM Publicacion p
        JOIN p.tipoPublicacion t
        WHERE p.anioPublicacion BETWEEN :desde AND :hasta
        ORDER BY p.anioPublicacion, p.titulo
    """)
    List<PublicacionResumen> buscarPorRangoDeAnios(@Param("desde") int desde, @Param("hasta") int hasta);
}
