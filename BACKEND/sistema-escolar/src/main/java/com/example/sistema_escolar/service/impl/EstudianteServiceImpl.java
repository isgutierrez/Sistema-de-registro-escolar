package com.example.sistema_escolar.service.impl;

import com.example.sistema_escolar.model.Estudiante;
import com.example.sistema_escolar.repository.EstudianteRepository;
import com.example.sistema_escolar.service.EstudianteService;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;

    public EstudianteServiceImpl(EstudianteRepository estudianteRepository){
        this.estudianteRepository = estudianteRepository;
    }

    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante){
        return estudianteRepository.save(estudiante);
    }

    @Override
    public List<Estudiante> listarEstudiante(){
        return estudianteRepository.findAll();
    }

    @Override
    public Estudiante obtenerPorId(Long id){
        return estudianteRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Estudiante no encontrado"));
    }

    @Override
    public Estudiante actualizarEstudiante(Long id, Estudiante estudianteActualizado) {
        Estudiante estudiante = obtenerPorId(id);
        estudiante.setNumeroMatricula(estudianteActualizado.getNumeroMatricula());
        estudiante.setGrado(estudianteActualizado.getGrado());
        return estudianteRepository.save(estudiante);
    }

    @Override
    public void eliminarEstudiante(Long id) {
        if(!estudianteRepository.existsById(id)){
            throw new RuntimeException("Estudiante no encontrado para eliminación.");
        }
        estudianteRepository.deleteById(id);
    }
}