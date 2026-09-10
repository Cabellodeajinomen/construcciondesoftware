package edu.pe.uls.cos.falabella;

public class Pedido {

    private int id;
    private int productoId;
    private int clienteId;
    private int cantidad;
    private String estado;

    public Pedido() {
    }

    public Pedido(int id, int productoId, int clienteId, int cantidad, String estado) {
        this.id = id;
        this.productoId = productoId;
        this.clienteId = clienteId;
        this.cantidad = cantidad;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}