package com.actividad1.ulasalle;

import java.util.ArrayList;
import java.util.List;

public class Biblioteca {
    private int id;
    private String nombre;
    private String direccion;
    private List<Categoria> categorias;

    public Biblioteca() {}

    public Biblioteca(int id, String nombre, String direccion) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.categorias = new ArrayList<>();
        this.categorias.add(new Categoria(1, "Literatura Latinoamericana", "Obras de autores de America Latina"));
        this.categorias.add(new Categoria(2, "Ciencia Ficcion", "Novelas de ciencia ficcion y distopia"));
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public List<Categoria> getCategorias() { return categorias; }
    public void setCategorias(List<Categoria> categorias) { this.categorias = categorias; }
}