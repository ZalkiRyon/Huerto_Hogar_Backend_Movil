package com.backend.huertohogar.dto;

import com.backend.huertohogar.model.DetalleOrden;

public class DetalleOrdenResponseDTO {

    private String productoNombre;
    private Integer cantidad;
    private Integer precioUnitario;
    private Integer subtotal;

    public DetalleOrdenResponseDTO(DetalleOrden detalle) {
        this.productoNombre = detalle.getProducto().getNombre();
        this.cantidad = detalle.getCantidad();
        this.precioUnitario = detalle.getPrecioUnitario();
        this.subtotal = detalle.getSubtotal();
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public void setProductoNombre(String productoNombre) {
        this.productoNombre = productoNombre;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Integer precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
}
