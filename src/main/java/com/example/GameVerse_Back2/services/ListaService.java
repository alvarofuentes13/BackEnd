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
    private ListaRepository listaRepository;

    @Autowired
    private VideojuegoRepository videojuegoRepository;



    public List<Lista> findAll() {
        return listaRepository.findAll();
    }

    public Lista findById(Long id) {
        return listaRepository.findById(id).orElse(null);
    }

    public Lista save(Lista lista) {
        return listaRepository.save(lista);
    }

    public void deleteById(Long id) {
        listaRepository.deleteById(id);
    }


    public Optional<Lista> agregarVideojuegosALaLista(Long listaId, List<Long> videojuegosIds) {
        Optional<Lista> listaOpt = listaRepository.findById(listaId);
        if (listaOpt.isEmpty()) return Optional.empty();

        Lista lista = listaOpt.get();

        List<Videojuego> juegosAAgregar = videojuegoRepository.findAllById(videojuegosIds);

        for (Videojuego juego : juegosAAgregar) {
            if (!lista.getVideojuegos().contains(juego)) {
                lista.getVideojuegos().add(juego);
            }
        }

        return Optional.of(listaRepository.save(lista));
    }

    public List<Lista> buscarPorNombre(String nombre) {
        return listaRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
