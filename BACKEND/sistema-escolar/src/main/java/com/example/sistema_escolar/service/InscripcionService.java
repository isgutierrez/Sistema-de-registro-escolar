package com.example.sistema_escolar.service;

import com.example.sistema_escolar.model.Inscripcion;
import java.util.List;

public interface InscripcionService {
    Inscripcion guardarInscripcion(Inscripcion inscripcion);
    List<Inscripcion> listarInscripcion();
    Inscripcion obtenerPorId(Long id);
    Inscripcion actualizarInscripcion(Long id, Inscripcion inscripcion);
    void eliminarInscripcion(Long id);
}