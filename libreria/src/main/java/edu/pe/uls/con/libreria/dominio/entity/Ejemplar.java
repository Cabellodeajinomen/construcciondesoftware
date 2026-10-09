package edu.pe.uls.con.libreria.dominio.entity;


import jakarta.persistence.*;

/**
 * Copia física de un libro. El préstamo se hace sobre el ejemplar, no sobre el libro.
 */
@Entity
@Table(name = "ejemplar")
public class Ejemplar {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_ejemplar")
    @SequenceGenerator(
        name = "sec_ejemplar",
        sequenceName = "sec_ejemplar",
        allocationSize = 1
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_libro", nullable = false)
    private Libro libro;

    @Column(name = "codigo_barras", nullable = false, length = 30, unique = true)
    private String codigoBarras;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private EstadoEjemplar estado = EstadoEjemplar.DISPONIBLE;

    // ---- reglas de negocio del propio ejemplar ----
    public void prestar() {
        if (estado != EstadoEjemplar.DISPONIBLE) {
            throw new IllegalStateException(
                "El ejemplar " + codigoBarras + " no está disponible (estado: " + estado + ")");
        }
        estado = EstadoEjemplar.PRESTADO;
    }

    public void devolver(boolean danado) {
        estado = danado ? EstadoEjemplar.DANADO : EstadoEjemplar.DISPONIBLE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public EstadoEjemplar getEstado() {
        return estado;
    }

    public void setEstado(EstadoEjemplar estado) {
        this.estado = estado;
    }
}
