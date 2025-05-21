package com.example.sistema_escolar.service;

import com.example.sistema_escolar.model.Curso;
import com.example.sistema_escolar.dto.CursoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CursoService {
    Curso guardar(CursoDTO dto);  
    Page<Curso> listar(Pageable pageable);
    Curso obtenerPorId(Long id);
    Curso actualizar(Long id, CursoDTO dto);
    void eliminar(Long id);
}