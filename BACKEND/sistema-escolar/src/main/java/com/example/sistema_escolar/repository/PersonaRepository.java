package com.example.sistema_escolar.repository;

import com.example.sistema_escolar.model.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    boolean existsByEmail(String email);
    Page<Persona> findAll(Pageable pageable);
}