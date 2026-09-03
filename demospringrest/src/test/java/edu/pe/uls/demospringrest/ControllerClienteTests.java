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
class ControllerClienteTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetConsultarCliente() throws Exception {
        URI uri = new URI("/cliente/10");
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        String body = result.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(body.indexOf("Cliente10") >= 0);
    }

    @Test
    public void testPostRegistrarCliente() throws Exception {
        URI uri = new URI("/cliente/nuevo");
        String json = """
        { "id": 1, "nombre": "Ana", "email": "ana@correo.com" }
        """;
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        String body = result.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(body.indexOf("Ana") >= 0);
        assertTrue(body.indexOf("1001") >= 0);
    }

    @Test
    public void testPostValidarCliente() throws Exception {
        URI uri = new URI("/cliente/validar");
        String json = """
        { "id": 1, "nombre": "Luis", "email": "luis@correo.com" }
        """;
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        String body = result.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(body.indexOf("true") >= 0);
    }
}