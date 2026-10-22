package edu.pe.uls.con.biblioteca;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Consigna A: consulta por ID de cada entity del modelo de datos.
 * Los datos esperados son los que carga data.sql en la base H2 de pruebas.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TestConsultaPorId {

    @Autowired
    private MockMvc mockMvc;

    // ---------------- Publicaciones ----------------

    @Test
    @DisplayName("Debe consultar un tipo de publicación por su id")
    void testConsultarTipoPublicacion() throws Exception {
        mockMvc.perform(get("/tipo-publicacion/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nombre").value("Libro"));
    }

    @Test
    @DisplayName("Debe consultar un idioma por su id")
    void testConsultarIdioma() throws Exception {
        mockMvc.perform(get("/idioma/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("es"))
                .andExpect(jsonPath("$.nombre").value("Español"));
    }

    @Test
    @DisplayName("Debe consultar una publicación con su tipo, idioma, autores y categorías")
    void testConsultarPublicacion() throws Exception {
        mockMvc.perform(get("/publicacion/1004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Clean Code"))
                .andExpect(jsonPath("$.tipoPublicacion").value("Libro"))
                .andExpect(jsonPath("$.idioma").value("Inglés"))
                .andExpect(jsonPath("$.autores", hasSize(1)))
                .andExpect(jsonPath("$.categorias", hasSize(2)));
    }

    @Test
    @DisplayName("Debe consultar un libro que pertenece a varias categorías")
    void testConsultarLibro() throws Exception {
        mockMvc.perform(get("/libro/1004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("978-0-0000-1004-4"))
                .andExpect(jsonPath("$.numeroPaginas").value(464))
                .andExpect(jsonPath("$.categorias", containsInAnyOrder("Ingeniería de Software", "Programación")));
    }

    @Test
    @DisplayName("Debe consultar una revista por su id")
    void testConsultarRevista() throws Exception {
        mockMvc.perform(get("/revista/1007"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.issn").value("0000-1007"))
                .andExpect(jsonPath("$.volumen").value("Vol. 5"))
                .andExpect(jsonPath("$.fechaPublicacion").value("2025-06-15"));
    }

    @Test
    @DisplayName("Debe consultar una tesis por su id")
    void testConsultarTesis() throws Exception {
        mockMvc.perform(get("/tesis/1009"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.gradoAcademico").value("Ingeniero de Software"))
                .andExpect(jsonPath("$.institucion").value("Universidad La Salle"))
                .andExpect(jsonPath("$.autores", hasSize(1)));
    }

    @Test
    @DisplayName("Debe consultar una editorial por su id")
    void testConsultarEditorial() throws Exception {
        mockMvc.perform(get("/editorial/1003"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Pearson"))
                .andExpect(jsonPath("$.paisOrigen").value("Reino Unido"));
    }

    @Test
    @DisplayName("Debe consultar un autor por su id")
    void testConsultarAutor() throws Exception {
        mockMvc.perform(get("/autor/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombres").value("Mario"))
                .andExpect(jsonPath("$.apellidos").value("Vargas Llosa"));
    }

    @Test
    @DisplayName("Debe consultar una categoría por su id")
    void testConsultarCategoria() throws Exception {
        mockMvc.perform(get("/categoria/1004"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Programación"));
    }

    // ---------------- Clientes ----------------

    @Test
    @DisplayName("Debe consultar un tipo de cliente por su id")
    void testConsultarTipoCliente() throws Exception {
        mockMvc.perform(get("/tipo-cliente/1002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Docente"));
    }

    @Test
    @DisplayName("Debe consultar un cliente junto con su tipo de cliente")
    void testConsultarCliente() throws Exception {
        mockMvc.perform(get("/cliente/1003"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dni").value("40000003"))
                .andExpect(jsonPath("$.tipoCliente").value("Docente"));
    }

    // ---------------- Ubicación y estados ----------------

    @Test
    @DisplayName("Debe consultar una ubicación (stand) por su id")
    void testConsultarUbicacion() throws Exception {
        mockMvc.perform(get("/ubicacion/1003"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("SI-B1-N3"))
                .andExpect(jsonPath("$.sala").value("Sala de Ingeniería"))
                .andExpect(jsonPath("$.estante").value("B1"));
    }

    @Test
    @DisplayName("Debe consultar un estado físico por su código numérico")
    void testConsultarEstadoFisico() throws Exception {
        mockMvc.perform(get("/estado-fisico/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Dañado"));
    }

    @Test
    @DisplayName("Debe consultar un estado de disponibilidad por su código numérico")
    void testConsultarEstadoDisponibilidad() throws Exception {
        mockMvc.perform(get("/estado-disponibilidad/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Prestado"));
    }

    @Test
    @DisplayName("Debe consultar un ejemplar con su título, ubicación, estados y nota")
    void testConsultarEjemplar() throws Exception {
        mockMvc.perform(get("/ejemplar/1002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigoBarras").value("EJ-1002"))
                .andExpect(jsonPath("$.titulo").value("La ciudad y los perros"))
                .andExpect(jsonPath("$.tipoPublicacion").value("Libro"))
                .andExpect(jsonPath("$.ubicacion").value("SG-A1-N1 (Sala General, estante A1, nivel 1, sección Literatura)"))
                .andExpect(jsonPath("$.idUbicacion").value(1001))
                .andExpect(jsonPath("$.idEstadoFisico").value(2))
                .andExpect(jsonPath("$.nota").value("Tapa con desgaste leve"));
    }

    // ---------------- Reservas y parámetros ----------------

    @Test
    @DisplayName("Debe consultar una reserva con el cliente, la publicación y el ejemplar asignado")
    void testConsultarReserva() throws Exception {
        mockMvc.perform(get("/reserva/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idCliente").value(1004))
                .andExpect(jsonPath("$.cliente.nombreCompleto").value("Rosa Elena Huamán Ccama"))
                .andExpect(jsonPath("$.titulo").value("Clean Code"))
                .andExpect(jsonPath("$.ejemplar.codigoBarras").value("EJ-1008"))
                .andExpect(jsonPath("$.estado").value(2))
                .andExpect(jsonPath("$.nombreEstado").value("Asignada"));
    }

    @Test
    @DisplayName("Debe consultar un parámetro del sistema por su id")
    void testConsultarParametro() throws Exception {
        mockMvc.perform(get("/parametro/1003"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("MULTA_DIARIA"))
                .andExpect(jsonPath("$.valor").value("1.50"));
    }

    // ---------------- Préstamos y devoluciones ----------------

    @Test
    @DisplayName("Debe consultar un préstamo con el cliente y el detalle de sus ejemplares")
    void testConsultarPrestamo() throws Exception {
        mockMvc.perform(get("/prestamo/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cliente.dni").value("70000001"))
                .andExpect(jsonPath("$.cantidadItems").value(2))
                .andExpect(jsonPath("$.items[0].ejemplar.codigoBarras").value("EJ-1003"))
                .andExpect(jsonPath("$.items[0].ejemplar.titulo").value("Cien años de soledad"));
    }

    @Test
    @DisplayName("Debe consultar un ítem de préstamo por su id")
    void testConsultarPrestamoItem() throws Exception {
        mockMvc.perform(get("/prestamo-item/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPrestamo").value(1001))
                .andExpect(jsonPath("$.devuelto").value(true))
                .andExpect(jsonPath("$.cliente.tipoCliente").value("Estudiante"))
                .andExpect(jsonPath("$.ejemplar.titulo").value("Cien años de soledad"));
    }

    @Test
    @DisplayName("Debe consultar una devolución con su multa total y el detalle de los ejemplares devueltos")
    void testConsultarDevolucion() throws Exception {
        mockMvc.perform(get("/devolucion/1001"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPrestamo").value(1001))
                .andExpect(jsonPath("$.totalMulta").value(6.0))
                .andExpect(jsonPath("$.cantidadItems").value(2))
                .andExpect(jsonPath("$.items[*].ejemplar.codigoBarras", containsInAnyOrder("EJ-1003", "EJ-1005")))
                .andExpect(jsonPath("$.cliente.nombreCompleto").value("Lucía Fernández Paredes"));
    }

    @Test
    @DisplayName("Debe consultar un ítem de devolución dañado")
    void testConsultarDevolucionItem() throws Exception {
        mockMvc.perform(get("/devolucion-item/1002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.danado").value(true))
                .andExpect(jsonPath("$.ejemplar.titulo").value("Clean Code"))
                .andExpect(jsonPath("$.ejemplar.estadoFisico").value("Dañado"))
                .andExpect(jsonPath("$.diasRetraso").value(2))
                .andExpect(jsonPath("$.multa").value(3.0));
    }

    // ---------------- Caso de error ----------------

    @Test
    @DisplayName("Debe responder 404 cuando el id no existe")
    void testConsultarIdInexistente() throws Exception {
        mockMvc.perform(get("/publicacion/99999"))
                .andExpect(status().isNotFound());
    }
}
