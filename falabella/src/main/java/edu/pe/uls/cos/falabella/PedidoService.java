package edu.pe.uls.cos.falabella;

import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    public record RequestPedidoNuevo(int productoId, int clienteId, int cantidad) {}
    public record ResponsePedidoResultado(int id, int productoId, int clienteId, int cantidad, String estado) {}

    public ResponsePedidoResultado obtenerPedido(int id) {
        return new ResponsePedidoResultado(id, 1, 200, 2, "CONFIRMADO");
    }

    public ResponsePedidoResultado registrarPedido(RequestPedidoNuevo request) {
        if (request.cantidad() <= 0) {
            throw new RecursoInvalidoException("La cantidad del pedido debe ser mayor a cero");
        }
        return new ResponsePedidoResultado(300, request.productoId(), request.clienteId(), request.cantidad(), "CREADO");
    }
}