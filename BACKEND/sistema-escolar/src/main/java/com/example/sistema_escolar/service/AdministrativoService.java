package com.example.sistema_escolar.service;

import com.example.sistema_escolar.model.Administrativo;
import java.util.List;

public interface AdministrativoService {
    Administrativo guardarAdministrativo(Administrativo administrativo);
    List <Administrativo> listarAdministrativos();
    Administrativo obtenerPorId(Long id);
    Administrativo actualizarAdministrativo(Long id, Administrativo administrativo);
    void eliminarAdministrativo(Long id);
}