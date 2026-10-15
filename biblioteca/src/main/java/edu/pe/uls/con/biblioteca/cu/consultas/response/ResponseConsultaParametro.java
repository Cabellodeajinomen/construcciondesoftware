package edu.pe.uls.con.biblioteca.cu.consultas.response;

public record ResponseConsultaParametro(
        int id,
        String codigo,
        String valor,
        String tipoDato,
        String descripcion) {
}
