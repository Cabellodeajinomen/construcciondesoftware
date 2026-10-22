package edu.pe.uls.con.biblioteca;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import edu.pe.uls.con.biblioteca.cu.registrardevolucion.request.RequestDevolucion;
import edu.pe.uls.con.biblioteca.cu.registrardevolucion.request.RequestDevolucion.RequestDevolucionItem;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo.RequestPrestamoItem;
import edu.pe.uls.con.biblioteca.dominio.entity.Ejemplar;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoFisico;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEjemplar;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Caso de uso 2: Registrar devolución (cabecera Devolucion + detalle DevolucionItem).
 * Cada operación REST del caso de uso tiene al menos un test exitoso y uno fallido, usando TestRestTemplate.
 * Usa su propia base H2 con los datos de data.sql.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:cudevolucion;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;DB_CLOSE_DELAY=-1"
})
@AutoConfigureTestRestTemplate
class TestCURegistrarDevolucion {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepoEjemplar repoEjemplar;

    // ---------- Operación 1: GET /devolucion/prestamo/{idPrestamo}/pendientes ----------

    @Test
    @DisplayName("Pendientes: el préstamo 1002 tiene el ejemplar 1009 sin devolver y con multa")
    void testPendientesExitoso() {
        ResponseEntity<String> response = restTemplate.getForEntity("/devolucion/prestamo/1002/pendientes", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode ej1009 = null;
        for (JsonNode item : leer(response)) {
            if (item.get("idEjemplar").asInt() == 1009) {
                ej1009 = item;
            }
        }
        assertThat(ej1009).isNotNull();
        // venció el 2026-10-08, así que ya tiene días de retraso
        assertThat(ej1009.get("diasRetraso").asLong()).isPositive();
        assertThat(ej1009.get("multaEstimada").asDouble()).isPositive();
    }

    @Test
    @DisplayName("Pendientes: un préstamo que no existe devuelve 404")
    void testPendientesPrestamoNoExiste() {
        ResponseEntity<String> response = restTemplate.getForEntity("/devolucion/prestamo/99999/pendientes", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // ---------- Operación 2: POST /devolucion/nuevo ----------

    @Test
    @DisplayName("Registrar devolución: con retraso, cobra multa y el ejemplar vuelve a estar disponible")
    void testRegistrarDevolucionConRetraso() {
        ResponseEntity<String> response = devolver(1002, new RequestDevolucionItem(1007, false));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        JsonNode json = leer(response);
        assertThat(json.get("items")).hasSize(1);
        assertThat(json.get("totalMulta").asDouble()).isPositive();
        assertThat(repoEjemplar.findById(1007).get().getEstadoDisponibilidad().getId())
                .isEqualTo(EstadoDisponibilidad.DISPONIBLE);
    }

    @Test
    @DisplayName("Registrar devolución (flujo alternativo): a tiempo, la multa es 0")
    void testRegistrarDevolucionATiempo() {
        // préstamo nuevo hecho hoy por REST, vence dentro de 7 días
        RequestPrestamo prestamo = new RequestPrestamo(1005, List.of(new RequestPrestamoItem(1011)));
        ResponseEntity<String> nuevo = restTemplate.postForEntity("/prestamo/nuevo", prestamo, String.class);
        assertThat(nuevo.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        int idPrestamo = leer(nuevo).get("idPrestamo").asInt();

        ResponseEntity<String> response = devolver(idPrestamo, new RequestDevolucionItem(1011, false));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(leer(response).get("totalMulta").asDouble()).isZero();
    }

    @Test
    @DisplayName("Registrar devolución (flujo alternativo): ejemplar dañado queda no disponible")
    void testRegistrarDevolucionDanado() {
        ResponseEntity<String> response = devolver(1003, new RequestDevolucionItem(1010, true));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(leer(response).get("items").get(0).get("danado").asBoolean()).isTrue();
        Ejemplar ejemplar = repoEjemplar.findById(1010).get();
        assertThat(ejemplar.getEstadoDisponibilidad().getId()).isEqualTo(EstadoDisponibilidad.NO_DISPONIBLE);
        assertThat(ejemplar.getEstadoFisico().getId()).isEqualTo(EstadoFisico.DANADO);
    }

    @Test
    @DisplayName("Registrar devolución: sin ejemplares devuelve 400")
    void testRegistrarDevolucionSinItems() {
        ResponseEntity<String> response = devolver(1002);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Registrar devolución: un préstamo que no existe devuelve 404")
    void testRegistrarDevolucionPrestamoNoExiste() {
        ResponseEntity<String> response = devolver(99999, new RequestDevolucionItem(1007, false));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Registrar devolución (flujo alternativo): ejemplar que no es del préstamo, 404")
    void testRegistrarDevolucionEjemplarNoPertenece() {
        ResponseEntity<String> response = devolver(1002, new RequestDevolucionItem(1001, false));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Registrar devolución (flujo alternativo): ejemplar ya devuelto, 409")
    void testRegistrarDevolucionYaDevuelto() {
        ResponseEntity<String> response = devolver(1001, new RequestDevolucionItem(1003, false));

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    // ---------- Operación 3: GET /devolucion/prestamo/{idPrestamo} ----------

    @Test
    @DisplayName("Historial: el préstamo 1001 tiene 1 devolución de 2 ejemplares con S/ 6.00 de multa")
    void testHistorialExitoso() {
        ResponseEntity<String> response = restTemplate.getForEntity("/devolucion/prestamo/1001", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode json = leer(response);
        assertThat(json).hasSize(1);
        assertThat(json.get(0).get("totalMulta").asDouble()).isEqualTo(6.0);
        assertThat(json.get(0).get("cantidadItems").asLong()).isEqualTo(2);
    }

    @Test
    @DisplayName("Historial: un préstamo que no existe devuelve 404")
    void testHistorialPrestamoNoExiste() {
        ResponseEntity<String> response = restTemplate.getForEntity("/devolucion/prestamo/99999", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // ---------- Operación 4: GET /devolucion/{id} ----------

    @Test
    @DisplayName("Consultar devolución: la devolución 1001 trae sus 2 ejemplares")
    void testConsultarDevolucionExitoso() {
        ResponseEntity<String> response = restTemplate.getForEntity("/devolucion/1001", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(leer(response).get("items")).hasSize(2);
    }

    @Test
    @DisplayName("Consultar devolución: un id que no existe devuelve 404")
    void testConsultarDevolucionNoExiste() {
        ResponseEntity<String> response = restTemplate.getForEntity("/devolucion/99999", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> devolver(int idPrestamo, RequestDevolucionItem... items) {
        List<RequestDevolucionItem> lst = new ArrayList<>(List.of(items));
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/devolucion/nuevo", new RequestDevolucion(idPrestamo, lst), String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());
        return response;
    }

    private JsonNode leer(ResponseEntity<String> response) {
        return objectMapper.readTree(response.getBody());
    }
}
