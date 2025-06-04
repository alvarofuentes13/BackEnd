package com.example.GameVerse_Back2.dto;

import com.example.GameVerse_Back2.models.Lista;
import com.example.GameVerse_Back2.models.Videojuego;

import java.util.List;

// Clase DTO (Data Transfer Object) para representar una lista de videojuegos
public class ListaDTO {
    private Long id;  // ID de la lista
    private String nombre;  // Nombre de la lista
    private String descripcion;  // Descripción de la lista
    private UsuarioDTO usuario;  // Usuario asociado a la lista
    private List<Videojuego> videojuegos;  // Lista de videojuegos en esta lista

    // Constructor que inicializa el DTO a partir de una entidad Lista
    public ListaDTO(Lista lista){
        this.id = lista.getId();  // Asigna el ID de la lista
        this.nombre = lista.getNombre();  // Asigna el nombre de la lista
        this.descripcion = lista.getDescripcion();  // Asigna la descripción de la lista
        this.usuario = new UsuarioDTO(lista.getUsuario());  // Crea un UsuarioDTO a partir del usuario de la lista
        this.videojuegos = lista.getVideojuegos();  // Asigna la lista de videojuegos
    }

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
