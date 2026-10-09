package edu.pe.uls.con.libreria.cu.consultas;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.pe.uls.con.libreria.dominio.entity.EstadoEjemplar;
import edu.pe.uls.con.libreria.dominio.repository.RepositoryCliente;
import edu.pe.uls.con.libreria.cu.consultas.exception.ParametroInvalidoException;
import edu.pe.uls.con.libreria.cu.consultas.exception.RecursoNoEncontradoException;
import edu.pe.uls.con.libreria.dominio.projection.ActividadCliente;
import edu.pe.uls.con.libreria.dominio.projection.EjemplarPorEstado;
import edu.pe.uls.con.libreria.dominio.projection.ItemDanado;
import edu.pe.uls.con.libreria.dominio.projection.ItemPendiente;
import edu.pe.uls.con.libreria.dominio.projection.LibroPrestado;
import edu.pe.uls.con.libreria.dominio.projection.MultaCliente;
import edu.pe.uls.con.libreria.dominio.projection.PrestamoResumen;
import edu.pe.uls.con.libreria.dominio.projection.PrestamoVencido;
import edu.pe.uls.con.libreria.dominio.repository.RepoDevolucion;
import edu.pe.uls.con.libreria.dominio.repository.RepoEjemplar;
import edu.pe.uls.con.libreria.dominio.repository.RepoPrestamo;
import edu.pe.uls.con.libreria.dominio.repository.RepoPrestamoItem;

@Service
@Transactional(readOnly = true)
public class ServiceConsultas {

    private static final Pattern DNI = Pattern.compile("\\d{8}");

    RepositoryCliente repoCliente;
    RepoPrestamo repoPrestamo;
    RepoPrestamoItem repoPrestamoItem;
    RepoDevolucion repoDevolucion;
    RepoEjemplar repoEjemplar;

    public ServiceConsultas(RepositoryCliente repoCliente, RepoPrestamo repoPrestamo,
            RepoPrestamoItem repoPrestamoItem, RepoDevolucion repoDevolucion, RepoEjemplar repoEjemplar) {
        this.repoCliente = repoCliente;
        this.repoPrestamo = repoPrestamo;
        this.repoPrestamoItem = repoPrestamoItem;
        this.repoDevolucion = repoDevolucion;
        this.repoEjemplar = repoEjemplar;
    }

    // Query 1
    public List<PrestamoResumen> consultarPrestamosPorDni(String dni) {
        if (dni == null || !DNI.matcher(dni.trim()).matches()) {
            throw new ParametroInvalidoException("El DNI debe tener exactamente 8 dígitos numéricos");
        }
        String dniLimpio = dni.trim();
        repoCliente.findByDni(dniLimpio)
            .orElseThrow(() -> new RecursoNoEncontradoException("No existe un cliente con DNI: " + dniLimpio));
        return repoPrestamo.buscarPorDniCliente(dniLimpio);
    }

    // Query 2
    public List<ItemPendiente> consultarItemsPendientesPorCliente(int idCliente) {
        if (idCliente <= 0) {
            throw new ParametroInvalidoException("El id del cliente debe ser un número positivo");
        }
        if (!repoCliente.existsById(idCliente)) {
            throw new RecursoNoEncontradoException("No existe el cliente con id: " + idCliente);
        }
        return repoPrestamo.buscarItemsPendientesPorCliente(idCliente);
    }

    // Query 3: si no se envía fecha se usa la de hoy
    public List<PrestamoVencido> consultarPrestamosVencidos(LocalDate fecha) {
        LocalDate referencia = (fecha != null) ? fecha : LocalDate.now();
        return repoPrestamo.buscarVencidosConItemsPendientes(referencia);
    }

    // Query 4
    public List<LibroPrestado> consultarLibrosMasPrestados(long minimo) {
        if (minimo < 1) {
            throw new ParametroInvalidoException("El mínimo de préstamos debe ser mayor o igual a 1");
        }
        return repoPrestamoItem.buscarLibrosPrestadosAlMenos(minimo);
    }

    // Query 5
    public List<MultaCliente> consultarMultasPorCliente() {
        return repoDevolucion.obtenerTotalMultasPorCliente();
    }

    // Query 6
    public List<ItemDanado> consultarItemsDanados() {
        return repoDevolucion.buscarItemsDevueltosDanados();
    }

    // Query nativo 1
    public List<EjemplarPorEstado> consultarEjemplaresPorEstado(String estado) {
        if (estado == null || estado.isBlank()) {
            throw new ParametroInvalidoException("El estado es obligatorio");
        }
        EstadoEjemplar estadoEnum;
        try {
            estadoEnum = EstadoEjemplar.valueOf(estado.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new ParametroInvalidoException(
                "Estado inválido: '" + estado + "'. Valores permitidos: " + Arrays.toString(EstadoEjemplar.values()));
        }
        return repoEjemplar.buscarEjemplaresPorEstado(estadoEnum.name()).stream()
            .map(f -> new EjemplarPorEstado((String) f[0], (String) f[1], (String) f[2]))
            .toList();
    }

    // Query nativo 2
    public List<ActividadCliente> consultarActividadPorCliente() {
        return repoPrestamo.obtenerActividadPorCliente().stream()
            .map(f -> new ActividadCliente(
                ((Number) f[0]).intValue(),
                (String) f[1],
                ((Number) f[2]).longValue(),
                ((Number) f[3]).longValue()))
            .toList();
    }
}
