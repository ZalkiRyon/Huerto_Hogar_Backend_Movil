package com.backend.huertohogar.dto;

import com.backend.huertohogar.model.Producto;

public class ProductoResponseDTO {
    private Integer id;
    private String nombre;
    private String categoria;
    private Integer precio;
    private Integer stock;
    private String descripcion;
    private String imagen;

    public ProductoResponseDTO() {
    }

    public ProductoResponseDTO(Producto producto) {
        this.id = producto.getId();
        this.nombre = producto.getNombre();
        this.categoria = producto.getCategoria() != null ? producto.getCategoria().getNombre() : null;
        this.precio = producto.getPrecio();
        this.stock = producto.getStock();
        this.descripcion = producto.getDescripcion();
        this.imagen = producto.getImagen();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
