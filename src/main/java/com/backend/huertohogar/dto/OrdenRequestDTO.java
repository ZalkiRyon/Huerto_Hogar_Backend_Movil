package com.backend.huertohogar.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class OrdenRequestDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Integer clienteId;

    private String comentario;

    @NotEmpty(message = "La orden debe tener al menos un detalle")
    @Valid
    private List<DetalleOrdenRequestDTO> detalles;

    public OrdenRequestDTO() {
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public List<DetalleOrdenRequestDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenRequestDTO> detalles) {
        this.detalles = detalles;
    }
}
