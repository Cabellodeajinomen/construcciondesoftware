package com.actividad1.ulasalle;

import com.actividad1.ulasalle.Biblioteca;
import com.actividad1.ulasalle.Categoria;
import com.actividad1.ulasalle.Libro;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/libro/{id}")
    public Libro getLibro(@PathVariable int id) {
        return new Libro(id, "El Principito", "Antoine de Saint-Exupery", 1943);
    }

    @GetMapping("/categoria/{id}")
    public Categoria getCategoria(@PathVariable int id) {
        return new Categoria(id, "Literatura Latinoamericana", "Obras de autores de America Latina");
    }

    @GetMapping("/biblioteca/{id}")
    public Biblioteca getBiblioteca(@PathVariable int id) {
        return new Biblioteca(id, "Biblioteca Central", "Av. Universitaria 456");
    }
}