package edu.pe.uls.con.libreria;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerAutor {
    ServiceAutor serviceAutor;
    MapperAutor mapperAutor;

    public ControllerAutor(ServiceAutor serviceAutor, MapperAutor mapperAutor) {
        this.serviceAutor = serviceAutor;
        this.mapperAutor = mapperAutor;
    }

    @PostMapping("/autor/nuevo")
    public ResponseAutor guardarAutor(@RequestBody RequestAutor nuevo) {
        Autor autor = mapperAutor.toAutor(nuevo);
        autor = serviceAutor.registrarAutor(autor);
        return mapperAutor.toResponse(autor);
    }

    @GetMapping("/autor/{id}")
    public ResponseAutor consultarAutor(@PathVariable(name = "id") int id) {
        return mapperAutor.toResponse(serviceAutor.consultarAutor(id));
    }

    @GetMapping("/autor/nacionalidad/{nacionalidad}")
    public List<ResponseAutor> consultarAutorPorNacionalidad(@PathVariable(name = "nacionalidad") String nacionalidad) {
        return serviceAutor.consultarPorNacionalidad(nacionalidad).stream().map(a -> mapperAutor.toResponse(a)).toList();
    }
}

