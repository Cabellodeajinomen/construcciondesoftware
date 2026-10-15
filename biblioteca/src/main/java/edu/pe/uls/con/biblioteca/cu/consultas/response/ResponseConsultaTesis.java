package edu.pe.uls.con.biblioteca.cu.consultas.response;

import java.util.List;

public record ResponseConsultaTesis(
        int id,
        String titulo,
        int añoPublicacion,
        String gradoAcademico,
        String institucion,
        String asesor,
        List<String> autores) {
}
