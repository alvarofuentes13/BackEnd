package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.dto.IGDBGameDTO;
import com.example.GameVerse_Back2.services.IGDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/igdb")  // Define la ruta base para las solicitudes relacionadas con IGDB
@CrossOrigin(origins = "*")  // Permite solicitudes desde cualquier origen
public class IGDBController {

    private final IGDBService igdbService;  // Servicio para interactuar con la API de IGDB

    // Constructor que inyecta el servicio IGDB
    @Autowired
    public IGDBController(IGDBService igdbService) {
        this.igdbService = igdbService;  // Inicializa el servicio IGDB
    }

    // Método para obtener una lista limitada de juegos
    @PostMapping("/limited")  // Mapeo para solicitudes POST a /igdb/limited
    public ResponseEntity<List<IGDBGameDTO>> getLimitedGames(@RequestBody int limit) {
        try {
            // Intenta obtener los juegos limitados desde el servicio IGDB
            return ResponseEntity.ok(igdbService.getGames(limit));  // Retorna la lista de juegos
        } catch (Exception e) {
            // Captura cualquier excepción y retorna un error 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Método para buscar juegos externos por título
    @GetMapping("/videojuegos/search/{title}")  // Mapeo para solicitudes GET a /igdb/videojuegos/search/{title}
    public ResponseEntity<List<IGDBGameDTO>> searchExternalGames(@PathVariable String title) {
        // Busca juegos en la API de IGDB según el título proporcionado
        List<IGDBGameDTO> juegos = igdbService.searchGames(title);
        return ResponseEntity.ok(juegos);  // Retorna la lista de juegos encontrados
    }
}
