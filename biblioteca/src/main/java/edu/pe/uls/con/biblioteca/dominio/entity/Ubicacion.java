package edu.pe.uls.con.biblioteca.dominio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Lugar físico (stand) donde se guarda un ejemplar: sala, estante, nivel y sección.
 */
@Entity
@Table(name = "ubicacion")
public class Ubicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_ubicacion")
    @SequenceGenerator(name = "sec_ubicacion", sequenceName = "sec_ubicacion", allocationSize = 1)
    private int id;

    @Column(nullable = false, length = 20, unique = true)
    private String codigo;

    @Column(length = 50)
    private String sala;

    @Column(length = 50)
    private String estante;

    @Column(length = 20)
    private String nivel;

    @Column(length = 50)
    private String seccion;

    // texto legible, por ejemplo: "SI-B1-N3 (Sala de Ingeniería, estante B1, nivel 3, sección Software)"
    public String getDescripcionCompleta() {
        return codigo + " (" + sala + ", estante " + estante + ", nivel " + nivel + ", sección " + seccion + ")";
    }

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
    public String getSala() {
        return sala;
    }
    public void setSala(String sala) {
        this.sala = sala;
    }
    public String getEstante() {
        return estante;
    }
    public void setEstante(String estante) {
        this.estante = estante;
    }
    public String getNivel() {
        return nivel;
    }
    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    public String getSeccion() {
        return seccion;
    }
    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }
}
