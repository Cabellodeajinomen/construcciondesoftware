package pe.edu.uls.falabella;

import org.mapstruct.Mapper;

import pe.edu.uls.falabella.request.RequestClienteNuevo;

@Mapper(componentModel = "spring")
public interface MapperCliente {

    Cliente toCliente(RequestClienteNuevo request);
}
