package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Curso;
import com.colegio.colegio.entity.Estudiante;
import com.colegio.colegio.entity.LoginRequest;
import com.colegio.colegio.entity.Nota;
import com.colegio.colegio.repository.CursoRepository;
import com.colegio.colegio.repository.EstudianteRepository;
import com.colegio.colegio.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
@CrossOrigin(origins = "*")
public class EstudianteController {

    @Autowired
    private EstudianteRepository estudianteRepo;

    @Autowired
    private CursoRepository cursoRepo;
    @Autowired
    private NotaRepository notaRepository;

    @GetMapping
    public List<Estudiante> listar() {
        return estudianteRepo.findAll();
    }


    @PostMapping
    public Estudiante registrar(@RequestBody Estudiante estudiante) {
        if (estudiante.getCursos() != null) {
            List<Curso> cursos = estudiante.getCursos().stream()
                    .map(c -> cursoRepo.findById(c.getId())
                            .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + c.getId())))
                    .toList();
            estudiante.setCursos(cursos);
        }
        return estudianteRepo.save(estudiante);
    }

    @PutMapping("/{id}")
    public Estudiante actualizar(@PathVariable Long id, @RequestBody Estudiante estudiante) {
        estudiante.setId(id);
        if (estudiante.getCursos() != null) {
            List<Curso> cursos = estudiante.getCursos().stream()
                    .map(c -> cursoRepo.findById(c.getId())
                            .orElseThrow(() -> new RuntimeException("Curso no encontrado: " + c.getId())))
                    .toList();
            estudiante.setCursos(cursos);
        }
        return estudianteRepo.save(estudiante);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        estudianteRepo.deleteById(id);
    }

    // --- Cursos inscritos de un estudiante ---
    @GetMapping("/{estudianteId}/cursos")
    public List<Curso> listarCursosEstudiante(@PathVariable Long estudianteId) {
        Estudiante estudiante = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return estudiante.getCursos();
    }

    // --- Notas de un estudiante en un curso ---
    @GetMapping("/{estudianteId}/cursos/{cursoId}/notas")
    public Nota obtenerNotasEstudiante(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        return notaRepository.findByCursoIdAndEstudianteId(cursoId, estudianteId)
                .orElseThrow(() -> new RuntimeException("Notas no encontradas"));
    }

    //
    @PostMapping("/login")
    public Estudiante login(@RequestBody LoginRequest request) {
        Estudiante estudiante = estudianteRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!estudiante.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        // Devuelve solo id y nombre para seguridad
        Estudiante respuesta = new Estudiante();
        respuesta.setId(estudiante.getId());
        respuesta.setNombre(estudiante.getNombre());
        respuesta.setApellido(estudiante.getApellido());
        respuesta.setEmail(estudiante.getEmail());
        return respuesta;
    }





}
