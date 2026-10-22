package edu.pe.uls.con.biblioteca;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Consulta de publicaciones (libros, revistas y tesis) por año de publicación.
 */
@SpringBootTest
@AutoConfigureMockMvc
class TestConsultaPublicaciones {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Debe listar las publicaciones de un año exacto")
    void testConsultarPorAnio() throws Exception {
        mockMvc.perform(get("/consultas/publicaciones/anio/{anio}", 1967))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].titulo").value("Cien años de soledad"))
                .andExpect(jsonPath("$[0].tipoPublicacion").value("Libro"));
    }

    @Test
    @DisplayName("Debe listar las publicaciones entre dos años, ordenadas por año")
    void testConsultarEntreAnios() throws Exception {
        mockMvc.perform(get("/consultas/publicaciones/anio")
                .param("desde", "2024")
                .param("hasta", "2025"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(4)))
                .andExpect(jsonPath("$[*].anioPublicacion", contains(2024, 2024, 2025, 2025)));
    }

    @Test
    @DisplayName("Debe responder 400 si el año 'desde' es mayor que 'hasta'")
    void testRangoInvalido() throws Exception {
        mockMvc.perform(get("/consultas/publicaciones/anio")
                .param("desde", "2025")
                .param("hasta", "2000"))
                .andExpect(status().isBadRequest());
    }
}
