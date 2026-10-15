package edu.pe.uls.con.biblioteca.dominio.projection;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record PrestamoResumen(Integer idPrestamo, String cliente, LocalDateTime fechaPrestamo, LocalDate fechaLimite) {
}
