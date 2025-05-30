package com.example.GameVerse_Back2.controllers;

import com.example.GameVerse_Back2.models.Usuario;
import com.example.GameVerse_Back2.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin("*")
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> registerUser(@RequestBody Usuario usuario) {
        Usuario newUser = usuarioService.registerUser(usuario);
        return ResponseEntity.ok(newUser);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(@PathVariable Long id, @RequestBody Usuario usuarioActualizado) {
        Usuario usuarioExistente = usuarioService.findById(id);
        if (usuarioExistente == null) {
            return ResponseEntity.notFound().build();
        }

        // Solo actualiza los campos que quieras permitir cambiar
        usuarioExistente.setAvatar(usuarioActualizado.getAvatar());
        usuarioExistente.setName(usuarioActualizado.getName());
        usuarioExistente.setEmail(usuarioActualizado.getEmail());
        usuarioExistente.setBiografia(usuarioActualizado.getBiografia());

        Usuario actualizado = usuarioService.updateUser(usuarioExistente);
        return ResponseEntity.ok(actualizado);
    }


    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioService.findAll();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Usuario> getUsuarioById(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Optional<Usuario>> getUsuarioByEmail(@PathVariable String email) {
        Optional<Usuario> usuario = usuarioService.getUsuarioByEmail(email);

        return usuario.isPresent() ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

//    @PostMapping("/login")
//    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
//        Usuario usuarioAutenticado = usuarioService.validarCredenciales(usuario.getEmail(), usuario.getPassword());
//
//        if (usuarioAutenticado == null) {
//            return ResponseEntity.status(401).body(null); // No autenticado
//        }
//
//        return ResponseEntity.ok(usuarioAutenticado); // Usuario autenticado
//    }
}
