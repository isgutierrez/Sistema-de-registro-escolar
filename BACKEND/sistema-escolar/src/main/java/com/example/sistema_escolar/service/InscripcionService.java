package com.example.sistema_escolar.service;

import com.example.sistema_escolar.model.Inscripcion;
import com.example.sistema_escolar.dto.InscripcionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface InscripcionService {
    Inscripcion guardar(InscripcionDTO dto);
    Page<Inscripcion> listarInscripcion(Pageable pageable);
    Inscripcion obtenerPorId(Long id);
    Inscripcion actualizarInscripcion(Long id, InscripcionDTO dto);
    void eliminarInscripcion(Long id);
}