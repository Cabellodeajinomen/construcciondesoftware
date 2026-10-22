package edu.pe.uls.con.biblioteca.dominio.entity;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;

/**
 * Datos comunes de toda publicación. Libro, Revista y Tesis heredan de esta clase
 * con la estrategia JOINED: cada una tiene su propia tabla cuya PK (id_publicacion)
 * también es FK hacia publicacion.
 */
@Entity
@Table(name = "publicacion")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sec_publicacion")
    @SequenceGenerator(name = "sec_publicacion", sequenceName = "sec_publicacion", allocationSize = 1)
    private int id;

    @Column(nullable = false)
    private String titulo;

    @Column(name = "anio_publicacion")
    private int anioPublicacion;

    @Column(length = 500)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_tipo_publicacion", nullable = false)
    private TipoPublicacion tipoPublicacion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_idioma", nullable = false)
    private Idioma idioma;

    // una tesis puede no tener editorial
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_editorial")
    private Editorial editorial;

    // tabla relacional: una publicación puede estar en varias categorías
    @ManyToMany
    @JoinTable(
        name = "publicacion_categoria",
        joinColumns = @JoinColumn(name = "id_publicacion"),
        inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private Set<Categoria> categorias = new HashSet<>();

    // tabla relacional: una publicación puede tener varios autores
    @ManyToMany
    @JoinTable(
        name = "publicacion_autor",
        joinColumns = @JoinColumn(name = "id_publicacion"),
        inverseJoinColumns = @JoinColumn(name = "id_autor")
    )
    private Set<Autor> autores = new HashSet<>();

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    public int getAnioPublicacion() {
        return anioPublicacion;
    }
    public void setAnioPublicacion(int anioPublicacion) {
        this.anioPublicacion = anioPublicacion;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public TipoPublicacion getTipoPublicacion() {
        return tipoPublicacion;
    }
    public void setTipoPublicacion(TipoPublicacion tipoPublicacion) {
        this.tipoPublicacion = tipoPublicacion;
    }
    public Idioma getIdioma() {
        return idioma;
    }
    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }
    public Editorial getEditorial() {
        return editorial;
    }
    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }
    public Set<Categoria> getCategorias() {
        return categorias;
    }
    public void setCategorias(Set<Categoria> categorias) {
        this.categorias = categorias;
    }
    public Set<Autor> getAutores() {
        return autores;
    }
    public void setAutores(Set<Autor> autores) {
        this.autores = autores;
    }
}
