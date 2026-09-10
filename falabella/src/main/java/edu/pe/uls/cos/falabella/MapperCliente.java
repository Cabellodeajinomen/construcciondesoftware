package edu.pe.uls.cos.falabella;

import org.mapstruct.Mapper;

import edu.pe.uls.cos.falabella.request.RequestClienteNuevo;

@Mapper(componentModel = "spring")
public interface MapperCliente {

    Cliente toCliente(RequestClienteNuevo request);
}
