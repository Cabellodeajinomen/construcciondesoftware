package edu.pe.uls.con.biblioteca.cu.consultas.response;

// datos legibles de un ejemplar, para mostrarlos dentro de otras consultas
public record ResponseDetalleEjemplar(
        int id,
        String codigoBarras,
        String titulo,
        String tipoPublicacion,
        String ubicacion,
        String estadoFisico,
        String estadoDisponibilidad,
        String nota) {
}
