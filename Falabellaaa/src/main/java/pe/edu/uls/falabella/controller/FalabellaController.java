package pe.edu.uls.falabella.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import pe.edu.uls.falabella.Cliente;
import pe.edu.uls.falabella.MapperCliente;
import pe.edu.uls.falabella.MapperPedido;
import pe.edu.uls.falabella.MapperProducto;
import pe.edu.uls.falabella.Pedido;
import pe.edu.uls.falabella.Producto;
import pe.edu.uls.falabella.request.RequestClienteNuevo;
import pe.edu.uls.falabella.request.RequestPedidoNuevo;
import pe.edu.uls.falabella.request.RequestProductoNuevo;
import pe.edu.uls.falabella.request.RequestProductoPrecio;
import pe.edu.uls.falabella.response.ResponseClienteResultado;
import pe.edu.uls.falabella.response.ResponsePedidoResultado;
import pe.edu.uls.falabella.response.ResponseProductoResultado;
import pe.edu.uls.falabella.response.ResponseStockResultado;
import pe.edu.uls.falabella.service.FalabellaService;

@RestController
public class FalabellaController {

    private final FalabellaService serviceFalabella;
    private final MapperProducto mapperProducto;
    private final MapperPedido mapperPedido;
    private final MapperCliente mapperCliente;

    public FalabellaController(FalabellaService serviceFalabella,
            MapperProducto mapperProducto, MapperPedido mapperPedido, MapperCliente mapperCliente) {
        this.serviceFalabella = serviceFalabella;
        this.mapperProducto = mapperProducto;
        this.mapperPedido = mapperPedido;
        this.mapperCliente = mapperCliente;
    }

    @GetMapping("/producto/{id}")
    public ResponseProductoResultado consultarProducto(@PathVariable(name = "id") int id) {
        return serviceFalabella.consultarProducto(id);
    }

    @GetMapping("/pedido/{id}")
    public ResponsePedidoResultado consultarPedido(@PathVariable(name = "id") int id) {
        return serviceFalabella.consultarPedido(id);
    }

    @GetMapping("/stock/{id}")
    public ResponseStockResultado consultarStock(@PathVariable(name = "id") int id) {
        return serviceFalabella.consultarStock(id);
    }

    @GetMapping("/cliente/{dni}")
    public ResponseClienteResultado consultarCliente(@PathVariable(name = "dni") String dni) {
        return serviceFalabella.consultarCliente(dni);
    }

    @PostMapping("/producto/nuevo")
    public Producto registrarProducto(@RequestBody RequestProductoNuevo request) {
        return serviceFalabella.registrarProducto(mapperProducto.toProducto(request));
    }

    @PostMapping("/pedido/nuevo")
    public Pedido registrarPedido(@RequestBody RequestPedidoNuevo request) {
        return serviceFalabella.registrarPedido(mapperPedido.toPedido(request));
    }

    @PostMapping("/producto/actualizarPrecio")
    public Producto actualizarPrecio(@RequestBody RequestProductoPrecio request) {
        return serviceFalabella.actualizarPrecio(request);
    }

    @PostMapping("/cliente/nuevo")
    public Cliente registrarCliente(@RequestBody RequestClienteNuevo request) {
        return serviceFalabella.registrarCliente(mapperCliente.toCliente(request));
    }
}
