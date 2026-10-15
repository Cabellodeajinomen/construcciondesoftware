package edu.pe.uls.con.biblioteca.dominio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Clasifica a los clientes (estudiante, docente, externo...) para poder sacar métricas por tipo.
 */
@Entity
@Table(name = "tipo_cliente")
public class TipoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_tipo_cliente")
    @SequenceGenerator(name = "sec_tipo_cliente", sequenceName = "sec_tipo_cliente", allocationSize = 1)
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
