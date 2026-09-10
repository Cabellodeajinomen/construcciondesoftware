package pe.edu.uls.falabella.service;

import java.util.Date;

import org.springframework.stereotype.Service;

import pe.edu.uls.falabella.Cliente;
import pe.edu.uls.falabella.ClienteInvalidoException;
import pe.edu.uls.falabella.Pedido;
import pe.edu.uls.falabella.PedidoInvalidoException;
import pe.edu.uls.falabella.Producto;
import pe.edu.uls.falabella.ProductoInvalidoException;
import pe.edu.uls.falabella.request.RequestProductoPrecio;
import pe.edu.uls.falabella.response.ResponseClienteResultado;
import pe.edu.uls.falabella.response.ResponsePedidoResultado;
import pe.edu.uls.falabella.response.ResponseProductoResultado;
import pe.edu.uls.falabella.response.ResponseStockResultado;

@Service
public class FalabellaService {

    public ResponseProductoResultado consultarProducto(int id) {
        if (id <= 0) {
            throw new ProductoInvalidoException("El id del producto debe ser mayor que 0");
        }
        return new ResponseProductoResultado(id, "Laptop", 8500, 10);
    }

    public ResponsePedidoResultado consultarPedido(int id) {
        if (id <= 0) {
            throw new PedidoInvalidoException("El id del pedido debe ser mayor que 0");
        }
        return new ResponsePedidoResultado(id, 1, 5, 2, 17000);
    }

    public ResponseStockResultado consultarStock(int id) {
        if (id <= 0) {
            throw new ProductoInvalidoException("El id del producto debe ser mayor que 0");
        }
        return new ResponseStockResultado(id, 10, true);
    }

    public ResponseClienteResultado consultarCliente(String dni) {
        validarDni(dni);
        return new ResponseClienteResultado(dni, "Alex", "alex@gmail.com");
    }

    public Producto registrarProducto(Producto nuevo) {
        if (nuevo == null || nuevo.getNombre() == null || nuevo.getNombre().length() < 2) {
            throw new ProductoInvalidoException(
                    "El producto debe tener un nombre de al menos dos caracteres");
        }
        if (nuevo.getPrecio() <= 0) {
            throw new ProductoInvalidoException("El precio debe ser mayor que 0");
        }
        if (nuevo.getStock() < 0) {
            throw new ProductoInvalidoException("El stock no puede ser negativo");
        }
        nuevo.setId((int) new Date().getTime());
        return nuevo;
    }

    public Pedido registrarPedido(Pedido nuevo) {
        if (nuevo == null) {
            throw new PedidoInvalidoException("El pedido no puede ser nulo");
        }
        if (nuevo.getClienteId() <= 0) {
            throw new PedidoInvalidoException("El id del cliente debe ser mayor que 0");
        }
        if (nuevo.getProductoId() <= 0) {
            throw new PedidoInvalidoException("El id del producto debe ser mayor que 0");
        }
        if (nuevo.getCantidad() <= 0) {
            throw new PedidoInvalidoException("La cantidad debe ser mayor que 0");
        }
        nuevo.setId((int) new Date().getTime());
        nuevo.setTotal(nuevo.getCantidad() * 8500);
        return nuevo;
    }

    public Producto actualizarPrecio(RequestProductoPrecio request) {
        if (request == null || request.id() <= 0) {
            throw new ProductoInvalidoException("El id del producto debe ser mayor que 0");
        }
        if (request.precio() <= 0) {
            throw new ProductoInvalidoException("El precio debe ser mayor que 0");
        }
        return new Producto(request.id(), "Laptop", request.precio(), 10);
    }

    public Cliente registrarCliente(Cliente nuevo) {
        if (nuevo == null) {
            throw new ClienteInvalidoException("El cliente no puede ser nulo");
        }
        validarDni(nuevo.getDni());
        if (nuevo.getNombre() == null || nuevo.getNombre().length() < 2) {
            throw new ClienteInvalidoException(
                    "El cliente debe tener un nombre de al menos dos caracteres");
        }
        if (nuevo.getCorreo() == null || !nuevo.getCorreo().contains("@")) {
            throw new ClienteInvalidoException("El correo no es valido");
        }
        return nuevo;
    }

    private void validarDni(String dni) {
        if (dni == null || !dni.matches("\\d{8}")) {
            throw new ClienteInvalidoException("El DNI debe tener 8 digitos");
        }
    }
}
