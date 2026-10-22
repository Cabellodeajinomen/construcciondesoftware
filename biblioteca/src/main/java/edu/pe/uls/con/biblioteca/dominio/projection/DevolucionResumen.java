package edu.pe.uls.con.biblioteca.dominio.projection;

import java.time.LocalDateTime;

public record DevolucionResumen(Integer idDevolucion, LocalDateTime fechaDevolucion, Double totalMulta, Long cantidadItems) {
}
