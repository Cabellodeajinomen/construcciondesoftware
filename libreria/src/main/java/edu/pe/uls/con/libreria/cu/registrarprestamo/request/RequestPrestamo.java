package edu.pe.uls.con.libreria.cu.registrarprestamo.request;

import java.util.List;

public record RequestPrestamo(int idCliente, List<RequestPrestamoItem> items) {

    public record RequestPrestamoItem(int idEjemplar) {

    }
}