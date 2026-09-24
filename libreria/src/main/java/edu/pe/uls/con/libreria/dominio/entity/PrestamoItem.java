package edu.pe.uls.con.libreria.dominio.entity;



import edu.pe.uls.con.libreria.Ejemplar;
import jakarta.persistence.*;

@Entity
@Table(name = "prestamo_item")
public class PrestamoItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_prestamo_item")
    @SequenceGenerator(
        name = "sec_prestamo_item",
        sequenceName = "sec_prestamo_item",
        allocationSize = 1
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_prestamo", nullable = false)
    private Prestamo prestamo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_ejemplar", nullable = false)
    private Ejemplar ejemplar;

    @Column(nullable = false)
    private boolean devuelto = false;

    public void marcarDevuelto(boolean danado) {
        if (devuelto) {
            throw new IllegalStateException(
                "El ejemplar " + ejemplar.getCodigoBarras() + " ya fue devuelto");
        }
        devuelto = true;
        ejemplar.devolver(danado);
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

    public Ejemplar getEjemplar() {
        return ejemplar;
    }

    public void setEjemplar(Ejemplar ejemplar) {
        this.ejemplar = ejemplar;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }
}

