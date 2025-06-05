package com.example.GameVerse_Back2.dto;

// Clase DTO (Data Transfer Object) para representar un videojuego proveniente de IGDB
public class IGDBGameDTO {
    private Long id;  // ID del videojuego
    private String name;  // Nombre del videojuego
    private String summary;  // Resumen del videojuego
    private String releaseDate;  // Fecha de lanzamiento del videojuego
    private String genre;  // Género del videojuego
    private String coverUrl;  // URL de la portada del videojuego
    private String developer;  // Desarrollador del videojuego

    // Getters y Setters para acceder y modificar los atributos

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }
}
