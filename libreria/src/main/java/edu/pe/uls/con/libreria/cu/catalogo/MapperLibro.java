package edu.pe.uls.con.libreria.cu.catalogo;

import edu.pe.uls.con.libreria.dominio.entity.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperLibro {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "autor", ignore = true)
    @Mapping(target = "editorial", ignore = true)
    @Mapping(target = "categoria", ignore = true)
    Libro toLibro(RequestLibro request);

    @Mapping(target = "autor", expression = "java(libro.getAutor() == null ? null : libro.getAutor().getNombres() + \" \" + libro.getAutor().getApellidos())")
    @Mapping(target = "editorial", source = "editorial.nombre")
    @Mapping(target = "categoria", source = "categoria.nombre")
    ResponseLibro toResponse(Libro libro);
}