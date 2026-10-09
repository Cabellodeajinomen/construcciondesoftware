package edu.pe.uls.con.libreria.cu.catalogo;

import edu.pe.uls.con.libreria.dominio.entity.*;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperCategoria {

    Categoria toCategoria(RequestCategoria request);

    ResponseCategoria toResponse(Categoria categoria);
}
