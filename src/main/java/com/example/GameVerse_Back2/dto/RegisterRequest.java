package com.example.GameVerse_Back2.dto;

// Clase para representar una solicitud de registro de usuario
public class RegisterRequest {

    private String email;  // Correo electrónico del usuario
    private String name;   // Nombre del usuario
    private String password;  // Contraseña del usuario

    // Getter para obtener el correo electrónico
    public String getEmail() {
        return email;
    }

    // Setter para establecer el correo electrónico
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter para obtener el nombre
    public String getName() {
        return name;
    }

    // Setter para establecer el nombre
    public void setName(String name) {
        this.name = name;
    }

    // Getter para obtener la contraseña
    public String getPassword() {
        return password;
    }

    // Setter para establecer la contraseña
    public void setPassword(String password) {
        this.password = password;
    }
}
