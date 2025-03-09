package com.example.GameVerse_Back2.services;

import com.example.GameVerse_Back2.models.Lista;
import com.example.GameVerse_Back2.repositories.ListaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListaService {

    @Autowired
    private ListaRepository listaRepository;

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
}
