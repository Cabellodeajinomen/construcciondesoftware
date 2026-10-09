package edu.pe.uls.con.libreria.cu.consultas.response;

import java.time.LocalDateTime;

public record ResponseConsultaDevolucion(
        int id,
        int idPrestamo,
        LocalDateTime fechaDevolucion,
        double totalMulta,
        int cantidadItems) {
}