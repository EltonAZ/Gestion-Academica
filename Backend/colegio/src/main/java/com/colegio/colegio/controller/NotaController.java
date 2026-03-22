package com.colegio.colegio.controller;

import com.colegio.colegio.entity.Nota;
import com.colegio.colegio.repository.NotaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notas")
@CrossOrigin(origins = "*")
public class NotaController {
    private final NotaRepository notaRepository;

    public NotaController(NotaRepository notaRepository) {
        this.notaRepository = notaRepository;
    }

    @GetMapping
    public List<Nota> listar() {
        return notaRepository.findAll();
    }

    @PostMapping
    public Nota crear(@RequestBody Nota nota) {
        return notaRepository.save(nota);
    }

    @PutMapping("/{id}")
    public Nota actualizar(@PathVariable Long id, @RequestBody Nota notaActualizada) {
        return notaRepository.findById(id)
                .map(nota -> {
                    nota.setValor(notaActualizada.getValor());
                    nota.setEstudiante(notaActualizada.getEstudiante());
                    nota.setCurso(notaActualizada.getCurso());
                    return notaRepository.save(nota);
                })
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));
    }

    @DeleteMapping("/{id}")
    public String eliminar(@PathVariable Long id) {
        notaRepository.deleteById(id);
        return "Nota eliminada con éxito";
    }
}
