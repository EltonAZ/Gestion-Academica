package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Curso;
import com.colegio.colegio.entity.Estudiante;
import com.colegio.colegio.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private CursoRepository repo;

    @GetMapping
    public List<Curso> listar() {
        return repo.findAll();
    }

    @PostMapping
    public Curso registrar(@RequestBody Curso curso) {
        return repo.save(curso);
    }

    //
    @GetMapping("/{cursoId}/estudiantes")
    public List<Estudiante> listarEstudiantesCurso(@PathVariable Long cursoId) {
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        return curso.getEstudiantes();
    }




}
