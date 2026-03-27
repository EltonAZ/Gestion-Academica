package com.colegio.colegio.repository;

import com.colegio.colegio.entity.Nota;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotaRepository extends JpaRepository<Nota, Long> {

    List<Nota> findByCursoId(Long cursoId);
    Optional<Nota> findByEstudianteIdAndCursoId(Long estudianteId, Long cursoId);

    Optional<Nota> findByCursoIdAndEstudianteId(Long cursoId, Long estudianteId);
}
