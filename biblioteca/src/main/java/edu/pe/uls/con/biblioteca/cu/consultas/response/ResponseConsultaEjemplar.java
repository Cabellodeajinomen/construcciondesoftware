package edu.pe.uls.con.biblioteca.cu.consultas.response;

public record ResponseConsultaEjemplar(
        int id,
        int idPublicacion,
        String titulo,
        String tipoPublicacion,
        String codigoBarras,
        int idUbicacion,
        String ubicacion,
        int idEstadoFisico,
        String estadoFisico,
        int idEstadoDisponibilidad,
        String estadoDisponibilidad,
        String nota) {
}
