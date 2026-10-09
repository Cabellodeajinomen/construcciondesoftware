package edu.pe.uls.con.libreria.cu.catalogo;

public record RequestLibro(String titulo, String isbn, int idAutor, int idEditorial, int idCategoria) {
}