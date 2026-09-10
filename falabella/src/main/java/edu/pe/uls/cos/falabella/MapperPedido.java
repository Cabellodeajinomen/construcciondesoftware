package edu.pe.uls.cos.falabella;

import org.mapstruct.Mapper;

import edu.pe.uls.cos.falabella.request.RequestPedidoNuevo;

@Mapper(componentModel = "spring")
public interface MapperPedido {

    Pedido toPedido(RequestPedidoNuevo request);
}
