package edu.pe.uls.con.biblioteca.cu.consultas.response;

// sirve para las tablas de catálogo que solo tienen id, nombre y descripción
// (tipo_publicacion, tipo_cliente, estado_fisico, estado_disponibilidad)
public record ResponseConsultaCatalogo(
        int id,
        String nombre,
        String descripcion) {
}
