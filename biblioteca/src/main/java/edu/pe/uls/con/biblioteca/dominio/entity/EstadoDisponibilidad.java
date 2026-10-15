package edu.pe.uls.con.biblioteca.dominio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Estado de disponibilidad del ejemplar. Se maneja con códigos numéricos fijos (no usa secuencia).
 */
@Entity
@Table(name = "estado_disponibilidad")
public class EstadoDisponibilidad {

    public static final int DISPONIBLE = 1;
    public static final int PRESTADO = 2;
    public static final int RESERVADO = 3;
    public static final int NO_DISPONIBLE = 4;

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
