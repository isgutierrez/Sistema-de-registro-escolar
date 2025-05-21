package com.example.sistema_escolar.service.impl;

import com.example.sistema_escolar.dto.ProfesorDTO;
import com.example.sistema_escolar.model.Persona;
import com.example.sistema_escolar.model.Profesor;
import com.example.sistema_escolar.repository.PersonaRepository;
import com.example.sistema_escolar.repository.ProfesorRepository;
import com.example.sistema_escolar.service.ProfesorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfesorServiceImpl implements ProfesorService {

    private final ProfesorRepository profesorRepository;
    private final PersonaRepository personaRepository;

    @Autowired
    public ProfesorServiceImpl(ProfesorRepository profesorRepository, PersonaRepository personaRepository) {
        this.profesorRepository = profesorRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    public Profesor guardarProfesor(ProfesorDTO dto) {
        Persona persona = new Persona();
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setEmail(dto.getEmail());
        persona.setTelefono(dto.getTelefono());
        persona.setFechaNacimiento(dto.getFechaNacimiento());

        Persona personaGuardada = personaRepository.save(persona);

        Profesor profesor = new Profesor();
        profesor.setPersona(personaGuardada);
        profesor.setEspecialidad(dto.getEspecialidad());
        profesor.setFechaContratacion(dto.getFechaContratacion());

        return profesorRepository.save(profesor);
    }

    @Override
    public Page<Profesor> listarProfesores(Pageable pageable) {
        return profesorRepository.findAll(pageable);
    }

    @Override
    public Profesor obtenerPorId(Long id) {
        return profesorRepository.findById(id).orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
    }

    @Override
    public Profesor actualizarProfesor(Long id, Profesor profesorActualizado) {
        Profesor profesor = obtenerPorId(id);
        profesor.setEspecialidad(profesorActualizado.getEspecialidad());
        return profesorRepository.save(profesor);
    }

    @Override
    public void eliminarProfesor(Long id) {
        profesorRepository.deleteById(id);
    }
}