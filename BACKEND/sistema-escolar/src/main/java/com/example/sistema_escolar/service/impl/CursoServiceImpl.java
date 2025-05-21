package com.example.sistema_escolar.service.impl;

import com.example.sistema_escolar.model.Curso;
import com.example.sistema_escolar.model.Profesor;
import com.example.sistema_escolar.dto.CursoDTO;
import com.example.sistema_escolar.repository.CursoRepository;
import com.example.sistema_escolar.repository.ProfesorRepository;
import com.example.sistema_escolar.service.CursoService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;
    private final ProfesorRepository profesorRepository;

    public CursoServiceImpl(CursoRepository cursoRepository, ProfesorRepository profesorRepository) {
        this.cursoRepository = cursoRepository;
        this.profesorRepository = profesorRepository;
    }

    @Override
    public Curso guardar(CursoDTO dto) {
        try {
            System.out.println("DTO recibido: " + dto);
            Profesor profesor = profesorRepository.findById(dto.getIdProfesor())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con ID: " + dto.getIdProfesor()));

            Curso curso = new Curso();
            curso.setNombre(dto.getNombre());
            curso.setDescripcion(dto.getDescripcion());
            curso.setCreditos(dto.getCreditos());
            curso.setProfesor(profesor);

            return cursoRepository.save(curso);
        } catch (Exception e) {
            e.printStackTrace(); 
            throw e; 
        }
    }

    @Override
    public Page<Curso> listar(Pageable pageable) {
        return cursoRepository.findAll(pageable);
    }

    @Override
    public Curso obtenerPorId(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }

    @Override
    @Transactional
    public Curso actualizar(Long id, CursoDTO dto) {
        Curso curso = obtenerPorId(id);
        Profesor profesor = profesorRepository.findById(dto.getIdProfesor())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        curso.setNombre(dto.getNombre());
        curso.setDescripcion(dto.getDescripcion());
        curso.setCreditos(dto.getCreditos());
        curso.setProfesor(profesor);

        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new RuntimeException("Curso no encontrado para eliminar.");
        }
        cursoRepository.deleteById(id);
    }
}