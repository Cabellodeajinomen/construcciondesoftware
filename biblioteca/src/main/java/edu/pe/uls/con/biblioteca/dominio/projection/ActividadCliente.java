package edu.pe.uls.con.biblioteca.dominio.projection;

public record ActividadCliente(Integer idCliente, String cliente, long totalPrestamos, long totalLibros) {
}
