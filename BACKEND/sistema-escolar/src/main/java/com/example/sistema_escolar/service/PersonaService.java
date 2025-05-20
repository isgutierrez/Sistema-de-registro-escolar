package com.example.sistema_escolar.service;

import com.example.sistema_escolar.model.Persona;
import java.util.List;

public interface PersonaService {
    Persona guardarPersona(Persona persona);
    List<Persona> obtenerPersonas();
    Persona obtenerPorId(Long id);
    Persona actualizarPersona(Long id, Persona persona);
    void eliminarPersona(Long id);
}