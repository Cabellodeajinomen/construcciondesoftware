package edu.pe.uls.con.libreria.cu.catalogo;

import edu.pe.uls.con.libreria.dominio.entity.*;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperEditorial {

    Editorial toEditorial(RequestEditorial request);

    ResponseEditorial toResponse(Editorial editorial);
}

