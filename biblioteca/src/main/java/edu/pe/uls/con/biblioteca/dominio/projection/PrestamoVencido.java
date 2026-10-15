package edu.pe.uls.con.biblioteca.dominio.projection;

import java.time.LocalDate;

public record PrestamoVencido(Integer idPrestamo, String cliente, String dni, LocalDate fechaLimite, Long itemsPendientes) {
}
