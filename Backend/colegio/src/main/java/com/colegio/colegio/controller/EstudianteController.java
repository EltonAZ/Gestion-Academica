package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Estudiante;
import com.colegio.colegio.repository.EstudianteRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteRepository repo;

    //Constructor para inyextar el repositorio
    public EstudianteController(EstudianteRepository repo) {
        this.repo = repo;
    }

    // Endpoint GET: Listar todos los estudiantes
    @GetMapping
    public List<Estudiante> listar() {
        return repo.findAll();
    }

    // Endpoint POST: Crear un nuevo estudiante
    @PostMapping
    public Estudiante crear(@RequestBody Estudiante estudiante) {
        return repo.save(estudiante);
    }
}
