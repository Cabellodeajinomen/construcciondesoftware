package edu.pe.uls.con.libreria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ServiceCliente {

    @Autowired
    RepositoryCliente repoCliente;

    @Transactional
    public Cliente registrarCliente(Cliente nuevo) {
        repoCliente.findByDni(nuevo.getDni()).ifPresent(c -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe un cliente registrado con el DNI: " + nuevo.getDni());
        });
        return repoCliente.save(nuevo);
    }

    public Cliente consultarCliente(int id) {
        return repoCliente.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado: " + id));
    }

    public Cliente consultarPorEmail(String email) {
        return repoCliente.findByEmailIgnoreCase(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con email: " + email));
    }
}
