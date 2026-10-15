package edu.pe.uls.con.biblioteca.cu.consultas.response;

import java.time.LocalDateTime;

public record ResponseConsultaDevolucionItem(
        int id,
        int idDevolucion,
        int idPrestamoItem,
        boolean danado,
        int diasRetraso,
        double multa,
        LocalDateTime fechaDevolucion,
        ResponseDetalleCliente cliente,
        ResponseDetalleEjemplar ejemplar) {
}
