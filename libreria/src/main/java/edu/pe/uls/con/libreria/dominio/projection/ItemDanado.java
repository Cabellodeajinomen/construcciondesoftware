package edu.pe.uls.con.libreria.dominio.projection;

import java.time.LocalDateTime;

public record ItemDanado(Integer idDevolucion, String titulo, String codigoBarras, LocalDateTime fechaDevolucion) {
}
