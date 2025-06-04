package com.example.GameVerse_Back2.models;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

// Clase que representa una reseña de un videojuego
@Entity
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID único de la reseña

    @CreationTimestamp
    private LocalDateTime fechaCompra;  // Fecha en la que se realizó la compra

    private double calificacion;  // Calificación dada en la reseña
    private boolean favorito;  // Indica si es un videojuego favorito
    private String comentario;  // Comentario de la reseña

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;  // Usuario que realizó la reseña

    @ManyToOne
    @JoinColumn(name = "id_videojuego", nullable = false)
    private Videojuego videojuego;  // Videojuego que se está reseñando

    // Constructor por defecto
    public Review() {}

    // Getters y Setters para acceder y modificar los atributos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDateTime fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Videojuego getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }
}
