package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.models.Lista;
import com.example.GameVerse_Back2.models.Review;
import com.example.GameVerse_Back2.models.Videojuego;
import com.example.GameVerse_Back2.services.ListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/listas")
public class ListaController {

    @Autowired
    private ListaService listaService;

    @GetMapping
    public List<Lista> getAllListas() {
        return listaService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lista> getListaById(@PathVariable Long id) {
        Lista lista = listaService.findById(id);
        return lista != null ? ResponseEntity.ok(lista) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Lista createLista(@RequestBody Lista lista) {
        return listaService.save(lista);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
        listaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{listaId}/videojuegos")
    public ResponseEntity<?> agregarJuegosALista(@PathVariable Long listaId, @RequestBody List<Long> videojuegosIds) {
        return listaService.agregarVideojuegosALaLista(listaId, videojuegosIds)
                .map(lista -> ResponseEntity.ok("Videojuegos agregados correctamente."))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/{nombre}")
    public List<Lista> buscarListasPorNombre(@PathVariable String nombre) {
        return listaService.buscarPorNombre(nombre);
    }
}
