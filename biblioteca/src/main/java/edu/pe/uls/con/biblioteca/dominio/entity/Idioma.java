package edu.pe.uls.con.biblioteca.dominio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "idioma")
public class Idioma {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_idioma")
    @SequenceGenerator(name = "sec_idioma", sequenceName = "sec_idioma", allocationSize = 1)
    private int id;

    // código ISO 639-1, por ejemplo "es", "en", "qu"
    @Column(nullable = false, length = 10, unique = true)
    private String codigo;

    @Column(nullable = false, length = 50)
    private String nombre;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
