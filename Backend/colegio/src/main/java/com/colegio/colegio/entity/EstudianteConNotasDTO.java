package com.colegio.colegio.entity;

import java.math.BigDecimal;

// DTO (Data Transfer Object) que combina datos del estudiante con sus notas
/*
   Reunir en un solo objeto la información del estudiante junto
   con sus notas y promedio.
   Es un objeto de transferencia que combina datos de estudiante y sus notas
   para enviarlos de forma clara y precisa al frontend.
 */
public class EstudianteConNotasDTO {
    private Long id;              // Identificador del estudiante
    private String nombre;        // Nombre del estudiante
    private String apellido;      // Apellido del estudiante
    private String email;         // Correo electrónico del estudiante
    private Double nota1;         // Primera nota
    private Double nota2;         // Segunda nota
    private Double nota3;         // Tercera nota
    private BigDecimal promedio; // ✅ Promedio BigDecimal para precisión

    //Getter y Setter
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

    public Double getNota1() {
        return nota1;
    }

    public void setNota1(Double nota1) {
        this.nota1 = nota1;
    }

    public Double getNota3() {
        return nota3;
    }

    public void setNota3(Double nota3) {
        this.nota3 = nota3;
    }

    public Double getNota2() {
        return nota2;
    }

    public void setNota2(Double nota2) {
        this.nota2 = nota2;
    }

    public BigDecimal getPromedio() {
        return promedio;
    }

    public void setPromedio(BigDecimal promedio) {
        this.promedio = promedio;
    }
}
