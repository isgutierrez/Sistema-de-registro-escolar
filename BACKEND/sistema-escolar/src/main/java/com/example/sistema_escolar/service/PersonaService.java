package com.example.sistema_escolar.service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.sistema_escolar.model.Persona;
import java.util.List;

public interface PersonaService {
    Persona guardarPersona(Persona persona);
    Page<Persona> listarPersonas(Pageable pageable);
    Persona obtenerPorId(Long id);
    Persona actualizarPersona(Long id, Persona persona);
    void eliminarPersona(Long id);
}