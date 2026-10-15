package edu.pe.uls.con.biblioteca.dominio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "libro")
@PrimaryKeyJoinColumn(name = "id_publicacion")
public class Libro extends Publicacion {

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(length = 50)
    private String edicion;

    @Column(name = "numero_paginas")
    private int numeroPaginas;

    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public String getEdicion() {
        return edicion;
    }
    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }
    public int getNumeroPaginas() {
        return numeroPaginas;
    }
    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }
}
