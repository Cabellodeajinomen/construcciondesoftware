package edu.pe.uls.con.libreria;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperAutor {

    Autor toAutor(RequestAutor request);

    ResponseAutor toResponse(Autor autor);
}