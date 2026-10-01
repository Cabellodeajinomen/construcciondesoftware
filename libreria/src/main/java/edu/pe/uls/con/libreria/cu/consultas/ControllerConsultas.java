package edu.pe.uls.con.libreria.cu.consultas;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.pe.uls.con.libreria.dominio.projection.ActividadCliente;
import edu.pe.uls.con.libreria.dominio.projection.EjemplarPorEstado;
import edu.pe.uls.con.libreria.dominio.projection.ItemDanado;
import edu.pe.uls.con.libreria.dominio.projection.ItemPendiente;
import edu.pe.uls.con.libreria.dominio.projection.LibroPrestado;
import edu.pe.uls.con.libreria.dominio.projection.MultaCliente;
import edu.pe.uls.con.libreria.dominio.projection.PrestamoResumen;
import edu.pe.uls.con.libreria.dominio.projection.PrestamoVencido;

@RestController
@RequestMapping("/consultas")
public class ControllerConsultas {

    ServiceConsultas serviceConsultas;

    public ControllerConsultas(ServiceConsultas serviceConsultas) {
        this.serviceConsultas = serviceConsultas;
    }

    // Query 1
    @GetMapping("/prestamos/cliente/{dni}")
    public List<PrestamoResumen> prestamosPorDni(@PathVariable("dni") String dni) {
        return serviceConsultas.consultarPrestamosPorDni(dni);
    }

    // Query 2
    @GetMapping("/prestamos/pendientes/cliente/{idCliente}")
    public List<ItemPendiente> itemsPendientes(@PathVariable("idCliente") int idCliente) {
        return serviceConsultas.consultarItemsPendientesPorCliente(idCliente);
    }

    // Query 3
    @GetMapping("/prestamos/vencidos")
    public List<PrestamoVencido> prestamosVencidos(
            @RequestParam(name = "fecha", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return serviceConsultas.consultarPrestamosVencidos(fecha);
    }

    // Query 4
    @GetMapping("/libros/mas-prestados")
    public List<LibroPrestado> librosMasPrestados(@RequestParam(name = "minimo", defaultValue = "1") long minimo) {
        return serviceConsultas.consultarLibrosMasPrestados(minimo);
    }

    // Query 5
    @GetMapping("/devoluciones/multas-por-cliente")
    public List<MultaCliente> multasPorCliente() {
        return serviceConsultas.consultarMultasPorCliente();
    }

    // Query 6
    @GetMapping("/devoluciones/items-danados")
    public List<ItemDanado> itemsDanados() {
        return serviceConsultas.consultarItemsDanados();
    }

    // Query nativo 1
    @GetMapping("/ejemplares/estado/{estado}")
    public List<EjemplarPorEstado> ejemplaresPorEstado(@PathVariable("estado") String estado) {
        return serviceConsultas.consultarEjemplaresPorEstado(estado);
    }

    // Query nativo 2
    @GetMapping("/clientes/actividad")
    public List<ActividadCliente> actividadPorCliente() {
        return serviceConsultas.consultarActividadPorCliente();
    }
}
