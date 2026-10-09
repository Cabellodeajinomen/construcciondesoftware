package edu.pe.uls.con.libreria.dominio.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Entity
@Table(name = "prestamo")
public class Prestamo {

    public static final int DIAS_PRESTAMO = 7;
    public static final int MAX_LIBROS = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_prestamo")
    @SequenceGenerator(
        name = "sec_prestamo",
        sequenceName = "sec_prestamo",
        allocationSize = 1
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(name = "fecha_prestamo", nullable = false)
    private LocalDateTime fechaPrestamo;

    @Column(name = "fecha_limite", nullable = false)
    private LocalDate fechaLimite;

    @OneToMany(
        mappedBy = "prestamo",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<PrestamoItem> items = new ArrayList<>();

    @PrePersist
    protected void prePersist() {
        if (fechaPrestamo == null) {
            fechaPrestamo = LocalDateTime.now();
        }
        if (fechaLimite == null) {
            fechaLimite = fechaPrestamo.toLocalDate().plusDays(DIAS_PRESTAMO);
        }
    }

    public void agregarItem(Ejemplar ejemplar) {
        if (items.size() >= MAX_LIBROS) {
            throw new IllegalStateException("Un préstamo admite como máximo " + MAX_LIBROS + " libros");
        }
        ejemplar.prestar();

        PrestamoItem item = new PrestamoItem();
        item.setPrestamo(this);
        item.setEjemplar(ejemplar);
        items.add(item);
    }

    public Optional<PrestamoItem> buscarItemPendiente(int idEjemplar) {
        return items.stream()
            .filter(i -> !i.isDevuelto() && i.getEjemplar().getId() == idEjemplar)
            .findFirst();
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

    public LocalDateTime getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(LocalDateTime fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public List<PrestamoItem> getItems() {
        return items;
    }

    public void setItems(List<PrestamoItem> items) {
        this.items = items;
    }
}