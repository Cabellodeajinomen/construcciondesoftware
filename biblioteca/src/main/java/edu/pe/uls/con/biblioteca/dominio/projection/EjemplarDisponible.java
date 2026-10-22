package edu.pe.uls.con.biblioteca.dominio.projection;

public record EjemplarDisponible(Integer idEjemplar, String codigoBarras, String titulo, String ubicacion,
        String sala, String estante, String estadoFisico, String nota) {
}
