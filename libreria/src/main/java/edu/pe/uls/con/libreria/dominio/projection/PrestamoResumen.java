package edu.pe.uls.con.libreria.dominio.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PrestamoResumen(Integer idPrestamo, String cliente, LocalDateTime fechaPrestamo, LocalDate fechaLimite) {
}
