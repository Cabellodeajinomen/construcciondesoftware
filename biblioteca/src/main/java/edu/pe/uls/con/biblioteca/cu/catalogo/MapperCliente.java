package edu.pe.uls.con.biblioteca.cu.catalogo;

import edu.pe.uls.con.biblioteca.dominio.entity.*;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MapperCliente {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "tipoCliente", ignore = true)
    Cliente toCliente(RequestCliente request);

    @Mapping(target = "tipoCliente", source = "tipoCliente.nombre")
    ResponseCliente toResponse(Cliente cliente);
}