package edu.pe.uls.con.biblioteca;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.TestRestTemplate;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureTestRestTemplate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo;
import edu.pe.uls.con.biblioteca.cu.registrarprestamo.request.RequestPrestamo.RequestPrestamoItem;
import edu.pe.uls.con.biblioteca.dominio.entity.Ejemplar;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.entity.EstadoFisico;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEjemplar;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEstadoDisponibilidad;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoEstadoFisico;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoPublicacion;
import edu.pe.uls.con.biblioteca.dominio.repository.RepoUbicacion;

/**
 * Concurrencia: dos clientes piden prestado el MISMO ejemplar al mismo tiempo.
 * Con bloqueo solo uno lo consigue; sin bloqueo los dos lo consiguen (error de negocio).
 * Usa su propia base H2 para no mezclarse con los datos de los otros tests.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = {
        "spring.datasource.url=jdbc:h2:mem:concurrencia;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DEFAULT_NULL_ORDERING=HIGH;DB_CLOSE_DELAY=-1;LOCK_TIMEOUT=10000",
        "biblioteca.demo.pausa-ms=2000"
})
@AutoConfigureTestRestTemplate
class TestPrestamoConcurrente {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RepoEjemplar repoEjemplar;

    @Autowired
    private RepoPublicacion repoPublicacion;

    @Autowired
    private RepoUbicacion repoUbicacion;

    @Autowired
    private RepoEstadoFisico repoEstadoFisico;

    @Autowired
    private RepoEstadoDisponibilidad repoEstadoDisponibilidad;

    @Test
    @DisplayName("Con bloqueo: de dos préstamos simultáneos del mismo ejemplar solo se guarda uno")
    void testRegistrarPrestamoConcurrenteConLock() throws Exception {
        int idEjemplar = crearEjemplarDisponible("EJ-LOCK");

        List<HttpStatusCode> lstRespuestas = prestarAlMismoTiempo("/prestamo/nuevo", idEjemplar);

        long nexitos = lstRespuestas.stream().filter(s -> s.is2xxSuccessful()).count();
        System.out.println("=== CON LOCK: respuestas " + lstRespuestas + ", exitosas " + nexitos
                + " (CORRECTO: el ejemplar se prestó una sola vez)");
        assertThat(lstRespuestas).hasSize(2);
        assertThat(nexitos).isEqualTo(1);
        assertThat(lstRespuestas).contains(HttpStatus.CONFLICT);
        Ejemplar ejemplar = repoEjemplar.findById(idEjemplar).get();
        assertThat(ejemplar.getEstadoDisponibilidad().getId()).isEqualTo(EstadoDisponibilidad.PRESTADO);
    }

    @Test
    @DisplayName("Test ficticio sin bloqueo: los dos préstamos se guardan con el mismo ejemplar")
    void testRegistrarPrestamoConcurrenteSinLock() throws Exception {
        int idEjemplar = crearEjemplarDisponible("EJ-SINLOCK");

        List<HttpStatusCode> lstRespuestas = prestarAlMismoTiempo("/prestamo/nuevo/sinlock", idEjemplar);

        // esto NO debería pasar: el mismo ejemplar quedó prestado a dos clientes
        long nexitos = lstRespuestas.stream().filter(s -> s.is2xxSuccessful()).count();
        System.out.println("=== SIN LOCK: respuestas " + lstRespuestas + ", exitosas " + nexitos
                + " (INCORRECTO: el mismo ejemplar quedó prestado a dos clientes)");
        assertThat(lstRespuestas).hasSize(2);
        assertThat(nexitos).isEqualTo(2);
    }

    // crea un ejemplar nuevo en estado Disponible para que cada test empiece limpio
    private int crearEjemplarDisponible(String codigoBarras) {
        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setPublicacion(repoPublicacion.findById(1001).get());
        ejemplar.setUbicacion(repoUbicacion.findById(1001).get());
        ejemplar.setCodigoBarras(codigoBarras);
        ejemplar.setEstadoFisico(repoEstadoFisico.findById(EstadoFisico.BUENO).get());
        ejemplar.setEstadoDisponibilidad(repoEstadoDisponibilidad.findById(EstadoDisponibilidad.DISPONIBLE).get());
        ejemplar = repoEjemplar.save(ejemplar);
        System.out.println("Ejemplar creado con ID " + ejemplar.getId());
        return ejemplar.getId();
    }

    // lanza dos POST en paralelo (un hilo por cliente) y devuelve los códigos HTTP
    private List<HttpStatusCode> prestarAlMismoTiempo(String url, int idEjemplar) throws InterruptedException {
        int[] idClientes = {1004, 1005};
        int numeroHilos = idClientes.length;

        CountDownLatch latchInicio = new CountDownLatch(1);
        CountDownLatch latchFin = new CountDownLatch(numeroHilos);
        ExecutorService executorService = Executors.newFixedThreadPool(numeroHilos);
        // lista sincronizada porque la llenan dos hilos a la vez
        List<HttpStatusCode> lstRespuestas = Collections.synchronizedList(new ArrayList<>());
        for (int idCliente : idClientes) {
            RequestPrestamo prestamo = new RequestPrestamo(idCliente, List.of(new RequestPrestamoItem(idEjemplar)));
            executorService.submit(() -> {
                try {
                    latchInicio.await();
                    // se pide la respuesta como texto para poder leer también el mensaje de error
                    ResponseEntity<String> response = restTemplate.postForEntity(
                            url, prestamo, String.class
                    );
                    lstRespuestas.add(response.getStatusCode());
                    if (response.getStatusCode().is2xxSuccessful()) {
                        System.out.println(">>> OK cliente " + idCliente + ": préstamo registrado " + response.getBody());
                    } else {
                        System.out.println(">>> RECHAZADO cliente " + idCliente + ": "
                                + response.getStatusCode() + " " + response.getBody());
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    latchFin.countDown();
                }
            });
        }
        latchInicio.countDown(); // ¡Disparar al mismo tiempo!
        latchFin.await(15, TimeUnit.SECONDS);
        executorService.shutdown();
        return lstRespuestas;
    }
}
