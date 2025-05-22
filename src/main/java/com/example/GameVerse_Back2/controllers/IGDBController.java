package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.dto.IGDBGameDTO;
import com.example.GameVerse_Back2.models.Review;
import com.example.GameVerse_Back2.models.Videojuego;
import com.example.GameVerse_Back2.services.IGDBService;
import com.example.GameVerse_Back2.services.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/igdb")
@CrossOrigin(origins = "*")
public class IGDBController {

    private final IGDBService igdbService;

    @Autowired
    public IGDBController(IGDBService igdbService) {
        this.igdbService = igdbService;
    }

    @PostMapping("/limited")
    public ResponseEntity<List<IGDBGameDTO>> getLimitedGames(@RequestBody int limit) {
        try {
            return ResponseEntity.ok(igdbService.getGames(limit));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/videojuegos/search/{title}")
    public ResponseEntity<List<IGDBGameDTO>> searchExternalGames(@PathVariable String title) {
        List<IGDBGameDTO> juegos = igdbService.searchGames(title);
        return ResponseEntity.ok(juegos);
    }

}
