package com.example.GameVerse_Back2.dto;

import com.example.GameVerse_Back2.models.Usuario;
import java.time.LocalDateTime;

// Clase DTO para representar un usuario
public class UsuarioDTO {

    private Long id;  // ID del usuario
    private String email;  // Correo electrónico del usuario
    private String name;  // Nombre del usuario
    private String password;  // Contraseña del usuario (considerar seguridad)
    private String avatar;  // URL del avatar del usuario
    private String biografia;  // Biografía del usuario
    private LocalDateTime fechaRegistro;  // Fecha de registro del usuario

    // Constructor que inicializa el DTO a partir de una entidad Usuario
    public UsuarioDTO(Usuario usuario) {
        this.id = usuario.getId();  // Asigna el ID del usuario
        this.email = usuario.getEmail();  // Asigna el correo electrónico
        this.name = usuario.getName();  // Asigna el nombre
        this.password = usuario.getPassword();  // Asigna la contraseña
        this.avatar = usuario.getAvatar();  // Asigna la URL del avatar
        this.biografia = usuario.getBiografia();  // Asigna la biografía
        this.fechaRegistro = usuario.getFechaRegistro();  // Asigna la fecha de registro
    }

    // Getters y Setters para acceder y modificar los atributos

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
