package com.example.GameVerse_Back2.models;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

// Clase que representa una lista de videojuegos
@Entity
@Table(name = "lista")
public class Lista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID único de la lista

    @Column(nullable = false)
    private String nombre;  // Nombre de la lista

    private String descripcion;  // Descripción de la lista

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;  // Usuario propietario de la lista

    @ManyToMany
    @JoinTable(
            name = "videojuego_lista",
            joinColumns = @JoinColumn(name = "id_lista"),
            inverseJoinColumns = @JoinColumn(name = "id_videojuego")
    )
    private List<Videojuego> videojuegos = new ArrayList<>();  // Lista de videojuegos en la lista

    // Constructor por defecto
    public Lista() {}

    // Getters y Setters para acceder y modificar los atributos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Videojuego> getVideojuegos() {
        return videojuegos;
    }

    public void setVideojuegos(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }
}
