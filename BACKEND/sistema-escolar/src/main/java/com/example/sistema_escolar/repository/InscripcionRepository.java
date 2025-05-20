package com.example.sistema_escolar.repository;

import com.example.sistema_escolar.model.Inscripcion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InscripcionRepository extends JpaRepository<Inscripcion, Long> {
}