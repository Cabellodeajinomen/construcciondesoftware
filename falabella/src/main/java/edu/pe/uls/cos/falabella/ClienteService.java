package edu.pe.uls.cos.falabella;

import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    public record RequestClienteNuevo(String nombre, String email) {}
    public record ResponseClienteResultado(int id, String nombre, String email, String mensaje) {}

    public ResponseClienteResultado obtenerCliente(int id) {
        return new ResponseClienteResultado(id, "Gabriel Jara", "gabriel@example.com", "Cliente encontrado");
    }

    public ResponseClienteResultado registrarCliente(RequestClienteNuevo request) {
        if (request.email() == null || !request.email().contains("@")) {
            throw new RecursoInvalidoException("El email proporcionado no es válido");
        }
        return new ResponseClienteResultado(200, request.nombre(), request.email(), "Cliente registrado exitosamente");
    }
}