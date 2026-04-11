package com.colegio.colegio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

// Clase que representa la tabla "cursos" en la base de datos
@Entity
@Table(name = "cursos")
public class Curso {
    @Id //Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Atributos básicos del curso
    private String nombre;
    private String descripcion;

    // Relación inversa muchos a muchos con estudiantes
    @ManyToMany(mappedBy = "cursos") // "mappedBy" indica que la relación está definida en la entidad Estudiante
    @JsonIgnoreProperties("cursos") // Evita ciclos infinitos al serializar
    private List<Estudiante> estudiantes;

    // Relación inversa muchos a muchos con docentes
    @ManyToMany(mappedBy = "cursos")
    @JsonIgnoreProperties("cursos")
    private List<Docente> docentes;

    // --- Constructores ---

    // Constructor con ID
    public Curso(Long id) {
        this.id = id;
    }

    // Constructor vacío requerido por JPA
    public Curso() {}

    //
    public Curso(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Getters y Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(List<Estudiante> estudiantes) {
        this.estudiantes = estudiantes;
    }

    public List<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Docente> docentes) {
        this.docentes = docentes;
    }
}
