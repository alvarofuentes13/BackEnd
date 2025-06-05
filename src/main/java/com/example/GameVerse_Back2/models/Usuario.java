package com.example.GameVerse_Back2.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// Clase que representa un usuario en el sistema
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID único del usuario

    @Column(nullable = false, unique = true)
    private String email;  // Correo electrónico del usuario

    @Column(nullable = false)
    private String name;  // Nombre del usuario

    @Column(nullable = false)
    private String password;  // Contraseña del usuario

    @CreationTimestamp
    private LocalDateTime fechaRegistro;  // Fecha de registro del usuario

    private String avatar;  // URL del avatar del usuario
    private String biografia;  // Biografía del usuario

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews = new HashSet<>();  // Reseñas escritas por el usuario

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Lista> listas = new HashSet<>();  // Listas creadas por el usuario

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "seguidores",
            joinColumns = @JoinColumn(name = "seguido_id"),
            inverseJoinColumns = @JoinColumn(name = "seguidor_id")
    )
    private Set<Usuario> seguidores = new HashSet<>();  // Usuarios que siguen a este usuario

    @ManyToMany(mappedBy = "seguidores")
    private Set<Usuario> seguidos = new HashSet<>();  // Usuarios que este usuario sigue

    // Constructor por defecto
    public Usuario() {}

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

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
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

    public Set<Usuario> getSeguidores() {
        return seguidores;
    }

    public void setSeguidores(Set<Usuario> seguidores) {
        this.seguidores = seguidores;
    }

    public Set<Usuario> getSeguidos() {
        return seguidos;
    }

    public void setSeguidos(Set<Usuario> seguidos) {
        this.seguidos = seguidos;
    }
}
