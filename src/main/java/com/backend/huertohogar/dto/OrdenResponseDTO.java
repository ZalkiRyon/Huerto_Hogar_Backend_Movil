package com.backend.huertohogar.dto;

import com.backend.huertohogar.model.Orden;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class OrdenResponseDTO {

    private Integer id;
    private String numeroOrden;
    private LocalDate fecha;
    private String estado;
    private Integer montoTotal;
    private String clienteNombre;
    private String comentario;
    private List<DetalleOrdenResponseDTO> detalles;

    public OrdenResponseDTO(Orden orden) {
        this.id = orden.getId();
        this.numeroOrden = orden.getNumeroOrden();
        this.fecha = orden.getFecha();
        this.estado = orden.getEstado().getNombre();
        this.montoTotal = orden.getMontoTotal();
        this.clienteNombre = orden.getUsuario().getNombre() + " " + orden.getUsuario().getApellido();
        this.comentario = orden.getComentario();
        this.detalles = orden.getDetalles().stream()
                .map(DetalleOrdenResponseDTO::new)
                .collect(Collectors.toList());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public void setNumeroOrden(String numeroOrden) {
        this.numeroOrden = numeroOrden;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(Integer montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getClienteNombre() {
        return clienteNombre;
    }

    public void setClienteNombre(String clienteNombre) {
        this.clienteNombre = clienteNombre;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public List<DetalleOrdenResponseDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetalleOrdenResponseDTO> detalles) {
        this.detalles = detalles;
    }
}
