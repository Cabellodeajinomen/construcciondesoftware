package edu.pe.uls.con.biblioteca.cu.registrardevolucion.response;

import java.time.LocalDate;

// Ejemplar que el cliente todavía tiene, con la multa que pagaría si lo devuelve hoy
public record ResponseItemPorDevolver(int idEjemplar, String codigoBarras, String titulo, LocalDate fechaLimite,
        long diasRetraso, double multaEstimada) {
}
