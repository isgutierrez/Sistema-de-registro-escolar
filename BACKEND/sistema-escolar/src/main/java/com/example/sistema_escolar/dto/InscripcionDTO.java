package com.example.sistema_escolar.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class InscripcionDTO {

    @NotNull(message = "El ID del estudiante es obligatorio")
    private Long idEstudiante;

    @NotNull(message = "El ID del curso es obligatorio")
    private Long idCurso;

    @NotNull(message = "La fecha de inscripción es obligatoria")
    private LocalDate fechaInscripcion;

    // Getters y Setters
    public Long getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(Long idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public LocalDate getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(LocalDate fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }
}