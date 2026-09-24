package edu.pe.uls.con.libreria.cu.registrardevolucion.response;

import java.util.List;

public record ResponseDevolucion(int idDevolucion, int idPrestamo, double totalMulta, List<ResponseDevolucionItem> items) {

    public record ResponseDevolucionItem(String titulo, String codigoBarras, int diasRetraso, double multa, boolean danado) {}
}

