package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.dto.VideojuegoDTO;
import com.example.GameVerse_Back2.models.Videojuego;
import com.example.GameVerse_Back2.services.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/videojuegos")
public class VideojuegoController {

    @Autowired
    private VideojuegoService videojuegoService;

    // Obtener todos los videojuegos
    @GetMapping
    public ResponseEntity<List<VideojuegoDTO>> getAllVideojuegos() {
        List<VideojuegoDTO> videojuegoDTOS = videojuegoService.findAll()
                .stream()
                .map(VideojuegoDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(videojuegoDTOS);
    }

    // Obtener un videojuego por su ID
    @GetMapping("/{id}")
    public ResponseEntity<VideojuegoDTO> getVideojuegoById(@PathVariable Long id) {
        Videojuego videojuego = videojuegoService.findById(id);
        return videojuego != null ?
                ResponseEntity.ok(new VideojuegoDTO(videojuego)) :
                ResponseEntity.notFound().build();
    }

    // Crear un nuevo videojuego
    @PostMapping("/id")
    public ResponseEntity<VideojuegoDTO> createVideojuego(@RequestBody Videojuego videojuego) {
        Videojuego creado = videojuegoService.save(videojuego);
        return ResponseEntity.ok(new VideojuegoDTO(creado));
    }

    // Eliminar un videojuego
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideojuego(@PathVariable Long id) {
        videojuegoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener o crear un videojuego por API ID
    @GetMapping("/get-or-create/{apiId}")
    public ResponseEntity<VideojuegoDTO> getOrCreateByApiId(@PathVariable Long apiId) {
        Videojuego videojuego = videojuegoService.getOrCreateByApiId(apiId);
        return ResponseEntity.ok(new VideojuegoDTO(videojuego));
    }
}
