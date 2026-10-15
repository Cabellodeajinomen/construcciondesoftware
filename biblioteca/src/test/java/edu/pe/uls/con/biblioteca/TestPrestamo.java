package edu.pe.uls.con.biblioteca;

import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo.RequestPrestamoItem;
import edu.pe.uls.con.biblioteca.dominio.entity.Ejemplar;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.entity.Parametro;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEjemplar;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoParametro;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.List;

/**
 * registro cabecera-detalle Prestamo + PrestamoItem.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TestPrestamo {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepoEjemplar repoEjemplar;

    @Autowired
    private RepoParametro repoParametro;

    @Test
    @DisplayName("Debe guardar un préstamo con sus ítems y dejar los ejemplares en estado Prestado")
    void testRegistrarPrestamo() throws Exception {
        int idEjemplar1 = 1001;
        int idEjemplar2 = 1004;
        assertThat(estadoDisponibilidad(idEjemplar1)).isEqualTo(EstadoDisponibilidad.DISPONIBLE);
        assertThat(estadoDisponibilidad(idEjemplar2)).isEqualTo(EstadoDisponibilidad.DISPONIBLE);
        int diasPrestamo = repoParametro.obtener(Parametro.DIAS_PRESTAMO).comoEntero();

        List<RequestPrestamoItem> items = List.of(
                new RequestPrestamoItem(idEjemplar1),
                new RequestPrestamoItem(idEjemplar2)
        );
        RequestPrestamo prestamo = new RequestPrestamo(1005, items);
        mockMvc.perform(post("/prestamo/nuevo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(prestamo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPrestamo").exists())
                .andExpect(jsonPath("$.items", hasSize(2)))
                .andExpect(jsonPath("$.fechaLimite").value(LocalDate.now().plusDays(diasPrestamo).toString()));

        assertThat(estadoDisponibilidad(idEjemplar1)).isEqualTo(EstadoDisponibilidad.PRESTADO);
        assertThat(estadoDisponibilidad(idEjemplar2)).isEqualTo(EstadoDisponibilidad.PRESTADO);
    }

    private int estadoDisponibilidad(int idEjemplar) {
        Ejemplar ejemplar = repoEjemplar.findById(idEjemplar).get();
        return ejemplar.getEstadoDisponibilidad().getId();
    }
}
