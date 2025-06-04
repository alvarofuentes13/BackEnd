package com.example.GameVerse_Back2.dto;

// Clase para representar una solicitud de inicio de sesión
public class LoginRequest {

    private String email;  // Correo electrónico del usuario
    private String password;  // Contraseña del usuario

    // Getter para obtener el correo electrónico
    public String getEmail() {
        return email;
    }

    // Setter para establecer el correo electrónico
    public void setEmail(String email) {
        this.email = email;
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
