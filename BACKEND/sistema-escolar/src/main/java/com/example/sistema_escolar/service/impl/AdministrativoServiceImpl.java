package com.example.sistema_escolar.service.impl;

import com.example.sistema_escolar.model.Administrativo;
import com.example.sistema_escolar.repository.AdministrativoRepository;
import com.example.sistema_escolar.service.AdministrativoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdministrativoServiceImpl implements AdministrativoService {

    private final AdministrativoRepository administrativoRepository;

    public AdministrativoServiceImpl(AdministrativoRepository administrativoRepository) {
        this.administrativoRepository = administrativoRepository;
    }

    @Override
    public Administrativo guardarAdministrativo(Administrativo administrativo) {
        return administrativoRepository.save(administrativo);
    }

    @Override
    public List<Administrativo> listarAdministrativos(){
        return administrativoRepository.findAll();
    }

    @Override
    public Administrativo obtenerPorId(Long id){
        return administrativoRepository.findById(id).orElseThrow(() -> new RuntimeException("Persona no encontrada"));
    }

    @Override
    public Administrativo actualizarAdministrativo(Long id, Administrativo administrativoActualizado) {
        Administrativo administrativo = obtenerPorId(id);
        administrativo.setCargo(administrativoActualizado.getCargo());
        administrativo.setDepartamento(administrativoActualizado.getDepartamento());
        return administrativoRepository.save(administrativo);
    }

    @Override
    public void eliminarAdministrativo(Long id) {
        if (!administrativoRepository.existsById(id)) {
            throw new RuntimeException("Administrativo no encontrado para eliminar");
        }
        administrativoRepository.deleteById(id);
    }
}