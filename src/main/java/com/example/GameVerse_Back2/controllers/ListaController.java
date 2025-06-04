package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.dto.ListaDTO;
import com.example.GameVerse_Back2.models.Lista;
import com.example.GameVerse_Back2.services.ListaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/listas")
public class ListaController {

    @Autowired
    private ListaService listaService;

    @GetMapping
    public ResponseEntity<List<ListaDTO>> getAllListas() {
        List<ListaDTO> listaDTOs = listaService.findAll().stream()
                .map(ListaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListaDTO> getListaById(@PathVariable Long id) {
        Lista lista = listaService.findById(id);
        return lista != null ? ResponseEntity.ok(new ListaDTO(lista)) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ListaDTO> createLista(@RequestBody Lista lista) {
        Lista nuevaLista = listaService.save(lista);
        return ResponseEntity.ok(new ListaDTO(nuevaLista));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
        listaService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{listaId}/videojuegos")
    public ResponseEntity<?> agregarJuegosALista(@PathVariable Long listaId, @RequestBody List<Long> videojuegosIds) {
        Optional<Lista> listaOpt = listaService.agregarVideojuegosALaLista(listaId, videojuegosIds);
        return listaOpt
                .map(lista -> ResponseEntity.ok(new ListaDTO(lista)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search/{nombre}")
    public ResponseEntity<List<ListaDTO>> buscarListasPorNombre(@PathVariable String nombre) {
        List<ListaDTO> listaDTOs = listaService.buscarPorNombre(nombre).stream()
                .map(ListaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTOs);
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<ListaDTO>> buscarListasPorUsuario(@PathVariable Long usuarioId){
        List<ListaDTO> listasDTOs = listaService.buscarPorUsuario(usuarioId).stream()
                .map(ListaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listasDTOs);
    }
}
