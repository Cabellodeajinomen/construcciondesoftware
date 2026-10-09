package edu.pe.uls.con.libreria.cu.registrarprestamo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import edu.pe.uls.con.libreria.dominio.entity.Cliente;
import edu.pe.uls.con.libreria.dominio.entity.Ejemplar;
import edu.pe.uls.con.libreria.dominio.repository.RepositoryCliente;
import edu.pe.uls.con.libreria.cu.registrarprestamo.request.RequestPrestamo;
import edu.pe.uls.con.libreria.cu.registrarprestamo.request.RequestPrestamo.RequestPrestamoItem;
import edu.pe.uls.con.libreria.cu.registrarprestamo.response.ResponsePrestamo;
import edu.pe.uls.con.libreria.dominio.entity.Prestamo;
import edu.pe.uls.con.libreria.dominio.repository.RepoEjemplar;
import edu.pe.uls.con.libreria.dominio.repository.RepoPrestamo;

@Service
public class ServiceRegistrarPrestamo {

    RepositoryCliente repoCliente;

    RepoEjemplar repoEjemplar;

    RepoPrestamo repoPrestamo;

    public ServiceRegistrarPrestamo(RepositoryCliente repoCliente, RepoEjemplar repoEjemplar, RepoPrestamo repoPrestamo) {
        this.repoCliente = repoCliente;
        this.repoEjemplar = repoEjemplar;
        this.repoPrestamo = repoPrestamo;
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

        Prestamo p = new Prestamo();
        p.setCliente(cliente);
        for (RequestPrestamoItem item : request.items()) {
            Ejemplar ejemplar = repoEjemplar.findById(item.idEjemplar())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Ejemplar no encontrado: " + item.idEjemplar()));
            p.agregarItem(ejemplar);
        }
        repoPrestamo.save(p);

        List<ResponsePrestamo.ResponsePrestamoItem> lst = new ArrayList<>();
        p.getItems().forEach(i -> lst.add(new ResponsePrestamo.ResponsePrestamoItem(
            i.getEjemplar().getLibro().getTitulo(), i.getEjemplar().getCodigoBarras())));

        return new ResponsePrestamo(p.getId(), cliente.getNombreCompleto(), p.getFechaLimite(), lst);
    }
}