package com.backend.huertohogar.dto;

import com.backend.huertohogar.model.Favorito;

public class FavoritoResponseDTO {

    private Integer id;
    private Integer productoId;
    private String nombreProducto;
    private Integer precioProducto;
    private String imagenProducto;


    public FavoritoResponseDTO(Favorito favorito) {
        this.id = favorito.getId();
        this.productoId = favorito.getProducto().getId();
        this.nombreProducto = favorito.getProducto().getNombre();
        this.precioProducto = favorito.getProducto().getPrecio();
        this.imagenProducto = favorito.getProducto().getImagen();
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

    public String getImagenProducto() {
        return imagenProducto;
    }

    public void setImagenProducto(String imagenProducto) {
        this.imagenProducto = imagenProducto;
    }
}
