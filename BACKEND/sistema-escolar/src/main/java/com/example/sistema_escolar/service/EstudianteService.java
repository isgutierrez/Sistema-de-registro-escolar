package com.example.sistema_escolar.service;

import com.example.sistema_escolar.model.Estudiante;
import com.example.sistema_escolar.dto.EstudianteDTO;
import java.util.List;

public interface EstudianteService {
    Estudiante guardarEstudiante(EstudianteDTO dto);
    List <Estudiante> listarEstudiante();
    Estudiante obtenerPorId(Long id);
    Estudiante actualizarEstudiante(Long id, Estudiante estudianteActualizado); 
    void eliminarEstudiante(Long id);
}