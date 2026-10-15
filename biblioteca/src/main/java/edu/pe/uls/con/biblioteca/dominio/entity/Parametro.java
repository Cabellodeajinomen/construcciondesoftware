package edu.pe.uls.con.biblioteca.dominio.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

/**
 * Valores configurables del sistema (días de préstamo, multa diaria...).
 * Así se cambian las reglas desde la BD sin tocar el código.
 */
@Entity
@Table(name = "parametro")
public class Parametro {

    public static final String MAX_PRESTAMOS = "MAX_PRESTAMOS";
    public static final String DIAS_PRESTAMO = "DIAS_PRESTAMO";
    public static final String MULTA_DIARIA = "MULTA_DIARIA";
    public static final String DIAS_RESERVA = "DIAS_RESERVA";
    public static final String MAX_RESERVAS = "MAX_RESERVAS";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_parametro")
    @SequenceGenerator(name = "sec_parametro", sequenceName = "sec_parametro", allocationSize = 1)
    private int id;

    @Column(nullable = false, length = 50, unique = true)
    private String codigo;

    @Column(nullable = false, length = 100)
    private String valor;

    // ENTERO, DECIMAL o TEXTO
    @Column(name = "tipo_dato", nullable = false, length = 20)
    private String tipoDato;

    private String descripcion;

    public int comoEntero() {
        return Integer.parseInt(valor.trim());
    }

    public double comoDecimal() {
        return Double.parseDouble(valor.trim());
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getValor() {
        return valor;
    }
    public void setValor(String valor) {
        this.valor = valor;
    }
    public String getTipoDato() {
        return tipoDato;
    }
    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
