package edu.pe.uls.con.biblioteca.cu.registrarprestamo.response;

// Situación del cliente antes de prestarle: cuántos ejemplares tiene pendientes y si puede llevar más
public record ResponseClientePrestamo(int idCliente, String nombreCompleto, String dni, String tipoCliente,
        long itemsPendientes, long itemsVencidos, boolean puedePrestar) {
}
