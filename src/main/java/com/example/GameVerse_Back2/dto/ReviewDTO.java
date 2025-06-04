package com.example.GameVerse_Back2.dto;

import com.example.GameVerse_Back2.models.Review;
import com.example.GameVerse_Back2.models.Videojuego;

public class ReviewDTO {
    private Long id;
    private double calificacion;
    private boolean favorito;
    private String comentario;
    private UsuarioDTO usuario;
    private Videojuego videojuego;

    public ReviewDTO(Review review) {
        this.id = review.getId();
        this.comentario = review.getComentario();
        this.calificacion = review.getCalificacion();
        this.favorito = review.isFavorito();
        this.usuario = new UsuarioDTO(review.getUsuario());
        this.videojuego = review.getVideojuego();
    }

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

    public Videojuego getVideojuego() {
        return videojuego;
    }
    public void setVideojuego(Videojuego videojuego) {
        this.videojuego = videojuego;
    }
}
