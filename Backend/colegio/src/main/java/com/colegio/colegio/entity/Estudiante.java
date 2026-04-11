package com.colegio.colegio.entity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.List;

// CLASE QUE REPRESENTA LA tabla "estudiantes" en la BD
@Entity
@Table(name = "estudiantes")
public class Estudiante {
    @Id //Clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Autoincremental
    private long id;

    // Atributos básicos del estudiante
    private String nombre;
    private String apellido;
    private String email;
    private String password;

    // Relación uno a uno con Usuario (cada estudiante tiene un usuario asociado)
    @OneToOne
    @JoinColumn(name = "usuario_id") // Columna foránea en la tabla estudiantes
    private Usuario usuario;

    // Relación muchos a muchos con cursos
    @ManyToMany(fetch = FetchType.EAGER) // Se cargan los cursos junto con el estudiante
    @JoinTable(
            name = "estudiante_curso", // Nombre Tabla intermedia
            joinColumns = @JoinColumn(name = "estudiante_id"), // FK hacia estudiante
            inverseJoinColumns = @JoinColumn(name = "curso_id") // FK hacia curso
    )

    // Evita ciclos infinitos al serializar
    @JsonIgnoreProperties({"estudiantes","docentes"})
    private List<Curso> cursos;

    // Constructor vacío requerido por JPA
    public Estudiante() {}

    // Constructor con parámetros para inicializar objetos Estudiante
    public Estudiante(String nombre, String apellido, String email, List<Curso> cursos, Usuario usuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.cursos = cursos;
        this.usuario = usuario;
    }

    // Métodos Getters y Setters para acceder y modificar atributos
    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public List<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(List<Curso> cursos) {
        this.cursos = cursos;
    }
}
