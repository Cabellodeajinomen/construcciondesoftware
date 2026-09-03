package edu.pe.uls.demospringrest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerPedido {

    @GetMapping("/pedido/{id}")
    public Pedido consultarPedido(@PathVariable(name = "id") int id) {
        List<Producto> productos = new ArrayList<>();
        productos.add(new Producto(1, "Teclado", 50.0));
        return new Pedido(id, 1, productos, 50.0);
    }

    @PostMapping("/pedido/nuevo")
    public Pedido registrarPedido(@RequestBody Pedido nuevo) {
        double total = 0;
        if (nuevo.getProductos() != null) {
            for (Producto p : nuevo.getProductos()) {
                total += p.getPrecio();
            }
        }
        nuevo.setTotal(total);
        nuevo.setId(nuevo.getId() + 500);
        return nuevo;
    }
}