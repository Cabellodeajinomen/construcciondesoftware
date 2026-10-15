package edu.pe.uls.con.biblioteca.cu.catalogo;

import java.util.List;

public record ResponseLibro(int id, String titulo, int añoPublicacion, String isbn, String edicion, int numeroPaginas,
        String idioma, String editorial, List<String> autores, List<String> categorias) {
}