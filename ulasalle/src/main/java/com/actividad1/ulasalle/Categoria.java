package com.actividad1.ulasalle;

import java.util.ArrayList;
import java.util.List;

public class Categoria {
    private int id;
    private String nombre;
    private String descripcion;
    private List<Libro> libros;

    public Categoria() {}

    public Categoria(int id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.libros = new ArrayList<>();
        this.libros.add(new Libro(1, "Cien Años de Soledad", "Gabriel Garcia Marquez", 1967));
        this.libros.add(new Libro(2, "El Quijote", "Miguel de Cervantes", 1605));
        this.libros.add(new Libro(3, "1984", "George Orwell", 1949));
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<Libro> getLibros() { return libros; }
    public void setLibros(List<Libro> libros) { this.libros = libros; }
}