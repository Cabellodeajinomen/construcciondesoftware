package edu.pe.uls.cos.falabella;

import org.mapstruct.Mapper;

import edu.pe.uls.cos.falabella.request.RequestProductoNuevo;

@Mapper(componentModel = "spring")
public interface MapperProducto {

    Producto toProducto(RequestProductoNuevo request);
}
