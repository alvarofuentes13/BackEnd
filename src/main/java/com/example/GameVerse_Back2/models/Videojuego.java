package com.example.GameVerse_Back2.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Clase que representa un videojuego en el sistema
@Entity
@Table(name = "videojuego")
public class Videojuego {

    @Id
    @Column(unique = true, nullable = false)
    private Long id;  // ID único del videojuego

    @Column(nullable = false)
    private String titulo;  // Título del videojuego

    private String desarrollador;  // Nombre del desarrollador del videojuego
    private LocalDate fechaLanzamiento;  // Fecha de lanzamiento del videojuego
    private String genero;  // Género del videojuego

    @ElementCollection
    private List<String> plataformas = new ArrayList<>();  // Plataformas en las que está disponible el videojuego

    @Column(length = 1500)
    private String descripcion;  // Descripción del videojuego

    private String portada;  // URL de la portada del videojuego

    @OneToMany(mappedBy = "videojuego", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();  // Reseñas asociadas al videojuego

    @ManyToMany(mappedBy = "videojuegos")
    private List<Lista> listas = new ArrayList<>();  // Listas en las que aparece el videojuego

    // Constructor por defecto
    public Videojuego() {}

    // Getters y Setters para acceder y modificar los atributos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDesarrollador() {
        return desarrollador;
    }

    public void setDesarrollador(String desarrollador) {
        this.desarrollador = desarrollador;
    }

    public LocalDate getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPortada() {
        return portada;
    }

    public void setPortada(String portada) {
        this.portada = portada;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public List<Lista> getListas() {
        return listas;
    }

    public void setListas(List<Lista> listas) {
        this.listas = listas;
    }
}
