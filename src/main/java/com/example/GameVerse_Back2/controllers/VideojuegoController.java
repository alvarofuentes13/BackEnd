package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.models.Videojuego;
import com.example.GameVerse_Back2.services.VideojuegoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")  // Permite solicitudes desde cualquier origen
@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/videojuegos")  // Define la ruta base para las solicitudes relacionadas con videojuegos
public class VideojuegoController {

    @Autowired  // Inyección automática del servicio de videojuegos
    private VideojuegoService videojuegoService;

    // Método para obtener todos los videojuegos
    @GetMapping  // Mapeo para solicitudes GET a /videojuegos
    public List<Videojuego> getAllVideojuegos() {
        return videojuegoService.findAll();  // Retorna la lista de todos los videojuegos
    }

    // Método para obtener un videojuego por su ID
    @GetMapping("/{id}")  // Mapeo para solicitudes GET a /videojuegos/{id}
    public ResponseEntity<Videojuego> getVideojuegoById(@PathVariable Long id) {
        Videojuego videojuego = videojuegoService.findById(id);  // Busca el videojuego por ID
        return videojuego != null ? ResponseEntity.ok(videojuego) : ResponseEntity.notFound().build();  // Retorna el videojuego o 404 si no existe
    }

    // Método para crear un nuevo videojuego
    @PostMapping("/id")  // Mapeo para solicitudes POST a /videojuegos/id
    public Videojuego createVideojuego(@RequestBody Videojuego videojuego) {
        return videojuegoService.save(videojuego);  // Guarda y retorna el nuevo videojuego creado
    }

    // Método para eliminar un videojuego por su ID
    @DeleteMapping("/{id}")  // Mapeo para solicitudes DELETE a /videojuegos/{id}
    public ResponseEntity<Void> deleteVideojuego(@PathVariable Long id) {
        videojuegoService.deleteById(id);  // Elimina el videojuego por ID
        return ResponseEntity.noContent().build();  // Retorna respuesta 204 No Content
    }

    // Método para obtener o crear un videojuego basado en un ID de API
    @GetMapping("/get-or-create/{apiId}")  // Mapeo para solicitudes GET a /videojuegos/get-or-create/{apiId}
    public ResponseEntity<Videojuego> getOrCreateByApiId(@PathVariable Long apiId) {
        Videojuego videojuego = videojuegoService.getOrCreateByApiId(apiId);  // Busca o crea el videojuego por ID de API
        return ResponseEntity.ok(videojuego);  // Retorna el videojuego encontrado o creado
    }
}
