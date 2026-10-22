package edu.pe.uls.con.biblioteca.cu.registrardevolucion;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import edu.pe.uls.con.biblioteca.cu.registrardevolucion.request.RequestDevolucion;
import edu.pe.uls.con.biblioteca.cu.registrardevolucion.request.RequestDevolucion.RequestDevolucionItem;
import edu.pe.uls.con.biblioteca.cu.registrardevolucion.response.ResponseDevolucion;
import edu.pe.uls.con.biblioteca.cu.registrardevolucion.response.ResponseItemPorDevolver;
import edu.pe.uls.con.biblioteca.dominio.entity.Devolucion;
import edu.pe.uls.con.biblioteca.dominio.entity.Ejemplar;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoFisico;
import edu.pe.uls.con.biblioteca.dominio.entity.Parametro;
import edu.pe.uls.con.biblioteca.dominio.entity.Prestamo;
import edu.pe.uls.con.biblioteca.dominio.entity.PrestamoItem;
import edu.pe.uls.con.biblioteca.dominio.projection.DevolucionResumen;
import edu.pe.uls.con.biblioteca.dominio.projection.ItemPorDevolver;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoDevolucion;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEstadoFisico;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoParametro;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoPrestamo;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoPrestamoItem;

@Service
public class ServiceRegistrarDevolucion {

    RepoPrestamo repoPrestamo;

    RepoDevolucion repoDevolucion;

    RepoEstadoDisponibilidad repoEstadoDisponibilidad;

    RepoEstadoFisico repoEstadoFisico;

    RepoParametro repoParametro;

    RepoPrestamoItem repoPrestamoItem;

    public ServiceRegistrarDevolucion(RepoPrestamo repoPrestamo, RepoDevolucion repoDevolucion,
            RepoEstadoDisponibilidad repoEstadoDisponibilidad, RepoEstadoFisico repoEstadoFisico,
            RepoParametro repoParametro, RepoPrestamoItem repoPrestamoItem) {
        this.repoPrestamoItem = repoPrestamoItem;
        this.repoPrestamo = repoPrestamo;
        this.repoDevolucion = repoDevolucion;
        this.repoEstadoDisponibilidad = repoEstadoDisponibilidad;
        this.repoEstadoFisico = repoEstadoFisico;
        this.repoParametro = repoParametro;
    }

    // Paso 1 del CU: ver qué ejemplares del préstamo faltan devolver y cuánto de multa llevan
    public List<ResponseItemPorDevolver> consultarPendientes(int idPrestamo) {
        validarPrestamoExiste(idPrestamo);
        double multaPorDia = repoParametro.obtener(Parametro.MULTA_DIARIA).comoDecimal();
        List<ResponseItemPorDevolver> lst = new ArrayList<>();
        for (ItemPorDevolver i : repoPrestamoItem.buscarPendientesDePrestamo(idPrestamo)) {
            long retraso = Math.max(0, ChronoUnit.DAYS.between(i.fechaLimite(), LocalDate.now()));
            lst.add(new ResponseItemPorDevolver(i.idEjemplar(), i.codigoBarras(), i.titulo(), i.fechaLimite(),
                retraso, retraso * multaPorDia));
        }
        return lst;
    }

    // Historial: devoluciones ya hechas de un préstamo, con su total de multa y cantidad de ejemplares
    public List<DevolucionResumen> consultarDevolucionesDePrestamo(int idPrestamo) {
        validarPrestamoExiste(idPrestamo);
        return repoDevolucion.buscarResumenPorPrestamo(idPrestamo);
    }

    private void validarPrestamoExiste(int idPrestamo) {
        if (!repoPrestamo.existsById(idPrestamo)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Préstamo no encontrado: " + idPrestamo);
        }
    }

    // Devolución parcial o total de un préstamo, en una sola transacción
    @Transactional
    public ResponseDevolucion registrarDevolucion(RequestDevolucion request) {
        if (request.items() == null || request.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La devolución debe tener al menos un ítem");
        }

        Prestamo prestamo = repoPrestamo.findById(request.idPrestamo())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Préstamo no encontrado: " + request.idPrestamo()));

        double multaPorDia = repoParametro.obtener(Parametro.MULTA_DIARIA).comoDecimal();
        EstadoDisponibilidad disponible = repoEstadoDisponibilidad.getReferenceById(EstadoDisponibilidad.DISPONIBLE);
        EstadoDisponibilidad noDisponible = repoEstadoDisponibilidad.getReferenceById(EstadoDisponibilidad.NO_DISPONIBLE);
        EstadoFisico danado = repoEstadoFisico.getReferenceById(EstadoFisico.DANADO);

        Devolucion d = new Devolucion();
        d.setPrestamo(prestamo);
        for (RequestDevolucionItem item : request.items()) {
            // el ejemplar no es de este préstamo: 404; ya se devolvió antes: 409
            boolean perteneceAlPrestamo = prestamo.getItems().stream()
                .anyMatch(i -> i.getEjemplar().getId() == item.idEjemplar());
            if (!perteneceAlPrestamo) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "El ejemplar " + item.idEjemplar() + " no pertenece al préstamo " + prestamo.getId());
            }
            PrestamoItem prestamoItem = prestamo.buscarItemPendiente(item.idEjemplar())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "El ejemplar " + item.idEjemplar() + " ya fue devuelto en el préstamo " + prestamo.getId()));
            d.agregarItem(prestamoItem, item.danado(), multaPorDia);

            // un ejemplar dañado queda fuera de circulación; si no, vuelve a estar disponible
            Ejemplar ejemplar = prestamoItem.getEjemplar();
            if (item.danado()) {
                ejemplar.devolver(noDisponible, danado);
            } else {
                ejemplar.devolver(disponible, ejemplar.getEstadoFisico());
            }
        }
        repoDevolucion.save(d);

        List<ResponseDevolucion.ResponseDevolucionItem> lst = new ArrayList<>();
        d.getItems().forEach(i -> lst.add(new ResponseDevolucion.ResponseDevolucionItem(
            i.getPrestamoItem().getEjemplar().getPublicacion().getTitulo(),
            i.getPrestamoItem().getEjemplar().getCodigoBarras(),
            i.getDiasRetraso(),
            i.getMulta(),
            i.isDanado())));

        return new ResponseDevolucion(d.getId(), prestamo.getId(), d.getTotalMulta(), lst);
    }
}

