package com.colegio.colegio.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

// Clase que representa la tabla "docentes" en la base de datos
@Entity
@Table(name = "docentes")
public class Docente {
    @Id // Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental en la BD
    private Long id;

    // Atributos básicos del docente
    private String nombre;
    private String apellido;
    private String email;
    private String password;

    // Relación muchos a muchos con cursos
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(

            name = "docente_curso", // Tabla intermedia que une docentes y cursos
            joinColumns = @JoinColumn(name = "docente_id"), // Columna que referencia al docente
            inverseJoinColumns = @JoinColumn(name = "curso_id") // Columna que referencia al curso
    )
    @JsonIgnoreProperties({"estudiantes","docentes"}) // Evita ciclos infinitos al serializar
    private List<Curso> cursos;

    // Relación uno a uno con Usuario
    @OneToOne
    @JoinColumn(name = "usuario_id")  // La tabla "docentes" tendrá una columna "usuario_id" como clave foránea
    private Usuario usuario;

    // --- Constructores ---
    // Constructor vacío requerido por JPA
    public Docente() {}


    // Constructor con parámetros para inicializar objetos Docente
    public Docente(String nombre, String apellido, String email, List<Curso> cursos, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.cursos = cursos;
        this.usuario = usuario;
    }

    // Métodos Getters y Setters para acceder y modificar atributos privados

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}
