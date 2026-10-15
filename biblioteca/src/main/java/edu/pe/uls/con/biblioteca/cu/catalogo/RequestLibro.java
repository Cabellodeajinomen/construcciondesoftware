package edu.pe.uls.con.biblioteca.cu.catalogo;

import java.util.List;

public record RequestLibro(String titulo, int añoPublicacion, String descripcion, String isbn, String edicion,
        int numeroPaginas, int idIdioma, int idEditorial, List<Integer> idAutores, List<Integer> idCategorias) {
}