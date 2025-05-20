package com.example.sistema_escolar.repository;

import com.example.sistema_escolar.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    
}