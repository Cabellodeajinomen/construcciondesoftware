package edu.pe.uls.con.libreria.dominio.projection;

import java.time.LocalDate;

public record ItemPendiente(Integer idPrestamo, String titulo, String codigoBarras, LocalDate fechaLimite) {
}
