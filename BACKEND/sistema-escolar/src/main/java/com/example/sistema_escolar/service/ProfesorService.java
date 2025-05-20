package com.example.sistema_escolar.service;

import com.example.sistema_escolar.dto.ProfesorDTO;
import com.example.sistema_escolar.model.Profesor;
import java.util.List;

public interface ProfesorService {
    Profesor guardarProfesor(ProfesorDTO dto);
    List <Profesor> listarProfesores();
    Profesor obtenerPorId(Long id);
    Profesor actualizarProfesor(Long id, Profesor profesor);
    void eliminarProfesor(Long id);
}