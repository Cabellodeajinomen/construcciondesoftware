package edu.pe.uls.con.biblioteca.dominio.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tesis")
@PrimaryKeyJoinColumn(name = "id_publicacion")
public class Tesis extends Publicacion {

    @Column(name = "grado_academico", length = 100)
    private String gradoAcademico;

    private String institucion;

    private String asesor;

    public String getGradoAcademico() {
        return gradoAcademico;
    }
    public void setGradoAcademico(String gradoAcademico) {
        this.gradoAcademico = gradoAcademico;
    }
    public String getInstitucion() {
        return institucion;
    }
    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }
    public String getAsesor() {
        return asesor;
    }
    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }
}
