package edu.pe.uls.con.biblioteca.cu.consultas.response;

import java.time.LocalDate;

public record ResponseConsultaPrestamoItem(
        int id,
        int idPrestamo,
        int idEjemplar,
        boolean devuelto,
        LocalDate fechaLimite,
        ResponseDetalleCliente cliente,
        ResponseDetalleEjemplar ejemplar) {
}
