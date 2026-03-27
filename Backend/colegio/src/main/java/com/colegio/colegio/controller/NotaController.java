package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Curso;
import com.colegio.colegio.entity.Estudiante;
import com.colegio.colegio.entity.Nota;
import com.colegio.colegio.repository.CursoRepository;
import com.colegio.colegio.repository.EstudianteRepository;
import com.colegio.colegio.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/docentes")
@CrossOrigin(origins = "*")
public class NotaController {
    @Autowired
    private NotaRepository notaRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private EstudianteRepository estudianteRepo;


    // Registrar o actualizar notas de un estudiante
    @PostMapping("/{docenteId}/cursos/{cursoId}/notas/{estudianteId}")
    public Nota registrarNota(@PathVariable Long docenteId,
                              @PathVariable Long cursoId,
                              @PathVariable Long estudianteId,
                              @RequestBody Nota nota) {

        // Validar curso
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        // Validar que el docente dicta este curso (ManyToMany)
        boolean dictaCurso = curso.getDocentes()
                .stream()
                .anyMatch(d -> d.getId().equals(docenteId));

        if (!dictaCurso) {
            throw new RuntimeException("El docente no dicta este curso");
        }

        // Validar estudiante
        Estudiante estudiante = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Buscar si ya existe nota registrada
        Nota notaExistente = notaRepo.findByEstudianteIdAndCursoId(estudianteId, cursoId)
                .orElse(new Nota());

        notaExistente.setCurso(curso);
        notaExistente.setEstudiante(estudiante);
        notaExistente.setNota1(nota.getNota1());
        notaExistente.setNota2(nota.getNota2());
        notaExistente.setNota3(nota.getNota3());
        notaExistente.calcularPromedio();

        return notaRepo.save(notaExistente);
    }


    // Listar notas de un curso
    @GetMapping("/{docenteId}/cursos/{cursoId}/notas")
    public List<Nota> listarNotas(@PathVariable Long docenteId,
                                  @PathVariable Long cursoId) {

        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        boolean dictaCurso = curso.getDocentes()
                .stream()
                .anyMatch(d -> d.getId().equals(docenteId));

        if (!dictaCurso) {
            throw new RuntimeException("El docente no dicta este curso");
        }

        return notaRepo.findByCursoId(cursoId);
    }


}
