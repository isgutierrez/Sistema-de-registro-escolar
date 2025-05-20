package com.example.sistema_escolar.service;

import com.example.sistema_escolar.model.Estudiante;
import java.util.List;

public interface EstudianteService {
    Estudiante guardarEstudiante(Estudiante estudiante);
    List <Estudiante> listarEstudiante();
    Estudiante obtenerPorId(Long id);
    Estudiante actualizarEstudiante(Long id, Estudiante estudianteActualizado); 
    void eliminarEstudiante(Long id);
}