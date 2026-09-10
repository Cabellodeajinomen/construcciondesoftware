package edu.pe.uls.cos.falabella;

import org.springframework.web.bind.annotation.*;
import edu.pe.uls.cos.falabella.PedidoService.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/{id}")
    public ResponsePedidoResultado obtenerPedido(@PathVariable int id) {
        return pedidoService.obtenerPedido(id);
    }

    @PostMapping
    public ResponsePedidoResultado registrarPedido(@RequestBody RequestPedidoNuevo request) {
        return pedidoService.registrarPedido(request);
    }
}