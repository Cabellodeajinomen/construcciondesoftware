package edu.pe.uls.con.libreria.dominio.projection;

import java.time.LocalDate;

public record PrestamoVencido(Integer idPrestamo, String cliente, String dni, LocalDate fechaLimite, Long itemsPendientes) {
}
