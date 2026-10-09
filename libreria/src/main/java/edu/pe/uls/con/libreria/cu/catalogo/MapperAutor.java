package edu.pe.uls.con.libreria.cu.catalogo;

import edu.pe.uls.con.libreria.dominio.entity.*;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperAutor {

    Autor toAutor(RequestAutor request);

    ResponseAutor toResponse(Autor autor);
}