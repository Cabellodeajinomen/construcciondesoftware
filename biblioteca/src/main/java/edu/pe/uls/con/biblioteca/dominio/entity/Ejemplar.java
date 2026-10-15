package edu.pe.uls.con.biblioteca.dominio.entity;


import jakarta.persistence.*;

/**
 * Copia física de una publicación. El préstamo se hace sobre el ejemplar, no sobre la publicación.
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
    @JoinColumn(name = "id_publicacion", nullable = false)
    private Publicacion publicacion;

    @Column(name = "codigo_barras", nullable = false, length = 30, unique = true)
    private String codigoBarras;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ubicacion", nullable = false)
    private Ubicacion ubicacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estado_fisico", nullable = false)
    private EstadoFisico estadoFisico;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_estado_disponibilidad", nullable = false)
    private EstadoDisponibilidad estadoDisponibilidad;

    // observación libre sobre el ejemplar (daños, encuadernación, uso solo en sala...)
    private String nota;

    // ---- reglas de negocio del propio ejemplar ----
    public boolean estaDisponible() {
        return estadoDisponibilidad.getId() == EstadoDisponibilidad.DISPONIBLE;
    }

    public void prestar(EstadoDisponibilidad prestado) {
        if (!estaDisponible()) {
            throw new IllegalStateException(
                "El ejemplar " + codigoBarras + " no está disponible (estado: " + estadoDisponibilidad.getNombre() + ")");
        }
        estadoDisponibilidad = prestado;
    }

    public void devolver(EstadoDisponibilidad nuevaDisponibilidad, EstadoFisico nuevoEstadoFisico) {
        estadoDisponibilidad = nuevaDisponibilidad;
        estadoFisico = nuevoEstadoFisico;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Publicacion getPublicacion() {
        return publicacion;
    }

    public void setPublicacion(Publicacion publicacion) {
        this.publicacion = publicacion;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }

    public Ubicacion getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Ubicacion ubicacion) {
        this.ubicacion = ubicacion;
    }

    public EstadoFisico getEstadoFisico() {
        return estadoFisico;
    }

    public void setEstadoFisico(EstadoFisico estadoFisico) {
        this.estadoFisico = estadoFisico;
    }

    public EstadoDisponibilidad getEstadoDisponibilidad() {
        return estadoDisponibilidad;
    }

    public void setEstadoDisponibilidad(EstadoDisponibilidad estadoDisponibilidad) {
        this.estadoDisponibilidad = estadoDisponibilidad;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
}
