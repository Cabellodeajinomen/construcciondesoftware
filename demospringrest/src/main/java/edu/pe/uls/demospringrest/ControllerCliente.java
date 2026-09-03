package edu.pe.uls.demospringrest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerCliente {

    @GetMapping("/cliente/{id}")
    public Cliente consultarCliente(@PathVariable(name = "id") int id) {
        return new Cliente(id, "Cliente" + id, "cliente" + id + "@correo.com");
    }

    @PostMapping("/cliente/nuevo")
    public Cliente registrarCliente(@RequestBody Cliente nuevo) {
        nuevo.setId(nuevo.getId() + 1000);
        return nuevo;
    }

    @PostMapping("/cliente/validar")
    public ValidacionResponse validarCliente(@RequestBody Cliente cliente) {
        boolean valido = cliente.getEmail() != null && cliente.getEmail().contains("@");
        String mensaje = valido ? "Cliente valido" : "Email invalido";
        return new ValidacionResponse(valido, mensaje);
    }
}