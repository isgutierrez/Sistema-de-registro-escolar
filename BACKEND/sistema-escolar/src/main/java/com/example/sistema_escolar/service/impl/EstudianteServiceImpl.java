package com.example.sistema_escolar.service.impl;

import com.example.sistema_escolar.dto.EstudianteDTO;
import com.example.sistema_escolar.model.Estudiante;
import com.example.sistema_escolar.model.Persona;
import com.example.sistema_escolar.repository.EstudianteRepository;
import com.example.sistema_escolar.repository.PersonaRepository;
import com.example.sistema_escolar.service.EstudianteService;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EstudianteServiceImpl implements EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final PersonaRepository personaRepository;

    public EstudianteServiceImpl(
        EstudianteRepository estudianteRepository,
        PersonaRepository personaRepository
    ) {
        this.estudianteRepository = estudianteRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    public Estudiante guardarEstudiante(EstudianteDTO dto){
        Persona persona = new Persona();
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setEmail(dto.getEmail());
        persona.setTelefono(dto.getTelefono());
        persona.setFechaNacimiento(dto.getFechaNacimiento());

        Persona personaGuardada = personaRepository.save(persona);

        Estudiante estudiante = new Estudiante();
        estudiante.setPersona(personaGuardada);
        estudiante.setNumeroMatricula(dto.getNumeroMatricula());
        estudiante.setGrado(dto.getGrado());

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