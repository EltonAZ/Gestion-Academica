package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Curso;
import com.colegio.colegio.entity.Docente;
import com.colegio.colegio.repository.CursoRepository;
import com.colegio.colegio.repository.DocenteRepository;
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
        return docenteRepo.save(docente);
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




}
