package edu.pe.uls.con.libreria;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapperCliente {

    Cliente toCliente(RequestCliente request);

    ResponseCliente toResponse(Cliente cliente);
}