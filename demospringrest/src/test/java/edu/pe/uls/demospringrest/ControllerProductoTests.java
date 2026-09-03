package edu.pe.uls.demospringrest;

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
class ControllerProductoTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetConsultarProducto() throws Exception {
        URI uri = new URI("/producto/301");
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(result.getResponse().getContentAsString().indexOf("301") >= 0);
    }

    @Test
    public void testGetListarProductos() throws Exception {
        URI uri = new URI("/productos");
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        String body = result.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(body.indexOf("Teclado") >= 0);
    }

    @Test
    public void testGetAplicarDescuento() throws Exception {
        URI uri = new URI("/producto/5/descuento/10");
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        String body = result.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(body.indexOf("90.0") >= 0);
    }

    @Test
    public void testPostRegistrarProducto() throws Exception {
        URI uri = new URI("/producto/nuevo");
        String json = """
        { "id": 8, "nombre": "Monitor", "precio": 800 }
        """;
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        String body = result.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(body.indexOf("Monitor") >= 0);
        assertTrue(body.indexOf("800") >= 0);
    }

    @Test
    public void testPostActualizarPrecio() throws Exception {
        URI uri = new URI("/producto/actualizarPrecio");
        String json = """
        { "id": 1, "nombre": "Silla", "precio": 100 }
        """;
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        String body = result.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(body.indexOf("115.0") >= 0);
    }
}