package com.example.sistema_escolar.service.impl;

import com.example.sistema_escolar.model.Persona;
import com.example.sistema_escolar.repository.PersonaRepository;
import com.example.sistema_escolar.service.PersonaService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    private final PersonaRepository personaRepository;

    public PersonaServiceImpl(PersonaRepository personaRepository) {
        this.personaRepository = personaRepository;
    }

    @Override
    public Persona guardarPersona(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public Page<Persona> listarPersonas(Pageable pageable) {
        return personaRepository.findAll(pageable);
    }

    @Override
    public Persona obtenerPorId(Long id) {
        return personaRepository.findById(id).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
    }

    @Override
    public Persona actualizarPersona(Long id, Persona personaActualizada) {
        Persona persona = obtenerPorId(id);
        persona.setNombre(personaActualizada.getNombre());
        persona.setApellido(personaActualizada.getApellido());
        persona.setEmail(personaActualizada.getEmail());
        persona.setTelefono(personaActualizada.getTelefono());
        persona.setFechaNacimiento(personaActualizada.getFechaNacimiento());
        return personaRepository.save(persona);
    }

    @Override
    public void eliminarPersona(Long id) {
        if(!personaRepository.existsById(id)) {
            throw new RuntimeException("Persona no encontrada para eliminar");
        }
        personaRepository.deleteById(id);
    }
}