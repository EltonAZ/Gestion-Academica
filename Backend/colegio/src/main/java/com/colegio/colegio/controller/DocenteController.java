package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Curso;
import com.colegio.colegio.entity.Docente;
import com.colegio.colegio.repository.CursoRepository;
import com.colegio.colegio.repository.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docentes")
@CrossOrigin(origins = "*")
public class DocenteController {

    @Autowired
    private DocenteRepository docenteRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public List<Docente> listar() {
        return docenteRepository.findAll();
    }

    @PostMapping
    public Docente crear(@RequestBody Docente docente) {
        return docenteRepository.save(docente);
    }

    @PutMapping("/{id}/curso/{cursoId}")
    public Docente asignarCurso(@PathVariable Long id, @PathVariable Long cursoId) {
        Docente docente = docenteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        Curso curso = cursoRepository.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        docente.setCurso(curso);
        return docenteRepository.save(docente);
    }

}
