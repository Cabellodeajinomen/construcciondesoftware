package edu.pe.uls.con.biblioteca.cu.consultas.response;

// datos legibles de un cliente, para mostrarlos dentro de otras consultas
public record ResponseDetalleCliente(
        int id,
        String nombreCompleto,
        String dni,
        String tipoCliente) {
}
