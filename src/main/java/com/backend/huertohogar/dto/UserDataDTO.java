package com.backend.huertohogar.dto;

import java.time.LocalDateTime;

public class UserDataDTO {
    private Integer id;
    private String email;
    private String nombre;
    private String apellido;
    private String run;
    private String telefono;
    private String region;
    private String comuna;
    private String direccion;
    private String comentario;
    private LocalDateTime fechaRegistro;
    private String rolNombre;
    private Integer roleId;

    public UserDataDTO() {}

    public UserDataDTO(Integer id, String email, String nombre, String apellido, String run, 
                       String telefono, String region, String comuna, String direccion, 
                       String comentario, LocalDateTime fechaRegistro, String rolNombre, Integer roleId) {
        this.id = id;
        this.email = email;
        this.nombre = nombre;
        this.apellido = apellido;
        this.run = run;
        this.telefono = telefono;
        this.region = region;
        this.comuna = comuna;
        this.direccion = direccion;
        this.comentario = comentario;
        this.fechaRegistro = fechaRegistro;
        this.rolNombre = rolNombre;
        this.roleId = roleId;
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

    public String getRolNombre() {
        return rolNombre;
    }

    public void setRolNombre(String rolNombre) {
        this.rolNombre = rolNombre;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
