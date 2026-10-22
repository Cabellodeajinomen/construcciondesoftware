package edu.pe.uls.con.biblioteca.cu.consultas.response;

import java.util.List;

public record ResponseConsultaPublicacion(
        int id,
        String titulo,
        int anioPublicacion,
        String descripcion,
        String tipoPublicacion,
        String idioma,
        String editorial,
        List<String> autores,
        List<String> categorias) {
}
