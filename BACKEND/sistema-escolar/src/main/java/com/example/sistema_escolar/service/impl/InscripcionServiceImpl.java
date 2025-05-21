package com.example.sistema_escolar.service.impl;

import com.example.sistema_escolar.model.Inscripcion;
import com.example.sistema_escolar.model.Estudiante;
import com.example.sistema_escolar.model.Curso;
import com.example.sistema_escolar.dto.InscripcionDTO;
import com.example.sistema_escolar.repository.InscripcionRepository;
import com.example.sistema_escolar.repository.EstudianteRepository;
import com.example.sistema_escolar.repository.CursoRepository;
import com.example.sistema_escolar.service.InscripcionService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class InscripcionServiceImpl implements InscripcionService {
    
private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    public InscripcionServiceImpl(InscripcionRepository inscripcionRepository,
                                   EstudianteRepository estudianteRepository,
                                   CursoRepository cursoRepository) {
        this.inscripcionRepository = inscripcionRepository;
        this.estudianteRepository = estudianteRepository;
        this.cursoRepository = cursoRepository;
    }

    @Override
    public Inscripcion guardar(InscripcionDTO dto) {
        Estudiante estudiante = estudianteRepository.findById(dto.getIdEstudiante())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Curso curso = cursoRepository.findById(dto.getIdCurso())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());

        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public Page<Inscripcion> listarInscripcion(Pageable pageable) {
        return inscripcionRepository.findAll(pageable);
    }

    @Override
    public Inscripcion obtenerPorId(Long id) {
        return inscripcionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inscripción no encontrada"));
    }

    @Override
    @Transactional
    public Inscripcion actualizarInscripcion(Long id, InscripcionDTO dto) {
        Inscripcion inscripcion = obtenerPorId(id);

        Estudiante estudiante = estudianteRepository.findById(dto.getIdEstudiante())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Curso curso = cursoRepository.findById(dto.getIdCurso())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        inscripcion.setEstudiante(estudiante);
        inscripcion.setCurso(curso);
        inscripcion.setFechaInscripcion(dto.getFechaInscripcion());

        return inscripcionRepository.save(inscripcion);
    }

    @Override
    public void eliminarInscripcion(Long id) {
        if (!inscripcionRepository.existsById(id)) {
            throw new RuntimeException("Inscripción no encontrada");
        }
        inscripcionRepository.deleteById(id);
    }
}