package com.example.sistema_escolar.repository;

import com.example.sistema_escolar.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    boolean existsByEmail(String email);
}