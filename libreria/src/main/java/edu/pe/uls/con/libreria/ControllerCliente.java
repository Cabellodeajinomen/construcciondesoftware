package edu.pe.uls.con.libreria;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerCliente {
    ServiceCliente serviceCliente;
    MapperCliente mapperCliente;

    public ControllerCliente(ServiceCliente serviceCliente, MapperCliente mapperCliente) {
        this.serviceCliente = serviceCliente;
        this.mapperCliente = mapperCliente;
    }

    @PostMapping("/cliente/nuevo")
    public ResponseCliente guardarCliente(@RequestBody RequestCliente nuevo) {
        Cliente cliente = mapperCliente.toCliente(nuevo);
        cliente = serviceCliente.registrarCliente(cliente);
        return mapperCliente.toResponse(cliente);
    }

    @GetMapping("/cliente/{id}")
    public ResponseCliente consultarCliente(@PathVariable(name = "id") int id) {
        return mapperCliente.toResponse(serviceCliente.consultarCliente(id));
    }

    @GetMapping("/cliente/email/{email}")
    public ResponseCliente consultarClientePorEmail(@PathVariable(name = "email") String email) {
        return mapperCliente.toResponse(serviceCliente.consultarPorEmail(email));
    }
}
