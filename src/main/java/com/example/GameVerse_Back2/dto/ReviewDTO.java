package com.example.GameVerse_Back2.dto;

import com.example.GameVerse_Back2.models.Review;
import com.example.GameVerse_Back2.models.Videojuego;

// Clase DTO para representar una reseña de un videojuego
public class ReviewDTO {
    private Long id;  // ID de la reseña
    private double calificacion;  // Calificación dada en la reseña
    private boolean favorito;  // Indica si es un videojuego favorito
    private String comentario;  // Comentario de la reseña
    private UsuarioDTO usuario;  // Usuario que hizo la reseña
    private VideojuegoDTO videojuego;  // Videojuego al que pertenece la reseña

    // Constructor que inicializa el DTO a partir de una entidad Review
    public ReviewDTO(Review review) {
        this.id = review.getId();  // Asigna el ID de la reseña
        this.comentario = review.getComentario();  // Asigna el comentario de la reseña
        this.calificacion = review.getCalificacion();  // Asigna la calificación de la reseña
        this.favorito = review.isFavorito();  // Asigna si es favorito
        this.usuario = new UsuarioDTO(review.getUsuario());  // Crea un UsuarioDTO a partir del usuario de la reseña
        this.videojuego = new VideojuegoDTO(review.getVideojuego());  // Asigna el videojuego relacionado
    }

    // Getters y Setters para acceder y modificar los atributos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(double calificacion) {
        this.calificacion = calificacion;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public VideojuegoDTO getVideojuego() {
        return videojuego;
    }

    public void setVideojuego(VideojuegoDTO videojuego) {
        this.videojuego = videojuego;
    }
}
