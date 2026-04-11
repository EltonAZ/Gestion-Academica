package com.colegio.colegio.entity;

/*
    Un DTO es una clase auxiliar que sirve para transportar datos específicos
    entre el backend y el frontend, evitando exponer directamente las entidades
    y enviando solo lo que realmente se necesita.
 */
public class NotaResponseDTO {
    private Long estudianteId;
    private String estudianteNombre;
    private String cursoNombre;
    private Double nota1;
    private Double nota2;
    private Double nota3;
    private Double promedio;

    // getters y setters

    public Long getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Long estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getEstudianteNombre() {
        return estudianteNombre;
    }

    public void setEstudianteNombre(String estudianteNombre) {
        this.estudianteNombre = estudianteNombre;
    }

    public String getCursoNombre() {
        return cursoNombre;
    }

    public void setCursoNombre(String cursoNombre) {
        this.cursoNombre = cursoNombre;
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
