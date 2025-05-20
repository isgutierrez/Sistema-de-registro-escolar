package com.example.sistema_escolar.repository;

import com.example.sistema_escolar.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    
}