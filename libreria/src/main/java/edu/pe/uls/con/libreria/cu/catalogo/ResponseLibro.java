package edu.pe.uls.con.libreria.cu.catalogo;

public record ResponseLibro(int id, String titulo, String isbn, String autor, String editorial, String categoria) {
}