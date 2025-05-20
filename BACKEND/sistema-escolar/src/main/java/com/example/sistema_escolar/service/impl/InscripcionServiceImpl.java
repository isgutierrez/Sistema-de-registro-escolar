package com.example.sistema_escolar.service.impl;

import com.example.sistema_escolar.model.Inscripcion;
import com.example.sistema_escolar.repository.InscripcionRepository;
import com.example.sistema_escolar.service.InscripcionService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionServiceImpl implements InscripcionService {
    
    private final InscripcionRepository inscripcionRepository;

    public InscripcionServiceImpl(InscripcionRepository inscripcionRepository) {
        this.inscripcionRepository = inscripcionRepository;
    }

    @Override
    public Inscripcion guardarInscripcion(Inscripcion inscripcion) {
        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public List<Inscripcion> listarInscripcion() {
        return inscripcionRepository.findAll();
    }

    @Override
    public Inscripcion obtenerPorId(Long id) {
        return inscripcionRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));
    }

    @Override
    public Inscripcion actualizarInscripcion(Long id, Inscripcion inscripcionActualizada) {
        Inscripcion inscripcion = obtenerPorId(id);
        inscripcion.setEstudiante(inscripcionActualizada.getEstudiante());
        inscripcion.setCurso(inscripcionActualizada.getCurso());
        inscripcion.setFechaInscripcion(inscripcionActualizada.getFechaInscripcion());
        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public void eliminarInscripcion(Long id) {
        if(!inscripcionRepository.existsById(id)){
            throw new RuntimeException("Inscripcion no encontrada para eliminar");
        }
        inscripcionRepository.deleteById(id);
    }

}