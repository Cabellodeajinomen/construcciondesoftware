package edu.pe.uls.con.libreria.cu.consultas.response;

public record ResponseConsultaEjemplar(
        int id,
        int idLibro,
        String codigoBarras,
        String estado) {
}