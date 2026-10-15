package edu.pe.uls.con.biblioteca.dominio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Catálogo de tipos de publicación. El id es un código numérico fijo (no usa secuencia).
 */
@Entity
@Table(name = "tipo_publicacion")
public class TipoPublicacion {

    public static final int LIBRO = 1;
    public static final int REVISTA = 2;
    public static final int TESIS = 3;

    @Id
    private int id;

    @Column(nullable = false, length = 50, unique = true)
    private String nombre;

    private String descripcion;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
