package edu.pe.uls.con.biblioteca.cu.registrarprestamo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import edu.pe.uls.con.biblioteca.dominio.entity.Cliente;
import edu.pe.uls.con.biblioteca.dominio.entity.Ejemplar;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.entity.Parametro;
import edu.pe.uls.con.biblioteca.dominio.repository.RepositoryCliente;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo.RequestPrestamoItem;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.response.ResponsePrestamo;
import edu.pe.uls.con.biblioteca.dominio.entity.Prestamo;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEjemplar;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoParametro;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoPrestamo;

@Service
public class ServiceRegistrarPrestamo {

    RepositoryCliente repoCliente;

    RepoEjemplar repoEjemplar;

    RepoPrestamo repoPrestamo;

    RepoEstadoDisponibilidad repoEstadoDisponibilidad;

    RepoParametro repoParametro;

    public ServiceRegistrarPrestamo(RepositoryCliente repoCliente, RepoEjemplar repoEjemplar, RepoPrestamo repoPrestamo,
            RepoEstadoDisponibilidad repoEstadoDisponibilidad, RepoParametro repoParametro) {
        this.repoCliente = repoCliente;
        this.repoEjemplar = repoEjemplar;
        this.repoPrestamo = repoPrestamo;
        this.repoEstadoDisponibilidad = repoEstadoDisponibilidad;
        this.repoParametro = repoParametro;
    }

    // Una sola transacción: si falla cualquier ítem, no se guarda nada (ni préstamo ni cambio de estado)
    @Transactional
    public ResponsePrestamo registrarPrestamo(RequestPrestamo request) {
        if (request.items() == null || request.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El préstamo debe tener al menos un ítem");
        }

        Cliente cliente = repoCliente.findById(request.idCliente())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cliente no encontrado: " + request.idCliente()));

        // reglas configurables desde la tabla parametro
        int diasPrestamo = repoParametro.obtener(Parametro.DIAS_PRESTAMO).comoEntero();
        int maxItems = repoParametro.obtener(Parametro.MAX_PRESTAMOS).comoEntero();
        EstadoDisponibilidad prestado = repoEstadoDisponibilidad.getReferenceById(EstadoDisponibilidad.PRESTADO);

        Prestamo p = new Prestamo();
        p.setCliente(cliente);
        p.setFechaLimite(LocalDate.now().plusDays(diasPrestamo));
        for (RequestPrestamoItem item : request.items()) {
            Ejemplar ejemplar = repoEjemplar.findById(item.idEjemplar())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Ejemplar no encontrado: " + item.idEjemplar()));
            p.agregarItem(ejemplar, prestado, maxItems);
        }
        repoPrestamo.save(p);

        List<ResponsePrestamo.ResponsePrestamoItem> lst = new ArrayList<>();
        p.getItems().forEach(i -> lst.add(new ResponsePrestamo.ResponsePrestamoItem(
            i.getEjemplar().getPublicacion().getTitulo(), i.getEjemplar().getCodigoBarras())));

        return new ResponsePrestamo(p.getId(), cliente.getNombreCompleto(), p.getFechaLimite(), lst);
    }
}