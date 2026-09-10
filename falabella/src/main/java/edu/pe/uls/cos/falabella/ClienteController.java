package edu.pe.uls.cos.falabella;

import org.springframework.web.bind.annotation.*;
import edu.pe.uls.cos.falabella.ClienteService.*;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping("/{id}")
    public ResponseClienteResultado obtenerCliente(@PathVariable int id) {
        return clienteService.obtenerCliente(id);
    }

    @PostMapping
    public ResponseClienteResultado registrarCliente(@RequestBody RequestClienteNuevo request) {
        return clienteService.registrarCliente(request);
    }
}