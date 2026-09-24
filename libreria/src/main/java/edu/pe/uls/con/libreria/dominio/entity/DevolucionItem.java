package edu.pe.uls.con.libreria.dominio.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "devolucion_item")
public class DevolucionItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_devolucion_item")
    @SequenceGenerator(
        name = "sec_devolucion_item",
        sequenceName = "sec_devolucion_item",
        allocationSize = 1
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_devolucion", nullable = false)
    private Devolucion devolucion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_prestamo_item", nullable = false)
    private PrestamoItem prestamoItem;

    @Column(nullable = false)
    private boolean danado;

    @Column(name = "dias_retraso", nullable = false)
    private int diasRetraso;

    @Column(nullable = false)
    private double multa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Devolucion getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(Devolucion devolucion) {
        this.devolucion = devolucion;
    }

    public PrestamoItem getPrestamoItem() {
        return prestamoItem;
    }

    public void setPrestamoItem(PrestamoItem prestamoItem) {
        this.prestamoItem = prestamoItem;
    }

    public boolean isDanado() {
        return danado;
    }

    public void setDanado(boolean danado) {
        this.danado = danado;
    }

    public int getDiasRetraso() {
        return diasRetraso;
    }

    public void setDiasRetraso(int diasRetraso) {
        this.diasRetraso = diasRetraso;
    }

    public double getMulta() {
        return multa;
    }

    public void setMulta(double multa) {
        this.multa = multa;
    }
}

