package com.example.GameVerse_Back2.dto;

import com.example.GameVerse_Back2.models.Lista;
import com.example.GameVerse_Back2.models.Videojuego;

import java.util.List;

public class ListaDTO {
    private Long id;
    private String nombre;
    private String descripcion;
    private UsuarioDTO usuario;
    private List<Videojuego> videojuegos;

    public ListaDTO(Lista lista){
        this.id = lista.getId();
        this.nombre = lista.getNombre();
        this.descripcion = lista.getDescripcion();
        this.usuario = new UsuarioDTO(lista.getUsuario());
        this.videojuegos = lista.getVideojuegos();
    }

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

    public UsuarioDTO getUsuario() {
        return usuario;
    }
    public void setUsuario(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public List<Videojuego> getVideojuegos() {
        return videojuegos;
    }
    public void setVideojuegos(List<Videojuego> videojuegos) {
        this.videojuegos = videojuegos;
    }
}
