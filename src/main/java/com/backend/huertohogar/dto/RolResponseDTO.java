package com.backend.huertohogar.dto;

import com.backend.huertohogar.model.Rol;

public class RolResponseDTO {
    private Integer id;
    private String nombre;

    public RolResponseDTO(Rol rol) {
        this.id = rol.getId();
        this.nombre = rol.getNombre();
    }

    public RolResponseDTO() {
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
}
