package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Curso;
import com.colegio.colegio.entity.Estudiante;
import com.colegio.colegio.entity.Nota;
import com.colegio.colegio.entity.NotaResponseDTO;
import com.colegio.colegio.repository.CursoRepository;
import com.colegio.colegio.repository.EstudianteRepository;
import com.colegio.colegio.repository.NotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@RestController
@RequestMapping("/notas")
@CrossOrigin(origins = "*")
public class NotaController {
    @Autowired
    private NotaRepository notaRepo;

    @Autowired
    private CursoRepository cursoRepo;

    @Autowired
    private EstudianteRepository estudianteRepo;


    // Registrar o actualizar notas de un estudiante
    @PostMapping("/cursos/{cursoId}/estudiantes/{estudianteId}")
    public NotaResponseDTO registrarNota(@PathVariable Long cursoId,
                              @PathVariable Long estudianteId,
                              @RequestBody Nota nota) {

        Curso curso = cursoRepo.findById(cursoId)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Estudiante estudiante = estudianteRepo.findById(estudianteId)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Nota notaExistente = notaRepo.findByEstudianteIdAndCursoId(estudianteId, cursoId)
                .orElse(new Nota());

        notaExistente.setCurso(curso);
        notaExistente.setEstudiante(estudiante);
        notaExistente.setNota1(nota.getNota1());
        notaExistente.setNota2(nota.getNota2());
        notaExistente.setNota3(nota.getNota3());

        double promedio = (nota.getNota1() + nota.getNota2() + nota.getNota3()) / 3.0;
        BigDecimal promedioRedondeado = BigDecimal.valueOf(promedio)
                .setScale(2, RoundingMode.HALF_UP);
        notaExistente.setPromedio(promedioRedondeado.doubleValue());

        notaRepo.save(notaExistente);

        // Construir DTO con nombres y datos
        NotaResponseDTO dto = new NotaResponseDTO();
        dto.setEstudianteId(estudiante.getId());
        dto.setEstudianteNombre(estudiante.getNombre() + " " + estudiante.getApellido());
        dto.setCursoNombre(curso.getNombre());
        dto.setNota1(notaExistente.getNota1());
        dto.setNota2(notaExistente.getNota2());
        dto.setNota3(notaExistente.getNota3());
        dto.setPromedio(notaExistente.getPromedio());

        return dto;
    }

    // Listar todas las notas de un curso
    @GetMapping("/cursos/{cursoId}")
    public List<Nota> listarNotasPorCurso(@PathVariable Long cursoId) {
        return notaRepo.findByCursoId(cursoId);
    }

    // Obtener notas de un estudiante en un curso
    @GetMapping("/cursos/{cursoId}/estudiantes/{estudianteId}")
    public Nota obtenerNotasEstudiante(@PathVariable Long cursoId,
                                       @PathVariable Long estudianteId) {
        return notaRepo.findByCursoIdAndEstudianteId(cursoId, estudianteId)
                .orElseThrow(() -> new RuntimeException("Notas no encontradas"));
    }


}
