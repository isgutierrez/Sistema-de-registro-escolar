package com.example.sistema_escolar.service;

import com.example.sistema_escolar.model.Administrativo;
import com.example.sistema_escolar.dto.AdministrativoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface AdministrativoService {
    Administrativo guardarAdministrativo(AdministrativoDTO dto);
    Page<Administrativo> listarAdministrativos(Pageable pageable);
    Administrativo obtenerPorId(Long id);
    Administrativo actualizarAdministrativo(Long id, Administrativo administrativo);
    void eliminarAdministrativo(Long id);
}