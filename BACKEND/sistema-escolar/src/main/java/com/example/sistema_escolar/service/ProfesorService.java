package com.example.sistema_escolar.service;

import com.example.sistema_escolar.dto.ProfesorDTO;
import com.example.sistema_escolar.model.Profesor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProfesorService {
    Profesor guardarProfesor(ProfesorDTO dto);
    Page<Profesor> listarProfesores(Pageable pageable);
    Profesor obtenerPorId(Long id);
    Profesor actualizarProfesor(Long id, Profesor profesor);
    void eliminarProfesor(Long id);
}