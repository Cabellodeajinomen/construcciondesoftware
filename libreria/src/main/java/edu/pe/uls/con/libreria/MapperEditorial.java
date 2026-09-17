package edu.pe.uls.con.libreria;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperEditorial {

    Editorial toEditorial(RequestEditorial request);

    ResponseEditorial toResponse(Editorial editorial);
}

