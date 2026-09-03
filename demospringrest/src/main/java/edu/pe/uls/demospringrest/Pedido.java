package edu.pe.uls.demospringrest;

import java.util.List;

public class Pedido {

    private int id;
    private int clienteId;
    private List<Producto> productos;
    private double total;

    public Pedido() {
    }

    public Pedido(int id, int clienteId, List<Producto> productos, double total) {
        this.id = id;
        this.clienteId = clienteId;
        this.productos = productos;
        this.total = total;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getClienteId() {
        return clienteId;
    }
    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }
    public List<Producto> getProductos() {
        return productos;
    }
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }
}