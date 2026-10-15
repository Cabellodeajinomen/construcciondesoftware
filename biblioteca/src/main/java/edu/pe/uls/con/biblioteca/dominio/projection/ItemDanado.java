package edu.pe.uls.con.biblioteca.dominio.projection;

import java.time.LocalDateTime;

public record ItemDanado(Integer idDevolucion, String titulo, String codigoBarras, LocalDateTime fechaDevolucion) {
}
