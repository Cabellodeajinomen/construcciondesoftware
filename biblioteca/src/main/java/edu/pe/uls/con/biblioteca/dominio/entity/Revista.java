package edu.pe.uls.con.biblioteca.dominio.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "revista")
@PrimaryKeyJoinColumn(name = "id_publicacion")
public class Revista extends Publicacion {

    @Column(length = 20)
    private String issn;

    @Column(length = 50)
    private String volumen;

    @Column(length = 50)
    private String numero;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    public String getIssn() {
        return issn;
    }
    public void setIssn(String issn) {
        this.issn = issn;
    }
    public String getVolumen() {
        return volumen;
    }
    public void setVolumen(String volumen) {
        this.volumen = volumen;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }
    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
}
