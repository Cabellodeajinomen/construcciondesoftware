package edu.pe.uls.con.biblioteca.cu.consultas.response;

import java.time.LocalDateTime;
import java.util.List;

public record ResponseConsultaDevolucion(
        int id,
        int idPrestamo,
        ResponseDetalleCliente cliente,
        LocalDateTime fechaDevolucion,
        double totalMulta,
        int cantidadItems,
        List<ResponseItem> items) {

    public record ResponseItem(int idItem, boolean danado, int diasRetraso, double multa,
            ResponseDetalleEjemplar ejemplar) {}
}
