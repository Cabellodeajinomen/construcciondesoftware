package edu.pe.uls.con.biblioteca.dominio.projection;

import java.time.LocalDate;

public record ItemPorDevolver(Integer idEjemplar, String codigoBarras, String titulo, LocalDate fechaLimite) {
}
