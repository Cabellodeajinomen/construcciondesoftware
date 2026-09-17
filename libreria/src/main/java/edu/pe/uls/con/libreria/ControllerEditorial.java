package edu.pe.uls.con.libreria;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControllerEditorial {
    ServiceEditorial serviceEditorial;
    MapperEditorial mapperEditorial;

    public ControllerEditorial(ServiceEditorial serviceEditorial, MapperEditorial mapperEditorial) {
        this.serviceEditorial = serviceEditorial;
        this.mapperEditorial = mapperEditorial;
    }

    @PostMapping("/editorial/nuevo")
    public ResponseEditorial guardarEditorial(@RequestBody RequestEditorial nuevo) {
        Editorial editorial = mapperEditorial.toEditorial(nuevo);
        editorial = serviceEditorial.registrarEditorial(editorial);
        return mapperEditorial.toResponse(editorial);
    }

    @GetMapping("/editorial/{id}")
    public ResponseEditorial consultarEditorial(@PathVariable(name = "id") int id) {
        return mapperEditorial.toResponse(serviceEditorial.consultarEditorial(id));
    }

    @GetMapping("/editorial/pais/{pais}")
    public List<ResponseEditorial> consultarEditorialPorPais(@PathVariable(name = "pais") String pais) {
        return serviceEditorial.consultarPorPais(pais).stream().map(e -> mapperEditorial.toResponse(e)).toList();
    }
}
