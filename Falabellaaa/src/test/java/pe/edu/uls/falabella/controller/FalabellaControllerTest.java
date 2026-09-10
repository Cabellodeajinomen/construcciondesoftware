package pe.edu.uls.falabella.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class FalabellaControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testGetProducto() throws Exception {
        MvcResult result = ejecutarGet("/producto/301");
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("301"));
    }

    @Test
    void testGetProductoError() throws Exception {
        MvcResult result = ejecutarGet("/producto/0");
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("id del producto"));
    }

    @Test
    void testGetPedido() throws Exception {
        MvcResult result = ejecutarGet("/pedido/10");
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("10"));
    }

    @Test
    void testGetPedidoError() throws Exception {
        MvcResult result = ejecutarGet("/pedido/0");
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("id del pedido"));
    }

    @Test
    void testGetStock() throws Exception {
        MvcResult result = ejecutarGet("/stock/5");
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("true"));
    }

    @Test
    void testGetStockError() throws Exception {
        MvcResult result = ejecutarGet("/stock/0");
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("id del producto"));
    }

    @Test
    void testGetCliente() throws Exception {
        MvcResult result = ejecutarGet("/cliente/12345678");
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("12345678"));
    }

    @Test
    void testGetClienteError() throws Exception {
        MvcResult result = ejecutarGet("/cliente/123");
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("DNI"));
    }

    @Test
    void testPostProducto() throws Exception {
        MvcResult result = ejecutarPost("/producto/nuevo", """
                {"nombre":"Monitor","precio":800,"stock":20}
                """);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("Monitor"));
    }

    @Test
    void testPostProductoError() throws Exception {
        MvcResult result = ejecutarPost("/producto/nuevo", """
                {"nombre":"A","precio":800,"stock":20}
                """);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("al menos dos caracteres"));
    }

    @Test
    void testPostPedido() throws Exception {
        MvcResult result = ejecutarPost("/pedido/nuevo", """
                {"clienteId":1,"productoId":5,"cantidad":2}
                """);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("\"cantidad\":2"));
    }

    @Test
    void testPostPedidoError() throws Exception {
        MvcResult result = ejecutarPost("/pedido/nuevo", """
                {"clienteId":1,"productoId":5,"cantidad":0}
                """);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("cantidad"));
    }

    @Test
    void testPostActualizarPrecio() throws Exception {
        MvcResult result = ejecutarPost("/producto/actualizarPrecio", """
                {"id":5,"precio":900}
                """);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("900"));
    }

    @Test
    void testPostActualizarPrecioError() throws Exception {
        MvcResult result = ejecutarPost("/producto/actualizarPrecio", """
                {"id":5,"precio":-100}
                """);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("precio"));
    }

    @Test
    void testPostCliente() throws Exception {
        MvcResult result = ejecutarPost("/cliente/nuevo", """
                {"dni":"12345678","nombre":"Alex","correo":"alex@gmail.com"}
                """);
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("Alex"));
    }

    @Test
    void testPostClienteError() throws Exception {
        MvcResult result = ejecutarPost("/cliente/nuevo", """
                {"dni":"123","nombre":"Alex","correo":"alex@gmail.com"}
                """);
        assertEquals(HttpStatus.BAD_REQUEST.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().contains("DNI"));
    }

    private MvcResult ejecutarGet(String path) throws Exception {
        URI uri = new URI(path);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request).andReturn();
    }

    private MvcResult ejecutarPost(String path, String json) throws Exception {
        URI uri = new URI(path);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON);
        return mockMvc.perform(request).andReturn();
    }
}
