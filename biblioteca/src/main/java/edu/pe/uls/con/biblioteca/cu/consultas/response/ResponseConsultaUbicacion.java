package edu.pe.uls.con.biblioteca.cu.consultas.response;

public record ResponseConsultaUbicacion(
        int id,
        String codigo,
        String sala,
        String estante,
        String nivel,
        String seccion) {
}
