package edu.pe.uls.con.biblioteca.cu.registrardevolucion.request;



import java.util.List;

public record RequestDevolucion(int idPrestamo, List<RequestDevolucionItem> items) {

    public record RequestDevolucionItem(int idEjemplar, boolean danado) {

    }
}
