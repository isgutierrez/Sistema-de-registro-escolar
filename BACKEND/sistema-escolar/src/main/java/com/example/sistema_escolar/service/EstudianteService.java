package com.example.sistema_escolar.service;

import com.example.sistema_escolar.model.Estudiante;
import com.example.sistema_escolar.dto.EstudianteDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EstudianteService {
    Estudiante guardarEstudiante(EstudianteDTO dto);
    Page<Estudiante> listarEstudiantes(Pageable pageable);
    Estudiante obtenerPorId(Long id);
    Estudiante actualizarEstudiante(Long id, Estudiante estudianteActualizado); 
    void eliminarEstudiante(Long id);
}