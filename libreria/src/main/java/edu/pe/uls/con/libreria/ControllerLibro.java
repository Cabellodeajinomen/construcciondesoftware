package edu.pe.uls.con.libreria;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerLibro {
    ServiceLibro serviceLibro;
    MapperLibro mapperLibro;

    public ControllerLibro(ServiceLibro serviceLibro, MapperLibro mapperLibro) {
        this.serviceLibro = serviceLibro;
        this.mapperLibro = mapperLibro;
    }

    @PostMapping("/libro/nuevo")
    public ResponseLibro guardarLibro(@RequestBody RequestLibro nuevo) {
        Libro libro = mapperLibro.toLibro(nuevo);
        libro = serviceLibro.registrarLibro(libro);
        return mapperLibro.toResponse(libro);
    }

    @GetMapping("/libro/{id}")
    public ResponseLibro consultarLibro(@PathVariable(name = "id") int id) {
        return mapperLibro.toResponse(serviceLibro.consultarLibro(id));
    }

    @GetMapping("/libro/autor/{autor}")
    public List<ResponseLibro> consultarLibroPorAutor(@PathVariable(name = "autor") String autor) {
        return serviceLibro.consultarPorAutor(autor).stream().map(l -> mapperLibro.toResponse(l)).toList();
    }

    @GetMapping("/libro/titulo/{titulo}")
    public List<ResponseLibro> consultarLibroPorTitulo(@PathVariable(name = "titulo") String titulo) {
        return serviceLibro.consultarPorTitulo(titulo).stream().map(l -> mapperLibro.toResponse(l)).toList();
    }
}