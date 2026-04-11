package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Curso;
import com.colegio.colegio.entity.Estudiante;
import com.colegio.colegio.entity.EstudianteConNotasDTO;
import com.colegio.colegio.repository.CursoRepository;
import com.colegio.colegio.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/cursos")
@CrossOrigin(origins = "*")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private CursoRepository repo;

    @Autowired
    private NotaRepository notaRepository;

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
    public List<EstudianteConNotasDTO> listarEstudiantesCurso(@PathVariable Long cursoId) {
        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        return curso.getEstudiantes().stream().map(est -> {
            EstudianteConNotasDTO dto = new EstudianteConNotasDTO();
            dto.setId(est.getId());
            dto.setNombre(est.getNombre());
            dto.setApellido(est.getApellido());
            dto.setEmail(est.getEmail());

            // Buscar notas guardadas
            notaRepository.findByCursoIdAndEstudianteId(cursoId, est.getId())
                    .ifPresent(nota -> {
                        dto.setNota1(nota.getNota1());
                        dto.setNota2(nota.getNota2());
                        dto.setNota3(nota.getNota3());

                        // Redondear promedio a 2 decimales con BigDecimal
                        BigDecimal promedio = BigDecimal.valueOf(nota.getPromedio())
                                .setScale(2, RoundingMode.HALF_UP);
                        dto.setPromedio(promedio);
                    });
            return  dto;
        }).collect(java.util.stream.Collectors.toList());
    }




}
