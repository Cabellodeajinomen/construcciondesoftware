package edu.pe.uls.con.libreria.dominio.projection;

public record ActividadCliente(Integer idCliente, String cliente, long totalPrestamos, long totalLibros) {
}
