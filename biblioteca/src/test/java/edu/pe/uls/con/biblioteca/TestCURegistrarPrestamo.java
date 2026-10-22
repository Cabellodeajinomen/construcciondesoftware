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

import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo.RequestPrestamoItem;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEjemplar;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

/**
 * Caso de uso 1: Registrar préstamo (cabecera Prestamo + detalle PrestamoItem).
 * Cada operación REST del caso de uso tiene al menos un test exitoso y uno fallido, usando TestRestTemplate.
 * Usa su propia base H2 con los datos de data.sql.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:cuprestamo;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;DB_CLOSE_DELAY=-1"
})
@AutoConfigureTestRestTemplate
class TestCURegistrarPrestamo {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RepoEjemplar repoEjemplar;

    // ---------- Operación 1: GET /prestamo/cliente/{dni} ----------

    @Test
    @DisplayName("Consultar cliente: un cliente sin deudas puede prestar")
    void testConsultarClienteExitoso() {
        ResponseEntity<String> response = restTemplate.getForEntity("/prestamo/cliente/70000005", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode json = leer(response);
        assertThat(json.get("idCliente").asInt()).isEqualTo(1005);
        assertThat(json.get("itemsVencidos").asLong()).isZero();
        assertThat(json.get("puedePrestar").asBoolean()).isTrue();
    }

    @Test
    @DisplayName("Consultar cliente: un DNI que no existe devuelve 404")
    void testConsultarClienteNoExiste() {
        ResponseEntity<String> response = restTemplate.getForEntity("/prestamo/cliente/99999999", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Consultar cliente (flujo alternativo): un cliente con préstamos vencidos no puede prestar")
    void testConsultarClienteConVencidos() {
        ResponseEntity<String> response = restTemplate.getForEntity("/prestamo/cliente/70000002", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        JsonNode json = leer(response);
        assertThat(json.get("itemsVencidos").asLong()).isEqualTo(2);
        assertThat(json.get("puedePrestar").asBoolean()).isFalse();
    }

    // ---------- Operación 2: GET /prestamo/ejemplares-disponibles/{idPublicacion} ----------

    @Test
    @DisplayName("Ejemplares disponibles: la publicación 1001 tiene los ejemplares 1001 y 1002 disponibles")
    void testEjemplaresDisponiblesExitoso() {
        ResponseEntity<String> response = restTemplate.getForEntity("/prestamo/ejemplares-disponibles/1001", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        List<Integer> ids = new ArrayList<>();
        leer(response).forEach(e -> ids.add(e.get("idEjemplar").asInt()));
        assertThat(ids).containsExactlyInAnyOrder(1001, 1002);
    }

    @Test
    @DisplayName("Ejemplares disponibles: una publicación que no existe devuelve 404")
    void testEjemplaresDisponiblesPublicacionNoExiste() {
        ResponseEntity<String> response = restTemplate.getForEntity("/prestamo/ejemplares-disponibles/99999", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    // ---------- Operación 3: POST /prestamo/nuevo ----------

    @Test
    @DisplayName("Registrar préstamo: se guarda la cabecera con 2 ejemplares y quedan prestados")
    void testRegistrarPrestamoExitoso() {
        ResponseEntity<String> response = prestar(1005, 1004, 1006);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        JsonNode json = leer(response);
        assertThat(json.get("idPrestamo").asInt()).isPositive();
        assertThat(json.get("items")).hasSize(2);
        assertThat(repoEjemplar.findById(1004).get().getEstadoDisponibilidad().getId())
                .isEqualTo(EstadoDisponibilidad.PRESTADO);
        assertThat(repoEjemplar.findById(1006).get().getEstadoDisponibilidad().getId())
                .isEqualTo(EstadoDisponibilidad.PRESTADO);
    }

    @Test
    @DisplayName("Registrar préstamo: sin ejemplares devuelve 400")
    void testRegistrarPrestamoSinItems() {
        ResponseEntity<String> response = prestar(1005);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    @DisplayName("Registrar préstamo: cliente que no existe devuelve 404")
    void testRegistrarPrestamoClienteNoExiste() {
        ResponseEntity<String> response = prestar(99999, 1013);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Registrar préstamo: ejemplar que no existe devuelve 404")
    void testRegistrarPrestamoEjemplarNoExiste() {
        ResponseEntity<String> response = prestar(1001, 99999);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    @DisplayName("Registrar préstamo (flujo alternativo): ejemplar reservado no se presta, 409")
    void testRegistrarPrestamoEjemplarNoDisponible() {
        ResponseEntity<String> response = prestar(1001, 1008);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(repoEjemplar.findById(1008).get().getEstadoDisponibilidad().getId())
                .isEqualTo(EstadoDisponibilidad.RESERVADO);
    }

    @Test
    @DisplayName("Registrar préstamo (flujo alternativo): cliente con préstamos vencidos, 409")
    void testRegistrarPrestamoClienteConVencidos() {
        ResponseEntity<String> response = prestar(1002, 1012);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(repoEjemplar.findById(1012).get().getEstadoDisponibilidad().getId())
                .isEqualTo(EstadoDisponibilidad.DISPONIBLE);
    }

    @Test
    @DisplayName("Registrar préstamo (flujo alternativo): más de 3 ejemplares, 409 y no se guarda nada")
    void testRegistrarPrestamoSuperaMaximo() {
        ResponseEntity<String> response = prestar(1004, 1011, 1012, 1013, 1002);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        // la transacción se deshizo: el primer ejemplar sigue disponible
        assertThat(repoEjemplar.findById(1011).get().getEstadoDisponibilidad().getId())
                .isEqualTo(EstadoDisponibilidad.DISPONIBLE);
    }

    @Test
    @DisplayName("Registrar préstamo (flujo alternativo): el mismo ejemplar dos veces, 409")
    void testRegistrarPrestamoEjemplarRepetido() {
        ResponseEntity<String> response = prestar(1001, 1013, 1013);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }

    // ---------- Operación 4: GET /prestamo/{id} ----------

    @Test
    @DisplayName("Consultar préstamo: el préstamo 1002 trae su cliente y sus 2 ejemplares")
    void testConsultarPrestamoExitoso() {
        ResponseEntity<String> response = restTemplate.getForEntity("/prestamo/1002", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).contains("70000002");
        assertThat(leer(response).get("items")).hasSize(2);
    }

    @Test
    @DisplayName("Consultar préstamo: un id que no existe devuelve 404")
    void testConsultarPrestamoNoExiste() {
        ResponseEntity<String> response = restTemplate.getForEntity("/prestamo/99999", String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<String> prestar(int idCliente, int... idEjemplares) {
        List<RequestPrestamoItem> items = new ArrayList<>();
        for (int idEjemplar : idEjemplares) {
            items.add(new RequestPrestamoItem(idEjemplar));
        }
        ResponseEntity<String> response = restTemplate.postForEntity(
                "/prestamo/nuevo", new RequestPrestamo(idCliente, items), String.class);
        System.out.println(">>> " + response.getStatusCode() + " " + response.getBody());
        return response;
    }

    private JsonNode leer(ResponseEntity<String> response) {
        return objectMapper.readTree(response.getBody());
    }
}
