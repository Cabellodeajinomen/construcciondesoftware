package edu.pe.uls.con.libreria;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperCategoria {

    Categoria toCategoria(RequestCategoria request);

    ResponseCategoria toResponse(Categoria categoria);
}
