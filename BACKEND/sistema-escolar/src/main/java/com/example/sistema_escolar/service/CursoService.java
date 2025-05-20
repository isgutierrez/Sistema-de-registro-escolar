package com.example.sistema_escolar.service;

import com.example.sistema_escolar.model.Curso;
import java.util.List;

public interface CursoService {
    Curso guardar(Curso curso);
    List<Curso> listar();
    Curso obtenerPorId(Long id);
    Curso actualizar(Long id, Curso curso);
    void eliminar(Long id);
}