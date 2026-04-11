package com.colegio.colegio.entity;

import com.colegio.colegio.repository.EstudianteRepository;
import jakarta.persistence.*;

// Clase que representa la tabla "notas" en la base de datos
@Entity
@Table(name = "notas")
public class Nota {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Relaciones con Estudiante y Curso
    @ManyToOne //Muchos a uno: Muchas notas puedes pertenecer a un Estudiante
    @JoinColumn(name = "estudiante_id", nullable = false) // Columna foránea en la tabla notas que apunta al estudiante
    private Estudiante estudiante;

    @ManyToOne // Muchos registros de Nota pueden pertenecer a un mismo Curso
    @JoinColumn(name = "curso_id", nullable = false) //Columna foranea en notas que apunta al curso
    private Curso curso;

    // Campos para almacenar las notas individuales y el promedio
    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double promedio;

    // --- Método para calcular promedio ---
    public void calcularPromedio() {
        // Si las tres notas no son nulas, se calcula el promedio
        if (nota1 != null && nota2 != null && nota3 != null) {
            this.promedio = (nota1 + nota2 + nota3) / 3.0;
        }
    }

    // --- Getters y Setters ---

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Double getNota1() {
        return nota1;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
    }

    public Double getNota2() {
        return nota2;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
    }

    public Double getNota3() {
        return nota3;
    }

    public void setNota3(Double nota3) {
        this.nota3 = nota3;
    }

    public Double getPromedio() {
        return promedio;
    }

    public void setPromedio(Double promedio) {
        this.promedio = promedio;
    }
}
