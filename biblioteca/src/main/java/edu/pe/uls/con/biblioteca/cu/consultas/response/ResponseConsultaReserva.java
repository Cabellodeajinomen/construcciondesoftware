package edu.pe.uls.con.biblioteca.cu.consultas.response;

import java.time.LocalDateTime;

public record ResponseConsultaReserva(
        int id,
        int idCliente,
        int idPublicacion,
        Integer idEjemplar,
        ResponseDetalleCliente cliente,
        String titulo,
        String tipoPublicacion,
        ResponseDetalleEjemplar ejemplar,
        LocalDateTime fechaReserva,
        LocalDateTime fechaVencimiento,
        int estado,
        String nombreEstado,
        LocalDateTime fechaAtencion) {
}
