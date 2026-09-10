package edu.pe.uls.cos.falabella;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    public record RequestProductoNuevo(String nombre, double precio, int stock) {}
    public record ResponseProductoResultado(int id, String nombre, double precio, int stock, String mensaje) {}
    public record ResponseProductoListado(List<ResponseProductoResultado> productos) {}
    public record RequestValidarStock(int productoId, int cantidad) {}
    public record ResponseValidarStock(int productoId, boolean disponible, int stockRestante) {}

    public ResponseProductoResultado obtenerProducto(int id) {
        return new ResponseProductoResultado(id, "Zapatilla Running", 199.90, 25, "Producto encontrado");
    }

    public ResponseProductoListado listarProductos() {
        List<ResponseProductoResultado> productos = List.of(
                new ResponseProductoResultado(1, "Zapatilla Running", 199.90, 25, "OK"),
                new ResponseProductoResultado(2, "Polo Deportivo", 59.90, 40, "OK")
        );
        return new ResponseProductoListado(productos);
    }

    public ResponseProductoResultado registrarProducto(RequestProductoNuevo request) {
        if (request.nombre() == null || request.nombre().isBlank()) {
            throw new RecursoInvalidoException("El nombre del producto no puede estar vacío");
        }
        return new ResponseProductoResultado(100, request.nombre(), request.precio(), request.stock(), "Producto registrado exitosamente");
    }

    public ResponseValidarStock validarStock(RequestValidarStock request) {
        if (request.cantidad() <= 0) {
            throw new RecursoInvalidoException("La cantidad a validar debe ser mayor a cero");
        }
        return new ResponseValidarStock(request.productoId(), true, 25 - request.cantidad());
    }
}