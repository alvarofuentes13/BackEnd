package com.example.GameVerse_Back2.services;

import com.example.GameVerse_Back2.models.Lista;
import com.example.GameVerse_Back2.models.Videojuego;
import com.example.GameVerse_Back2.repositories.ListaRepository;
import com.example.GameVerse_Back2.repositories.VideojuegoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ListaService {

    @Autowired
    private ListaRepository listaRepository; // Repositorio para gestionar listas

    @Autowired
    private VideojuegoRepository videojuegoRepository; // Repositorio para gestionar videojuegos

    // Método para obtener todas las listas
    public List<Lista> findAll() {
        return listaRepository.findAll(); // Devuelve todas las listas
    }

    // Método para encontrar una lista por su ID
    public Lista findById(Long id) {
        return listaRepository.findById(id).orElse(null); // Devuelve la lista si existe, o null
    }

    // Método para guardar una nueva lista
    public Lista save(Lista lista) {
        return listaRepository.save(lista); // Guarda la lista en la base de datos
    }

    // Método para eliminar una lista por su ID
    public void deleteById(Long id) {
        listaRepository.deleteById(id); // Elimina la lista de la base de datos
    }

    // Método para agregar videojuegos a una lista existente
    public Optional<Lista> agregarVideojuegosALaLista(Long listaId, List<Long> videojuegosIds) {
        Optional<Lista> listaOpt = listaRepository.findById(listaId); // Busca la lista por ID
        if (listaOpt.isEmpty()) return Optional.empty(); // Devuelve vacío si no se encuentra la lista

        Lista lista = listaOpt.get(); // Obtiene la lista

        // Busca los videojuegos por sus IDs
        List<Videojuego> juegosAAgregar = videojuegoRepository.findAllById(videojuegosIds);

        // Agrega los videojuegos a la lista si no están ya presentes
        for (Videojuego juego : juegosAAgregar) {
            if (!lista.getVideojuegos().contains(juego)) {
                lista.getVideojuegos().add(juego);
            }
        }

        return Optional.of(listaRepository.save(lista)); // Guarda la lista actualizada y devuelve
    }

    // Método para buscar listas por nombre
    public List<Lista> buscarPorNombre(String nombre) {
        return listaRepository.findByNombreContainingIgnoreCase(nombre); // Busca listas que contengan el nombre
    }

    // Método para buscar listas por ID de usuario
    public List<Lista> buscarPorUsuario(Long usuarioId) {
        return listaRepository.findByUsuarioId(usuarioId); // Busca listas asociadas a un usuario específico
    }
}
