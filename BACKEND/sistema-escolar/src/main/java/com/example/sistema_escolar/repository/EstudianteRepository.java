package com.example.sistema_escolar.repository;

import com.example.sistema_escolar.model.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
    Page<Estudiante> findAll(Pageable pageable);
}