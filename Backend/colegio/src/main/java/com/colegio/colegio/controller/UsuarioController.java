package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Usuario;
import com.colegio.colegio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*") //Permite la llamada de tu fronted
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public Usuario login(@RequestBody Usuario usuario) {
        Optional<Usuario> encontrado = usuarioRepository.findByEmailAndPassword(
                usuario.getEmail(), usuario.getPassword()
        );
        return encontrado.orElseThrow(() -> new RuntimeException("Credenciales inválidas"));
    }
}
