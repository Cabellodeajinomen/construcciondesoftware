package edu.pe.uls.con.libreria;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerCategoria {
    ServiceCategoria serviceCategoria;
    MapperCategoria mapperCategoria;

    public ControllerCategoria(ServiceCategoria serviceCategoria, MapperCategoria mapperCategoria) {
        this.serviceCategoria = serviceCategoria;
        this.mapperCategoria = mapperCategoria;
    }

    @PostMapping("/categoria/nuevo")
    public ResponseCategoria guardarCategoria(@RequestBody RequestCategoria nuevo) {
        Categoria categoria = mapperCategoria.toCategoria(nuevo);
        categoria = serviceCategoria.registrarCategoria(categoria);
        return mapperCategoria.toResponse(categoria);
    }

    @GetMapping("/categoria/{id}")
    public ResponseCategoria consultarCategoria(@PathVariable(name = "id") int id) {
        return mapperCategoria.toResponse(serviceCategoria.consultarCategoria(id));
    }

    @GetMapping("/categoria/nombre/{texto}")
    public List<ResponseCategoria> consultarCategoriaPorNombre(@PathVariable(name = "texto") String texto) {
        return serviceCategoria.consultarPorNombre(texto).stream().map(c -> mapperCategoria.toResponse(c)).toList();
    }
}

