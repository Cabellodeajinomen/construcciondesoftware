package edu.pe.uls.demospringrest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerProducto {

    @GetMapping("/producto/{id}")
    public Producto consultarProducto(@PathVariable(name = "id") int id) {
        return new Producto(id, "Producto" + id, 2 * id);
    }

    @GetMapping("/productos")
    public List<Producto> listarProductos() {
        List<Producto> lista = new ArrayList<>();
        lista.add(new Producto(1, "Teclado", 50.0));
        lista.add(new Producto(2, "Mouse", 25.0));
        lista.add(new Producto(3, "Monitor", 800.0));
        return lista;
    }

    @GetMapping("/producto/{id}/descuento/{porcentaje}")
    public Producto aplicarDescuento(@PathVariable(name = "id") int id,
            @PathVariable(name = "porcentaje") double porcentaje) {
        Producto p = new Producto(id, "Producto" + id, 100.0);
        double nuevoPrecio = p.getPrecio() - (p.getPrecio() * porcentaje / 100);
        p.setPrecio(nuevoPrecio);
        return p;
    }

    @PostMapping("/producto/nuevo")
    public Producto registrarProducto(@RequestBody Producto nuevo) {
        Date ahora = new Date();
        nuevo.setId((int) ahora.getTime());
        return nuevo;
    }

    @PostMapping("/producto/actualizarPrecio")
    public Producto actualizarPrecio(@RequestBody Producto producto) {
        producto.setPrecio(producto.getPrecio() + 15.0);
        return producto;
    }
}