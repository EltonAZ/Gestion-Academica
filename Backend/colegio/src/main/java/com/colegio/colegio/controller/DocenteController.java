package com.colegio.colegio.controller;

import com.colegio.colegio.entity.*;
import com.colegio.colegio.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/docentes")
@CrossOrigin(origins = "*")
public class DocenteController {

    @Autowired
    private DocenteRepository docenteRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private UsuarioRepository usuarioRepo; // <-- Agregamos el repositorio de Usuario

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private NotaRepository notaRepo;

    @GetMapping
    public List<Docente> listar() {
        return docenteRepo.findAll();
    }

    @PostMapping
    public Docente registrar(@RequestBody Docente docente) {
        if (docente.getCursos() != null) {
            List<Curso> cursos = docente.getCursos().stream()
                    .map(c -> cursoRepo.findById(c.getId())
                            .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + c.getId())))
                    .toList();
            docente.setCursos(cursos);
        }

        // Crear usuario vinculado
        Usuario usuario = new Usuario();
        usuario.setEmail(docente.getEmail());
        usuario.setPassword(docente.getPassword());
        usuario.setRol(Rol.DOCENTE);
        usuarioRepo.save(usuario);

        // Vincular usuario al docente
        docente.setUsuario(usuario);

        // Guardar docente
        return docenteRepo.save(docente);
    }

    @PostMapping("/login")
    public Docente login(@RequestBody LoginRequest request) {
        Docente docente = docenteRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!docente.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        Docente respuesta = new Docente();
        respuesta.setId(docente.getId());
        respuesta.setNombre(docente.getNombre());
        respuesta.setApellido(docente.getApellido());
        respuesta.setEmail(docente.getEmail());
        return respuesta;
    }

    @PutMapping("/{id}")
    public Docente actualizar(@PathVariable Long id, @RequestBody Docente docente) {
        docente.setId(id);
        if (docente.getCursos() != null) {
            List<Curso> cursos = docente.getCursos().stream()
                    .map(c -> cursoRepo.findById(c.getId())
                            .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + c.getId())))
                    .toList();
            docente.setCursos(cursos);
        }
        return docenteRepo.save(docente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        docenteRepo.deleteById(id);
    }


    //
    @GetMapping("/{docenteId}/cursos")
    public List<Curso> listarCursosDocente(@PathVariable Long docenteId) {
        Docente docente = docenteRepo.findById(docenteId)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        return docente.getCursos() != null ? docente.getCursos() : List.of();
    }

    //Obtener docente por Id. Para Subtitulo de Panel Docente
    @GetMapping("/{id}")
    public Docente obtenerDocente(@PathVariable Long id) {
        return docenteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
    }






}
