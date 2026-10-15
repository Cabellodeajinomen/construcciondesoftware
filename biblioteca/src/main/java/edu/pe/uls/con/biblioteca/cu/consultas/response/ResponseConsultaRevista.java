package edu.pe.uls.con.biblioteca.cu.consultas.response;

import java.time.LocalDate;

public record ResponseConsultaRevista(
        int id,
        String titulo,
        String issn,
        String volumen,
        String numero,
        LocalDate fechaPublicacion,
        String editorial) {
}
