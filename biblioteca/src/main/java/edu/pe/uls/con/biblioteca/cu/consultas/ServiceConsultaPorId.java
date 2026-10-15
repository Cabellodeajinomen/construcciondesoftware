package edu.pe.uls.con.biblioteca.cu.consultas;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaCatalogo;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaDevolucion;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaDevolucionItem;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaEjemplar;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaIdioma;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaParametro;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaPrestamo;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaPrestamoItem;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaPublicacion;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaReserva;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaRevista;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaTesis;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseConsultaUbicacion;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseDetalleCliente;
import edu.pe.uls.con.biblioteca.cu.consultas.response.ResponseDetalleEjemplar;
import edu.pe.uls.con.biblioteca.dominio.entity.Autor;
import edu.pe.uls.con.biblioteca.dominio.entity.Categoria;
import edu.pe.uls.con.biblioteca.dominio.entity.Cliente;
import edu.pe.uls.con.biblioteca.dominio.entity.Devolucion;
import edu.pe.uls.con.biblioteca.dominio.entity.DevolucionItem;
import edu.pe.uls.con.biblioteca.dominio.entity.Ejemplar;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoFisico;
import edu.pe.uls.con.biblioteca.dominio.entity.Idioma;
import edu.pe.uls.con.biblioteca.dominio.entity.Parametro;
import edu.pe.uls.con.biblioteca.dominio.entity.Prestamo;
import edu.pe.uls.con.biblioteca.dominio.entity.PrestamoItem;
import edu.pe.uls.con.biblioteca.dominio.entity.Publicacion;
import edu.pe.uls.con.biblioteca.dominio.entity.Reserva;
import edu.pe.uls.con.biblioteca.dominio.entity.Revista;
import edu.pe.uls.con.biblioteca.dominio.entity.Tesis;
import edu.pe.uls.con.biblioteca.dominio.entity.TipoCliente;
import edu.pe.uls.con.biblioteca.dominio.entity.TipoPublicacion;
import edu.pe.uls.con.biblioteca.dominio.entity.Ubicacion;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoDevolucion;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoDevolucionItem;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEjemplar;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEstadoFisico;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoIdioma;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoParametro;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoPrestamo;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoPrestamoItem;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoPublicacion;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoReserva;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoRevista;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoTesis;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoTipoCliente;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoTipoPublicacion;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoUbicacion;

@Service
@Transactional(readOnly = true)
public class ServiceConsultaPorId {

    private final RepoEjemplar repoEjemplar;
    private final RepoPrestamo repoPrestamo;
    private final RepoPrestamoItem repoPrestamoItem;
    private final RepoDevolucion repoDevolucion;
    private final RepoDevolucionItem repoDevolucionItem;
    private final RepoTipoPublicacion repoTipoPublicacion;
    private final RepoIdioma repoIdioma;
    private final RepoPublicacion repoPublicacion;
    private final RepoRevista repoRevista;
    private final RepoTesis repoTesis;
    private final RepoTipoCliente repoTipoCliente;
    private final RepoUbicacion repoUbicacion;
    private final RepoEstadoFisico repoEstadoFisico;
    private final RepoEstadoDisponibilidad repoEstadoDisponibilidad;
    private final RepoReserva repoReserva;
    private final RepoParametro repoParametro;

    public ServiceConsultaPorId(RepoEjemplar repoEjemplar, RepoPrestamo repoPrestamo,
            RepoPrestamoItem repoPrestamoItem, RepoDevolucion repoDevolucion,
            RepoDevolucionItem repoDevolucionItem, RepoTipoPublicacion repoTipoPublicacion,
            RepoIdioma repoIdioma, RepoPublicacion repoPublicacion, RepoRevista repoRevista,
            RepoTesis repoTesis, RepoTipoCliente repoTipoCliente, RepoUbicacion repoUbicacion,
            RepoEstadoFisico repoEstadoFisico, RepoEstadoDisponibilidad repoEstadoDisponibilidad,
            RepoReserva repoReserva, RepoParametro repoParametro) {
        this.repoEjemplar = repoEjemplar;
        this.repoPrestamo = repoPrestamo;
        this.repoPrestamoItem = repoPrestamoItem;
        this.repoDevolucion = repoDevolucion;
        this.repoDevolucionItem = repoDevolucionItem;
        this.repoTipoPublicacion = repoTipoPublicacion;
        this.repoIdioma = repoIdioma;
        this.repoPublicacion = repoPublicacion;
        this.repoRevista = repoRevista;
        this.repoTesis = repoTesis;
        this.repoTipoCliente = repoTipoCliente;
        this.repoUbicacion = repoUbicacion;
        this.repoEstadoFisico = repoEstadoFisico;
        this.repoEstadoDisponibilidad = repoEstadoDisponibilidad;
        this.repoReserva = repoReserva;
        this.repoParametro = repoParametro;
    }

