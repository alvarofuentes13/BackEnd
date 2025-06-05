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

@CrossOrigin("*")  // Permite solicitudes desde cualquier origen
@RestController  // Indica que esta clase es un controlador REST
@RequestMapping("/listas")  // Define la ruta base para las solicitudes relacionadas con listas
public class ListaController {

    @Autowired  // Inyección automática del servicio de listas
    private ListaService listaService;

    // Método para obtener todas las listas
    @GetMapping  // Mapeo para solicitudes GET a /listas
    public ResponseEntity<List<ListaDTO>> getAllListas() {
        // Convierte todas las listas a DTOs y las retorna
        List<ListaDTO> listaDTOs = listaService.findAll().stream()
                .map(ListaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTOs);  // Retorna respuesta 200 OK con las listas
    }

    // Método para obtener una lista por su ID
    @GetMapping("/{id}")  // Mapeo para solicitudes GET a /listas/{id}
    public ResponseEntity<ListaDTO> getListaById(@PathVariable Long id) {
        Lista lista = listaService.findById(id);  // Busca la lista por ID
        return lista != null ? ResponseEntity.ok(new ListaDTO(lista)) : ResponseEntity.notFound().build();  // Retorna la lista o 404 si no existe
    }

    // Método para crear una nueva lista
    @PostMapping  // Mapeo para solicitudes POST a /listas
    public ResponseEntity<ListaDTO> createLista(@RequestBody Lista lista) {
        Lista nuevaLista = listaService.save(lista);  // Guarda la nueva lista
        return ResponseEntity.ok(new ListaDTO(nuevaLista));  // Retorna la nueva lista como DTO
    }

    // Método para eliminar una lista por su ID
    @DeleteMapping("/{id}")  // Mapeo para solicitudes DELETE a /listas/{id}
    public ResponseEntity<Void> deleteLista(@PathVariable Long id) {
        listaService.deleteById(id);  // Elimina la lista por ID
        return ResponseEntity.noContent().build();  // Retorna respuesta 204 No Content
    }

    // Método para agregar videojuegos a una lista existente
    @PostMapping("/{listaId}/videojuegos")  // Mapeo para solicitudes POST a /listas/{listaId}/videojuegos
    public ResponseEntity<?> agregarJuegosALista(@PathVariable Long listaId, @RequestBody List<Long> videojuegosIds) {
        Optional<Lista> listaOpt = listaService.agregarVideojuegosALaLista(listaId, videojuegosIds);  // Intenta agregar los videojuegos a la lista
        return listaOpt
                .map(lista -> ResponseEntity.ok(new ListaDTO(lista)))  // Retorna la lista actualizada si se encontró
                .orElseGet(() -> ResponseEntity.notFound().build());  // Retorna 404 si la lista no existe
    }

    // Método para buscar listas por nombre
    @GetMapping("/search/{nombre}")  // Mapeo para solicitudes GET a /listas/search/{nombre}
    public ResponseEntity<List<ListaDTO>> buscarListasPorNombre(@PathVariable String nombre) {
        // Convierte las listas encontradas a DTOs y las retorna
        List<ListaDTO> listaDTOs = listaService.buscarPorNombre(nombre).stream()
                .map(ListaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listaDTOs);  // Retorna respuesta 200 OK con las listas encontradas
    }

    // Método para buscar listas por ID de usuario
    @GetMapping("/usuario/{usuarioId}")  // Mapeo para solicitudes GET a /listas/usuario/{usuarioId}
    public ResponseEntity<List<ListaDTO>> buscarListasPorUsuario(@PathVariable Long usuarioId){
        // Convierte las listas encontradas a DTOs y las retorna
        List<ListaDTO> listasDTOs = listaService.buscarPorUsuario(usuarioId).stream()
                .map(ListaDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(listasDTOs);  // Retorna respuesta 200 OK con las listas encontradas
    }
}
