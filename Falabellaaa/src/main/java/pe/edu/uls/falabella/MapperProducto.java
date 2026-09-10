package pe.edu.uls.falabella;

import org.mapstruct.Mapper;

import pe.edu.uls.falabella.request.RequestProductoNuevo;

@Mapper(componentModel = "spring")
public interface MapperProducto {

    Producto toProducto(RequestProductoNuevo request);
}
