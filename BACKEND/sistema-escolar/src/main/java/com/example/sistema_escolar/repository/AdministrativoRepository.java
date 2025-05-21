package com.example.sistema_escolar.repository;

import com.example.sistema_escolar.model.Administrativo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AdministrativoRepository extends JpaRepository<Administrativo, Long> {
    Page<Administrativo> findAll(Pageable pageable);
}