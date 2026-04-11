package com.colegio.colegio.repository;

import com.colegio.colegio.entity.Docente;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface DocenteRepository extends JpaRepository<Docente, Long> {

    Optional<Docente> findByEmail(String email);
}


