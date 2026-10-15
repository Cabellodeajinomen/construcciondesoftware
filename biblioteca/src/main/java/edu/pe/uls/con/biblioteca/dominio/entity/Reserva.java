package edu.pe.uls.con.biblioteca.dominio.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

/**
 * Reserva de una publicación por parte de un cliente. Cuando se asigna,
 * queda ligada a un ejemplar concreto. El estado es un código numérico.
 */
@Entity
@Table(name = "reserva")
public class Reserva {

    public static final int PENDIENTE = 1;
    public static final int ASIGNADA = 2;
    public static final int ATENDIDA = 3;   // convertida en préstamo
    public static final int CANCELADA = 4;
    public static final int VENCIDA = 5;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_reserva")
    @SequenceGenerator(name = "sec_reserva", sequenceName = "sec_reserva", allocationSize = 1)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_publicacion", nullable = false)
    private Publicacion publicacion;

    // se llena cuando la reserva pasa a ASIGNADA
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_ejemplar")
    private Ejemplar ejemplar;

    @Column(name = "fecha_reserva", nullable = false)
    private LocalDateTime fechaReserva;

    @Column(name = "fecha_vencimiento", nullable = false)
    private LocalDateTime fechaVencimiento;

    @Column(nullable = false)
    private int estado = PENDIENTE;

    @Column(name = "fecha_atencion")
    private LocalDateTime fechaAtencion;

    public String getNombreEstado() {
        return switch (estado) {
            case PENDIENTE -> "Pendiente";
            case ASIGNADA -> "Asignada";
            case ATENDIDA -> "Atendida";
            case CANCELADA -> "Cancelada";
            case VENCIDA -> "Vencida";
            default -> "Desconocido";
        };
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public LocalDateTime getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(LocalDateTime fechaReserva) {
        this.fechaReserva = fechaReserva;
    }

    public LocalDateTime getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDateTime fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaAtencion() {
        return fechaAtencion;
    }

    public void setFechaAtencion(LocalDateTime fechaAtencion) {
        this.fechaAtencion = fechaAtencion;
    }
}
