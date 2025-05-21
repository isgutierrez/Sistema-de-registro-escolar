package com.example.sistema_escolar.repository;

import com.example.sistema_escolar.model.Profesor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProfesorRepository extends JpaRepository<Profesor, Long> {
    Page<Profesor> findAll(Pageable pageable);
}