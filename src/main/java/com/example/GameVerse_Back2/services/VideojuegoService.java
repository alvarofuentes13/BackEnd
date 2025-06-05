package com.example.GameVerse_Back2.services;

import com.example.GameVerse_Back2.dto.IGDBGameDTO;
import com.example.GameVerse_Back2.models.Videojuego;
import com.example.GameVerse_Back2.repositories.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class VideojuegoService {

    @Autowired
    private VideojuegoRepository videojuegoRepository; // Repositorio para gestionar videojuegos

    @Autowired
    private IGDBService igdbService; // Servicio para interactuar con la API de IGDB

    // Método para obtener o crear un videojuego a partir de su ID en la API
    public Videojuego getOrCreateByApiId(Long apiId) {
        return videojuegoRepository.findById(apiId)
                .orElseGet(() -> {
                    IGDBGameDTO dto = igdbService.getGameById(apiId); // Obtiene el videojuego desde IGDB
                    if (dto == null) {
                        throw new RuntimeException("No se pudo obtener el juego desde IGDB");
                    }

                    // Crea un nuevo objeto Videojuego a partir del DTO
                    Videojuego juego = new Videojuego();
                    juego.setId(dto.getId());
                    juego.setTitulo(dto.getName());
                    juego.setDescripcion(dto.getSummary());
                    juego.setGenero(dto.getGenre());
                    juego.setPortada(dto.getCoverUrl());
                    juego.setFechaLanzamiento(parseFecha(dto.getReleaseDate()));
                    juego.setDesarrollador(dto.getDeveloper());

                    return videojuegoRepository.save(juego); // Guarda el nuevo videojuego en la base de datos
                });
    }

    // Método para parsear la fecha desde un String a LocalDate
    private LocalDate parseFecha(String fecha) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(fecha, formatter); // Convierte el String a LocalDate
        } catch (Exception e) {
            return null; // Retorna null si hay un error en el formato
        }
    }

    // Método para obtener todos los videojuegos
    public List<Videojuego> findAll() {
        return videojuegoRepository.findAll(); // Devuelve todos los videojuegos
    }

    // Método para encontrar un videojuego por su ID
    public Videojuego findById(Long id) {
        return videojuegoRepository.findById(id).orElse(null); // Devuelve el videojuego si existe, o null
    }

    // Método para guardar un videojuego
    public Videojuego save(Videojuego videojuego) {
        return videojuegoRepository.save(videojuego); // Guarda el videojuego en la base de datos
    }

    // Método para eliminar un videojuego por su ID
    public void deleteById(Long id) {
        videojuegoRepository.deleteById(id); // Elimina el videojuego de la base de datos
    }
}
