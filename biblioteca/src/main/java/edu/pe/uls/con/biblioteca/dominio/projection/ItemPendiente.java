package edu.pe.uls.con.biblioteca.dominio.projection;

import java.time.LocalDate;

public record ItemPendiente(Integer idPrestamo, String titulo, String codigoBarras, LocalDate fechaLimite) {
}
