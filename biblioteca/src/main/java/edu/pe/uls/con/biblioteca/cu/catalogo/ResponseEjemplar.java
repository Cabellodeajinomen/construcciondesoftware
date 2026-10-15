package edu.pe.uls.con.biblioteca.cu.catalogo;


public record ResponseEjemplar(int idEjemplar, String titulo, String codigoBarras, String ubicacion,
        String estadoFisico, String estadoDisponibilidad, String nota) {

}
