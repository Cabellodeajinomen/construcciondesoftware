package edu.pe.uls.con.libreria.cu.consultas.response;

public record ResponseConsultaPrestamoItem(
        int id,
        int idPrestamo,
        int idEjemplar,
        boolean devuelto) {
}