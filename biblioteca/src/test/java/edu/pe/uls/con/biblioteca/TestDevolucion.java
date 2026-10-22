package edu.pe.uls.con.biblioteca;

import edu.pe.uls.con.biblioteca.cu.registrardevolucion.request.RequestDevolucion;
import edu.pe.uls.con.biblioteca.cu.registrardevolucion.request.RequestDevolucion.RequestDevolucionItem;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.entity.Parametro;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEjemplar;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoParametro;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoPrestamoItem;

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
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * Consigna B (2): registro cabecera-detalle Devolucion + DevolucionItem.
 * Usa el préstamo 1002 de data.sql, que tiene el ejemplar 1007 pendiente y venció el 2026-10-08.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TestDevolucion {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepoEjemplar repoEjemplar;

    @Autowired
    private RepoPrestamoItem repoPrestamoItem;

    @Autowired
    private RepoParametro repoParametro;

    @Test
    @DisplayName("Debe guardar una devolución con sus ítems, calcular la multa y liberar el ejemplar")
    void testRegistrarDevolucion() throws Exception {
        int idPrestamo = 1002;
        int idPrestamoItem = 1003;
        int idEjemplar = 1007;
        assertThat(repoPrestamoItem.findById(idPrestamoItem).get().isDevuelto()).isFalse();
        assertThat(repoEjemplar.findById(idEjemplar).get().getEstadoDisponibilidad().getId())
                .isEqualTo(EstadoDisponibilidad.PRESTADO);

        // multa esperada = días de retraso x multa diaria (tabla parametro)
        double multaDiaria = repoParametro.obtener(Parametro.MULTA_DIARIA).comoDecimal();
        long diasRetraso = Math.max(0, ChronoUnit.DAYS.between(LocalDate.parse("2026-10-08"), LocalDate.now()));
        double multaEsperada = diasRetraso * multaDiaria;

        List<RequestDevolucionItem> items = List.of(new RequestDevolucionItem(idEjemplar, false));
        RequestDevolucion devolucion = new RequestDevolucion(idPrestamo, items);
        mockMvc.perform(post("/devolucion/nuevo")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(devolucion)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idDevolucion").exists())
                .andExpect(jsonPath("$.items", hasSize(1)))
                .andExpect(jsonPath("$.totalMulta").value(multaEsperada));

        assertThat(repoPrestamoItem.findById(idPrestamoItem).get().isDevuelto()).isTrue();
        assertThat(repoEjemplar.findById(idEjemplar).get().getEstadoDisponibilidad().getId())
                .isEqualTo(EstadoDisponibilidad.DISPONIBLE);
    }
}
