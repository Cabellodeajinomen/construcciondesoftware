package edu.pe.uls.con.libreria;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperLibro {

    Libro toLibro(RequestLibro request);

    ResponseLibro toResponse(Libro libro);
}