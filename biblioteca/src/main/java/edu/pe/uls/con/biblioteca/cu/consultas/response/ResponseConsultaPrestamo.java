package edu.pe.uls.con.biblioteca.cu.consultas.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ResponseConsultaPrestamo(
        int id,
        int idCliente,
        ResponseDetalleCliente cliente,
        LocalDateTime fechaPrestamo,
        LocalDate fechaLimite,
        int cantidadItems,
        List<ResponseItem> items) {

    public record ResponseItem(int idItem, boolean devuelto, ResponseDetalleEjemplar ejemplar) {}
}
