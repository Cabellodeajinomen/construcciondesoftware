package edu.pe.uls.con.libreria;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_libro")
    @SequenceGenerator(name = "sec_libro", sequenceName = "sec_libro", allocationSize = 1)
    private int id;
    private String titulo;
    private String autor;
    private String isbn;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public String getAutor() {
        return autor;
    }
    public void setAutor(String autor) {
        this.autor = autor;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}