package edu.pe.uls.con.libreria;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Editorial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_editorial")
    @SequenceGenerator(name = "sec_editorial", sequenceName = "sec_editorial", allocationSize = 1)
    private int id;
    @Column(unique = true, nullable = false)
    private String nombre;
    private String paisOrigen;
    private int anioFundacion;

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
    public String getPaisOrigen() {
        return paisOrigen;
    }
    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
    public int getAnioFundacion() {
        return anioFundacion;
    }
    public void setAnioFundacion(int anioFundacion) {
        this.anioFundacion = anioFundacion;
    }
}
