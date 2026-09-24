package edu.pe.uls.con.libreria.cu.registrarprestamo.response;

import java.time.LocalDate;
import java.util.List;

public record ResponsePrestamo(int idPrestamo, String cliente, LocalDate fechaLimite, List<ResponsePrestamoItem> items) {

    public record ResponsePrestamoItem(String titulo, String codigoBarras) {}
}