    public ResponseConsultaEjemplar consultarEjemplar(int id) {
        Ejemplar ejemplar = repoEjemplar.findById(id).orElseThrow(() -> noEncontrado("Ejemplar", id));
        Publicacion publicacion = ejemplar.getPublicacion();
        return new ResponseConsultaEjemplar(ejemplar.getId(), publicacion.getId(), publicacion.getTitulo(),
                publicacion.getTipoPublicacion().getNombre(), ejemplar.getCodigoBarras(),
                ejemplar.getUbicacion().getId(), ejemplar.getUbicacion().getDescripcionCompleta(),
                ejemplar.getEstadoFisico().getId(), ejemplar.getEstadoFisico().getNombre(),
                ejemplar.getEstadoDisponibilidad().getId(), ejemplar.getEstadoDisponibilidad().getNombre(),
                ejemplar.getNota());
    }

    public ResponseConsultaPrestamo consultarPrestamo(int id) {
        Prestamo prestamo = repoPrestamo.findById(id).orElseThrow(() -> noEncontrado("Préstamo", id));
        List<ResponseConsultaPrestamo.ResponseItem> items = prestamo.getItems().stream()
                .map(i -> new ResponseConsultaPrestamo.ResponseItem(i.getId(), i.isDevuelto(), detalle(i.getEjemplar())))
                .toList();
        return new ResponseConsultaPrestamo(prestamo.getId(), prestamo.getCliente().getId(),
                detalle(prestamo.getCliente()), prestamo.getFechaPrestamo(), prestamo.getFechaLimite(),
                items.size(), items);
    }

    public ResponseConsultaPrestamoItem consultarPrestamoItem(int id) {
        PrestamoItem item = repoPrestamoItem.findById(id)
                .orElseThrow(() -> noEncontrado("Ítem de préstamo", id));
        Prestamo prestamo = item.getPrestamo();
        return new ResponseConsultaPrestamoItem(item.getId(), prestamo.getId(), item.getEjemplar().getId(),
                item.isDevuelto(), prestamo.getFechaLimite(), detalle(prestamo.getCliente()), detalle(item.getEjemplar()));
    }

    public ResponseConsultaDevolucion consultarDevolucion(int id) {
        Devolucion devolucion = repoDevolucion.findById(id)
                .orElseThrow(() -> noEncontrado("Devolución", id));
        List<ResponseConsultaDevolucion.ResponseItem> items = devolucion.getItems().stream()
                .map(i -> new ResponseConsultaDevolucion.ResponseItem(i.getId(), i.isDanado(), i.getDiasRetraso(),
                        i.getMulta(), detalle(i.getPrestamoItem().getEjemplar())))
                .toList();
        return new ResponseConsultaDevolucion(devolucion.getId(), devolucion.getPrestamo().getId(),
                detalle(devolucion.getPrestamo().getCliente()), devolucion.getFechaDevolucion(),
                devolucion.getTotalMulta(), items.size(), items);
    }

    public ResponseConsultaDevolucionItem consultarDevolucionItem(int id) {
        DevolucionItem item = repoDevolucionItem.findById(id)
                .orElseThrow(() -> noEncontrado("Ítem de devolución", id));
        Devolucion devolucion = item.getDevolucion();
        return new ResponseConsultaDevolucionItem(item.getId(), devolucion.getId(), item.getPrestamoItem().getId(),
                item.isDanado(), item.getDiasRetraso(), item.getMulta(), devolucion.getFechaDevolucion(),
                detalle(devolucion.getPrestamo().getCliente()), detalle(item.getPrestamoItem().getEjemplar()));
    }

    public ResponseConsultaCatalogo consultarTipoPublicacion(int id) {
        TipoPublicacion tipo = repoTipoPublicacion.findById(id)
                .orElseThrow(() -> noEncontrado("Tipo de publicación", id));
        return new ResponseConsultaCatalogo(tipo.getId(), tipo.getNombre(), tipo.getDescripcion());
    }

    public ResponseConsultaIdioma consultarIdioma(int id) {
        Idioma idioma = repoIdioma.findById(id).orElseThrow(() -> noEncontrado("Idioma", id));
        return new ResponseConsultaIdioma(idioma.getId(), idioma.getCodigo(), idioma.getNombre());
    }

    public ResponseConsultaPublicacion consultarPublicacion(int id) {
        Publicacion p = repoPublicacion.findById(id).orElseThrow(() -> noEncontrado("Publicación", id));
        return new ResponseConsultaPublicacion(p.getId(), p.getTitulo(), p.getAñoPublicacion(),
                p.getDescripcion(), p.getTipoPublicacion().getNombre(), p.getIdioma().getNombre(),
                p.getEditorial() == null ? null : p.getEditorial().getNombre(),
                nombresAutores(p.getAutores()), nombresCategorias(p.getCategorias()));
    }

