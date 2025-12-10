package com.backend.huertohogar.dto;

import com.backend.huertohogar.model.Favorito;

public class FavoritoResponseDTO {

    private Integer id;
    private Integer productoId;
    private String nombreProducto;
    private String categoriaProducto;
    private Integer precioProducto;
    private Integer stockProducto;
    private String descripcionProducto;
    private String imagenProducto;


    public FavoritoResponseDTO(Favorito favorito) {
        this.id = favorito.getId();
        this.productoId = favorito.getProducto().getId();
        this.nombreProducto = favorito.getProducto().getNombre();
        this.categoriaProducto = favorito.getProducto().getCategoria();
        this.precioProducto = favorito.getProducto().getPrecio();
        this.stockProducto = favorito.getProducto().getStock();
        this.descripcionProducto = favorito.getProducto().getDescripcion();
        this.imagenProducto = favorito.getProducto().getImagenUrl();
    }

    public FavoritoResponseDTO() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Integer precioProducto) {
        this.precioProducto = precioProducto;
    }

    public String getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(String categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }

    public Integer getStockProducto() {
        return stockProducto;
    }

    public void setStockProducto(Integer stockProducto) {
        this.stockProducto = stockProducto;
    }

    public String getDescripcionProducto() {
        return descripcionProducto;
    }

    public void setDescripcionProducto(String descripcionProducto) {
        this.descripcionProducto = descripcionProducto;
    }

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }
}
