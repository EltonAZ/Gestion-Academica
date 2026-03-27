package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Usuario;
import com.colegio.colegio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")//Permite la llamada de tu fronted
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepo;

    // Registrar usuario
    @PostMapping("/registro")
    public Usuario registrar(@RequestBody Usuario usuario) {
        // Aquí deberías encriptar la contraseña con BCrypt
        return usuarioRepo.save(usuario);
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> userOpt = usuarioRepo.findByEmail(usuario.getEmail());
        if (userOpt.isPresent()) {
            Usuario user = userOpt.get();
            if (user.getPassword().equals(usuario.getPassword())) {
                return ResponseEntity.ok(user);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

}