    public ResponseConsultaRevista consultarRevista(int id) {
        Revista r = repoRevista.findById(id).orElseThrow(() -> noEncontrado("Revista", id));
        return new ResponseConsultaRevista(r.getId(), r.getTitulo(), r.getIssn(), r.getVolumen(),
                r.getNumero(), r.getFechaPublicacion(),
                r.getEditorial() == null ? null : r.getEditorial().getNombre());
    }

    public ResponseConsultaTesis consultarTesis(int id) {
        Tesis t = repoTesis.findById(id).orElseThrow(() -> noEncontrado("Tesis", id));
        return new ResponseConsultaTesis(t.getId(), t.getTitulo(), t.getAñoPublicacion(),
                t.getGradoAcademico(), t.getInstitucion(), t.getAsesor(), nombresAutores(t.getAutores()));
    }

    public ResponseConsultaCatalogo consultarTipoCliente(int id) {
        TipoCliente tipo = repoTipoCliente.findById(id).orElseThrow(() -> noEncontrado("Tipo de cliente", id));
        return new ResponseConsultaCatalogo(tipo.getId(), tipo.getNombre(), tipo.getDescripcion());
    }

    public ResponseConsultaUbicacion consultarUbicacion(int id) {
        Ubicacion u = repoUbicacion.findById(id).orElseThrow(() -> noEncontrado("Ubicación", id));
        return new ResponseConsultaUbicacion(u.getId(), u.getCodigo(), u.getSala(), u.getEstante(),
                u.getNivel(), u.getSeccion());
    }

    public ResponseConsultaCatalogo consultarEstadoFisico(int id) {
        EstadoFisico e = repoEstadoFisico.findById(id).orElseThrow(() -> noEncontrado("Estado físico", id));
        return new ResponseConsultaCatalogo(e.getId(), e.getNombre(), e.getDescripcion());
    }

    public ResponseConsultaCatalogo consultarEstadoDisponibilidad(int id) {
        EstadoDisponibilidad e = repoEstadoDisponibilidad.findById(id)
                .orElseThrow(() -> noEncontrado("Estado de disponibilidad", id));
        return new ResponseConsultaCatalogo(e.getId(), e.getNombre(), e.getDescripcion());
    }

    public ResponseConsultaReserva consultarReserva(int id) {
        Reserva r = repoReserva.findById(id).orElseThrow(() -> noEncontrado("Reserva", id));
        Ejemplar ejemplar = r.getEjemplar();
        return new ResponseConsultaReserva(r.getId(), r.getCliente().getId(), r.getPublicacion().getId(),
                ejemplar == null ? null : ejemplar.getId(), detalle(r.getCliente()),
                r.getPublicacion().getTitulo(), r.getPublicacion().getTipoPublicacion().getNombre(),
                ejemplar == null ? null : detalle(ejemplar),
                r.getFechaReserva(), r.getFechaVencimiento(), r.getEstado(), r.getNombreEstado(), r.getFechaAtencion());
    }

    public ResponseConsultaParametro consultarParametro(int id) {
        Parametro p = repoParametro.findById(id).orElseThrow(() -> noEncontrado("Parámetro", id));
        return new ResponseConsultaParametro(p.getId(), p.getCodigo(), p.getValor(), p.getTipoDato(),
                p.getDescripcion());
    }

    // detalle legible de un ejemplar: se reutiliza en préstamos, devoluciones y reservas
    private ResponseDetalleEjemplar detalle(Ejemplar e) {
        return new ResponseDetalleEjemplar(e.getId(), e.getCodigoBarras(), e.getPublicacion().getTitulo(),
                e.getPublicacion().getTipoPublicacion().getNombre(), e.getUbicacion().getDescripcionCompleta(),
                e.getEstadoFisico().getNombre(), e.getEstadoDisponibilidad().getNombre(), e.getNota());
    }

    private ResponseDetalleCliente detalle(Cliente c) {
        return new ResponseDetalleCliente(c.getId(), c.getNombreCompleto(), c.getDni(), c.getTipoCliente().getNombre());
    }

    private List<String> nombresAutores(Set<Autor> autores) {
        return autores.stream().map(a -> a.getNombres() + " " + a.getApellidos()).sorted().toList();
    }

    private List<String> nombresCategorias(Set<Categoria> categorias) {
        return categorias.stream().map(Categoria::getNombre).sorted().toList();
    }

    private ResponseStatusException noEncontrado(String entidad, int id) {
        return new ResponseStatusException(HttpStatus.NOT_FOUND, entidad + " no encontrado: " + id);
    }
}
