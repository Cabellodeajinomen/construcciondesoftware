package edu.pe.uls.con.biblioteca.cu.catalogo;

import edu.pe.uls.con.biblioteca.dominio.entity.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperLibro {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoPublicacion", ignore = true)
    @Mapping(target = "idioma", ignore = true)
    @Mapping(target = "editorial", ignore = true)
    @Mapping(target = "autores", ignore = true)
    @Mapping(target = "categorias", ignore = true)
    Libro toLibro(RequestLibro request);

    @Mapping(target = "idioma", source = "idioma.nombre")
    @Mapping(target = "editorial", source = "editorial.nombre")
    @Mapping(target = "autores", expression = "java(libro.getAutores().stream().map(a -> a.getNombres() + \" \" + a.getApellidos()).sorted().toList())")
    @Mapping(target = "categorias", expression = "java(libro.getCategorias().stream().map(c -> c.getNombre()).sorted().toList())")
    ResponseLibro toResponse(Libro libro);
}