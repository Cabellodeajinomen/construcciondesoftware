package edu.pe.uls.con.libreria.cu.consultas.response;

public record ResponseConsultaDevolucionItem(
        int id,
        int idDevolucion,
        int idPrestamoItem,
        boolean danado,
        int diasRetraso,
        double multa) {
}