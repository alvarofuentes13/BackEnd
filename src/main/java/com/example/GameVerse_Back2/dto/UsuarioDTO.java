package com.example.GameVerse_Back2.dto;

import com.example.GameVerse_Back2.models.Usuario;

import java.time.LocalDateTime;

public class UsuarioDTO {
    
    private Long id;
    private String email;
    private String name;
    private String password;
    private String avatar;
    private String biografia;
    private LocalDateTime fechaRegistro;

    // Constructor
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.email = usuario.getEmail();
        this.name = usuario.getName();
        this.password = usuario.getPassword();
        this.avatar = usuario.getAvatar();
        this.biografia = usuario.getBiografia();
        this.fechaRegistro = usuario.getFechaRegistro();
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatar() {
        return avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getBiografia() {
        return biografia;
    }
    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
}
