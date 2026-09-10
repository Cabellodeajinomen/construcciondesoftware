package edu.pe.uls.cos.falabella;

import org.springframework.web.bind.annotation.*;
import edu.pe.uls.cos.falabella.ProductoService.*;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping("/{id}")
    public ResponseProductoResultado obtenerProducto(@PathVariable int id) {
        return productoService.obtenerProducto(id);
    }

    @GetMapping
    public ResponseProductoListado listarProductos() {
        return productoService.listarProductos();
    }

    @PostMapping
    public ResponseProductoResultado registrarProducto(@RequestBody RequestProductoNuevo request) {
        return productoService.registrarProducto(request);
    }

    @PostMapping("/validar-stock")
    public ResponseValidarStock validarStock(@RequestBody RequestValidarStock request) {
        return productoService.validarStock(request);
    }
}