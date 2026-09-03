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
class ControllerPedidoTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testGetConsultarPedido() throws Exception {
        URI uri = new URI("/pedido/7");
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        String body = result.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(body.indexOf("Teclado") >= 0);
    }

    @Test
    public void testPostRegistrarPedido() throws Exception {
        URI uri = new URI("/pedido/nuevo");
        String json = """
        { "id": 1, "clienteId": 5, "total": 0, "productos": [ { "id": 1, "nombre": "Mouse", "precio": 25 }, { "id": 2, "nombre": "Teclado", "precio": 50 } ] }
        """;
        MockHttpServletRequestBuilder req = MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).content(json).accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(req).andReturn();
        String body = result.getResponse().getContentAsString();
        assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
        assertTrue(body.indexOf("75.0") >= 0);
    }
}