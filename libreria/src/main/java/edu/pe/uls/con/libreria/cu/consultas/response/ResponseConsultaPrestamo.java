package edu.pe.uls.con.libreria.cu.consultas.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ResponseConsultaPrestamo(
        int id,
        int idCliente,
        LocalDateTime fechaPrestamo,
        LocalDate fechaLimite,
        int cantidadItems) {
}