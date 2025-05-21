package com.example.sistema_escolar.service.impl;

import com.example.sistema_escolar.model.Persona;
import com.example.sistema_escolar.model.Administrativo;
import com.example.sistema_escolar.dto.AdministrativoDTO;
import com.example.sistema_escolar.repository.PersonaRepository;
import com.example.sistema_escolar.repository.AdministrativoRepository;
import com.example.sistema_escolar.service.AdministrativoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrativoServiceImpl implements AdministrativoService {
    
    private final AdministrativoRepository administrativoRepository;
    private final PersonaRepository personaRepository;

    public AdministrativoServiceImpl(
            AdministrativoRepository administrativoRepository,
            PersonaRepository personaRepository) {
        this.administrativoRepository = administrativoRepository;
        this.personaRepository = personaRepository;
    }

    @Override
    public Administrativo guardarAdministrativo(AdministrativoDTO dto) {
        Persona persona = new Persona();
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setEmail(dto.getEmail());
        persona.setTelefono(dto.getTelefono());
        persona.setFechaNacimiento(dto.getFechaNacimiento());

        Persona personaGuardada = personaRepository.save(persona); 

        Administrativo administrativo = new Administrativo();
        administrativo.setPersona(personaGuardada); 
        administrativo.setCargo(dto.getCargo());
        administrativo.setDepartamento(dto.getDepartamento());

        return administrativoRepository.save(administrativo);
    }

    @Override
    public Page<Administrativo> listarAdministrativos(Pageable pageable) {
        return administrativoRepository.findAll(pageable);
    }

    @Override
    public Administrativo obtenerPorId(Long id) {
        return administrativoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Administrativo no encontrado"));
    }

    @Override
    @Transactional
    public Administrativo actualizarAdministrativo(Long id, Administrativo actualizado) {
        Administrativo existente = obtenerPorId(id);

        // Actualizar datos de persona
        Persona personaExistente = existente.getPersona();
        Persona personaActualizada = actualizado.getPersona();

        personaExistente.setNombre(personaActualizada.getNombre());
        personaExistente.setApellido(personaActualizada.getApellido());
        personaExistente.setEmail(personaActualizada.getEmail());
        personaExistente.setTelefono(personaActualizada.getTelefono());
        personaExistente.setFechaNacimiento(personaActualizada.getFechaNacimiento());

        personaRepository.save(personaExistente);

        // Actualizar datos de administrativo
        existente.setCargo(actualizado.getCargo());
        existente.setDepartamento(actualizado.getDepartamento());

        return administrativoRepository.save(existente);
    }

    @Override
    public void eliminarAdministrativo(Long id) {
        administrativoRepository.deleteById(id);
    }
}