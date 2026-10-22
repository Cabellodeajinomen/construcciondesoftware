package edu.pe.uls.con.biblioteca.cu.registrarprestamo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
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
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.response.ResponseClientePrestamo;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.response.ResponsePrestamo;
import edu.pe.uls.con.biblioteca.dominio.entity.Prestamo;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEjemplar;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoParametro;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoPrestamo;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoPublicacion;
import edu.pe.uls.con.biblioteca.dominio.projection.EjemplarDisponible;

@Service
public class ServiceRegistrarPrestamo {

    RepositoryCliente repoCliente;

    RepoEjemplar repoEjemplar;

    RepoPrestamo repoPrestamo;

    RepoEstadoDisponibilidad repoEstadoDisponibilidad;

    RepoParametro repoParametro;

    RepoPublicacion repoPublicacion;

    // pausa entre leer el ejemplar y guardarlo; solo se usa en el test de concurrencia (por defecto 0)
    long pausaDemoMs;

    public ServiceRegistrarPrestamo(RepositoryCliente repoCliente, RepoEjemplar repoEjemplar, RepoPrestamo repoPrestamo,
            RepoEstadoDisponibilidad repoEstadoDisponibilidad, RepoParametro repoParametro,
            RepoPublicacion repoPublicacion, @Value("${biblioteca.demo.pausa-ms:0}") long pausaDemoMs) {
        this.repoCliente = repoCliente;
        this.repoEjemplar = repoEjemplar;
        this.repoPrestamo = repoPrestamo;
        this.repoEstadoDisponibilidad = repoEstadoDisponibilidad;
        this.repoParametro = repoParametro;
        this.repoPublicacion = repoPublicacion;
        this.pausaDemoMs = pausaDemoMs;
    }

    // Paso 1 del CU: identificar al cliente por DNI y ver si puede llevarse ejemplares
    public ResponseClientePrestamo consultarCliente(String dni) {
        Cliente cliente = repoCliente.findByDni(dni)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con DNI: " + dni));
        long pendientes = repoPrestamo.contarItemsPendientes(cliente.getId());
        long vencidos = repoPrestamo.contarItemsVencidos(cliente.getId(), LocalDate.now());
        return new ResponseClientePrestamo(cliente.getId(), cliente.getNombreCompleto(), cliente.getDni(),
            cliente.getTipoCliente().getNombre(), pendientes, vencidos, vencidos == 0);
    }

    // Paso 2 del CU: ver qué ejemplares de la publicación están disponibles y dónde están
    public List<EjemplarDisponible> consultarEjemplaresDisponibles(int idPublicacion) {
        if (!repoPublicacion.existsById(idPublicacion)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Publicación no encontrada: " + idPublicacion);
        }
        return repoEjemplar.buscarPorPublicacionYEstado(idPublicacion, EstadoDisponibilidad.DISPONIBLE);
    }

    // Una sola transacción: si falla cualquier ítem, no se guarda nada (ni préstamo ni cambio de estado)
    // Con bloqueo: el ejemplar se lee con SELECT ... FOR UPDATE
    @Transactional
    public ResponsePrestamo registrarPrestamo(RequestPrestamo request) {
        return registrar(request, true);
    }

    // Sin bloqueo: el ejemplar se lee con un SELECT normal (solo para demostrar el problema)
    @Transactional
    public ResponsePrestamo registrarPrestamoSinLock(RequestPrestamo request) {
        return registrar(request, false);
    }

    private ResponsePrestamo registrar(RequestPrestamo request, boolean conLock) {
        if (request.items() == null || request.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El préstamo debe tener al menos un ítem");
        }

        Cliente cliente = repoCliente.findById(request.idCliente())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Cliente no encontrado: " + request.idCliente()));

        // un cliente con ejemplares vencidos sin devolver no puede llevarse más
        long vencidos = repoPrestamo.contarItemsVencidos(cliente.getId(), LocalDate.now());
        if (vencidos > 0) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                "El cliente tiene " + vencidos + " ejemplar(es) con préstamo vencido sin devolver");
        }

        // reglas configurables desde la tabla parametro
        int diasPrestamo = repoParametro.obtener(Parametro.DIAS_PRESTAMO).comoEntero();
        int maxItems = repoParametro.obtener(Parametro.MAX_PRESTAMOS).comoEntero();
        EstadoDisponibilidad prestado = repoEstadoDisponibilidad.getReferenceById(EstadoDisponibilidad.PRESTADO);

        Prestamo p = new Prestamo();
        p.setCliente(cliente);
        p.setFechaLimite(LocalDate.now().plusDays(diasPrestamo));
        for (RequestPrestamoItem item : request.items()) {
            String hilo = Thread.currentThread().getName();
            System.out.println(System.currentTimeMillis() + " INICIO " + hilo);
            Ejemplar ejemplar = (conLock
                    ? repoEjemplar.findByIdForUpdate(item.idEjemplar())
                    : repoEjemplar.findById(item.idEjemplar()))
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Ejemplar no encontrado: " + item.idEjemplar()));
            System.out.println(System.currentTimeMillis() + " DESPUES DE FIND " + hilo
                + " estado=" + ejemplar.getEstadoDisponibilidad().getNombre());
            esperarParaDemostrarConcurrencia();
            System.out.println(System.currentTimeMillis() + " DESPUES DE SLEEP " + hilo);
            try {
                p.agregarItem(ejemplar, prestado, maxItems);
            } catch (IllegalStateException e) {
                // ejemplar no disponible o demasiados ítems: es un conflicto, no un error del servidor
                throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
        }
        repoPrestamo.save(p);

        List<ResponsePrestamo.ResponsePrestamoItem> lst = new ArrayList<>();
        p.getItems().forEach(i -> lst.add(new ResponsePrestamo.ResponsePrestamoItem(
            i.getEjemplar().getPublicacion().getTitulo(), i.getEjemplar().getCodigoBarras())));

        return new ResponsePrestamo(p.getId(), cliente.getNombreCompleto(), p.getFechaLimite(), lst);
    }

    // pausa entre leer y guardar, para que los dos hilos del test de concurrencia se crucen
    private void esperarParaDemostrarConcurrencia() {
        if (pausaDemoMs <= 0) {
            return;
        }
        try {
            Thread.sleep(pausaDemoMs);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
