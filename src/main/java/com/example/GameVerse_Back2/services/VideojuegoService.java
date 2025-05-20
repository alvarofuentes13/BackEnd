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
    private VideojuegoRepository videojuegoRepository;

    @Autowired
    private IGDBService igdbService;

    public Videojuego getOrCreateByApiId(Long apiId) {
        return videojuegoRepository.findById(apiId)
                .orElseGet(() -> {
                    IGDBGameDTO dto = igdbService.getGameById(apiId);
                    if (dto == null) {
                        throw new RuntimeException("No se pudo obtener el juego desde IGDB");
                    }

                    Videojuego juego = new Videojuego();
                    juego.setId(dto.getId());
                    juego.setTitulo(dto.getName());
                    juego.setDescripcion(dto.getSummary());
                    juego.setGenero(dto.getGenre());
                    juego.setPortada(dto.getCoverUrl());
                    juego.setFechaLanzamiento(parseFecha(dto.getReleaseDate()));
                    juego.setDesarrollador(dto.getDeveloper());

                    return videojuegoRepository.save(juego);
                });
    }

    private LocalDate parseFecha(String fecha) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(fecha, formatter);
        } catch (Exception e) {
            return null;
        }
    }

    public List<Videojuego> findAll() {
        return videojuegoRepository.findAll();
    }

    public Videojuego findById(Long id) {
        return videojuegoRepository.findById(id).orElse(null);
    }

    public Videojuego save(Videojuego videojuego) {
        return videojuegoRepository.save(videojuego);
    }

    public void deleteById(Long id) {
        videojuegoRepository.deleteById(id);
    }
}
