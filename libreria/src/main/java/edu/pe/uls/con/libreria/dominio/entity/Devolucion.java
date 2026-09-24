package edu.pe.uls.con.libreria.dominio.entity;


import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "devolucion")
public class Devolucion {

    public static final double MULTA_POR_DIA = 1.50;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_devolucion")
    @SequenceGenerator(
        name = "sec_devolucion",
        sequenceName = "sec_devolucion",
        allocationSize = 1
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_prestamo", nullable = false)
    private Prestamo prestamo;

    @Column(name = "fecha_devolucion", nullable = false)
    private LocalDateTime fechaDevolucion;

    @Column(name = "total_multa")
    private double totalMulta;

    @OneToMany(
        mappedBy = "devolucion",
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    private List<DevolucionItem> items = new ArrayList<>();

    @PrePersist
    protected void prePersist() {
        if (fechaDevolucion == null) {
            fechaDevolucion = LocalDateTime.now();
        }
    }

    /**
     * Devuelve un ítem pendiente del préstamo: libera (o daña) el ejemplar,
     * calcula los días de retraso, la multa y acumula el total.
     */
    public void agregarItem(PrestamoItem prestamoItem, boolean danado) {
        prestamoItem.marcarDevuelto(danado);

        long retraso = Math.max(0, ChronoUnit.DAYS.between(prestamo.getFechaLimite(), LocalDate.now()));
        double multa = retraso * MULTA_POR_DIA;

        DevolucionItem item = new DevolucionItem();
        item.setDevolucion(this);
        item.setPrestamoItem(prestamoItem);
        item.setDanado(danado);
        item.setDiasRetraso((int) retraso);
        item.setMulta(multa);
        items.add(item);

        totalMulta += multa;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Prestamo getPrestamo() {
        return prestamo;
    }

    public void setPrestamo(Prestamo prestamo) {
        this.prestamo = prestamo;
    }

    public LocalDateTime getFechaDevolucion() {
        return fechaDevolucion;
    }

    public void setFechaDevolucion(LocalDateTime fechaDevolucion) {
        this.fechaDevolucion = fechaDevolucion;
    }

    public double getTotalMulta() {
        return totalMulta;
    }

    public void setTotalMulta(double totalMulta) {
        this.totalMulta = totalMulta;
    }

    public List<DevolucionItem> getItems() {
        return items;
    }

    public void setItems(List<DevolucionItem> items) {
        this.items = items;
    }
}
