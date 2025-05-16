package com.example.GameVerse_Back2.dto;

public class IGDBGameDTO {
    private Long id;
    private String name;
    private String summary;
    private String releaseDate;
    private String genre;
    private String coverUrl;
    private String developer;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getCoverUrl() { return coverUrl; }
    public void setCoverUrl(String coverUrl) { this.coverUrl = coverUrl; }

    public String getDeveloper() { return developer; }
    public void setDeveloper(String developer) { this.developer = developer; }
}
