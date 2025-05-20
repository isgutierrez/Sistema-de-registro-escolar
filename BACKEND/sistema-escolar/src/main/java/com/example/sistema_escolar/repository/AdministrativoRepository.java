package com.example.sistema_escolar.repository;

import com.example.sistema_escolar.model.Administrativo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministrativoRepository extends JpaRepository<Administrativo, Long> {
    
}