package pe.edu.uls.falabella;

import org.mapstruct.Mapper;

import pe.edu.uls.falabella.request.RequestPedidoNuevo;

@Mapper(componentModel = "spring")
public interface MapperPedido {

    Pedido toPedido(RequestPedidoNuevo request);
}
