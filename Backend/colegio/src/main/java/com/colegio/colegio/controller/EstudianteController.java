package com.colegio.colegio.controller;

import com.colegio.colegio.entity.*;
import com.colegio.colegio.repository.CursoRepository;
import com.colegio.colegio.repository.EstudianteRepository;
import com.colegio.colegio.repository.NotaRepository;
import com.colegio.colegio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @Autowired
    private UsuarioRepository usuarioRepo;

    @GetMapping
    public List<Estudiante> listar() {
        return estudianteRepo.findAll();
    }


    @PostMapping
    public Estudiante registrar(@RequestBody Estudiante estudiante) {
        if (estudiante.getCursos() != null) {
            List<Curso> cursos = estudiante.getCursos().stream()
                    .map(c -> cursoRepo.findById(c.getId()).orElse(null))
                    .filter(c -> c != null)
                    .toList();
            estudiante.setCursos(cursos);
        }

        // Crear usuario vinculado
        Usuario usuario = new Usuario();
        usuario.setEmail(estudiante.getEmail());
        usuario.setPassword(estudiante.getPassword());
        usuario.setRol(Rol.ESTUDIANTE);
        usuarioRepo.save(usuario);

        // Vincular usuario al estudiante
        estudiante.setUsuario(usuario);

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
    public EstudianteConNotasDTO obtenerNotasEstudiante(
            @PathVariable Long estudianteId,
            @PathVariable Long cursoId) {
        Nota nota = notaRepository.findByCursoIdAndEstudianteId(cursoId, estudianteId)
                .orElseThrow(() -> new RuntimeException("Notas no encontradas"));

        Estudiante estudiante = nota.getEstudiante();

        EstudianteConNotasDTO dto = new EstudianteConNotasDTO();
        dto.setId(estudiante.getId());
        dto.setNombre(estudiante.getNombre());
        dto.setApellido(estudiante.getApellido());
        dto.setEmail(estudiante.getEmail());
        dto.setNota1(nota.getNota1());
        dto.setNota2(nota.getNota2());
        dto.setNota3(nota.getNota3());
        dto.setPromedio(BigDecimal.valueOf(nota.getPromedio()).setScale(2, RoundingMode.HALF_UP));

        return dto;
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

    @GetMapping("/{id}")
    public Estudiante obtenerEstudiante(@PathVariable Long id) {
        return estudianteRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
    }





}
