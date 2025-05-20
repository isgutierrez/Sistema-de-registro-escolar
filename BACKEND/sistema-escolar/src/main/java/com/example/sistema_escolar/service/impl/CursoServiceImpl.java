package com.example.sistema_escolar.service.impl;

import com.example.sistema_escolar.model.Curso;
import com.example.sistema_escolar.repository.CursoRepository;
import com.example.sistema_escolar.service.CursoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService {

    private final CursoRepository cursoRepository;

    public CursoServiceImpl(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    @Override
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    public Curso obtenerPorId(Long id) {
        return cursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
    }

    @Override
    public Curso actualizar(Long id, Curso actualizado) {
        Curso curso = obtenerPorId(id);
        curso.setNombre(actualizado.getNombre());
        curso.setDescripcion(actualizado.getDescripcion());
        curso.setCreditos(actualizado.getCreditos());
        curso.setProfesor(actualizado.getProfesor());
        return cursoRepository.save(curso);
    }

    @Override
    public void eliminar(Long id) {
        if(!cursoRepository.existsById(id)){
            throw new RuntimeException("Curso no encontrado para eliminar.");
        }
        cursoRepository.deleteById(id);
    }
}