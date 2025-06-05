package com.example.GameVerse_Back2.dto;

import com.example.GameVerse_Back2.models.Videojuego;

import java.time.LocalDate;

public class VideojuegoDTO {
    private Long id;
    private String titulo;
    private String desarrollador;
    private LocalDate fechaLanzamiento;
    private String genero;
    private String descripcion;
    private String portada;

    public VideojuegoDTO(Videojuego videojuego){
        this.id = videojuego.getId();
        this.titulo = videojuego.getTitulo();
        this.desarrollador = videojuego.getDesarrollador();
        this.fechaLanzamiento = videojuego.getFechaLanzamiento();
        this.genero = videojuego.getGenero();
        this.descripcion = videojuego.getDescripcion();
        this.portada = videojuego.getPortada();
    }

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
}
