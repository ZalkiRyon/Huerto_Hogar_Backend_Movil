package com.backend.huertohogar.dto;

import com.backend.huertohogar.model.User;

import java.time.LocalDateTime;

public class UserResponseDTO {
    private Integer id;
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private String run;
    private String telefono;
    private String region;
    private String comuna;
    private String direccion;
    private String comentario;
    private LocalDateTime fechaRegistro;
    private String fotoPerfil;

    private String roleNombre;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nombre = user.getNombre();
        this.apellido = user.getApellido();
        this.run = user.getRun();
        this.telefono = user.getTelefono();
        this.region = user.getRegion();
        this.comuna = user.getComuna();
        this.direccion = user.getDireccion();
        this.comentario = user.getComentario();
        this.fechaRegistro = user.getFechaRegistro();
        this.fotoPerfil = user.getFotoPerfil();

        if (user.getRol() != null) {
            this.roleNombre = user.getRol().getNombre();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRun() {
        return run;
    }

    public void setRun(String run) {
        this.run = run;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getRoleNombre() {
        return roleNombre;
    }

    public void setRoleNombre(String roleNombre) {
        this.roleNombre = roleNombre;
    }

    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }
}
