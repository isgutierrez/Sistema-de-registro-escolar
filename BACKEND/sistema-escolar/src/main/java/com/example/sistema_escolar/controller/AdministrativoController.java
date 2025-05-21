package com.example.sistema_escolar.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import com.example.sistema_escolar.model.Persona;
import com.example.sistema_escolar.model.Administrativo;
import com.example.sistema_escolar.service.AdministrativoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sistema_escolar.dto.AdministrativoDTO;

import java.util.List;

@RestController
@RequestMapping("/api/administrativos")
public class AdministrativoController {

    private final AdministrativoService administrativoService;

    public AdministrativoController(AdministrativoService administrativoService) {
        this.administrativoService = administrativoService;
    }

    @PostMapping
    public ResponseEntity<Administrativo> crear(@RequestBody AdministrativoDTO dto) {
        return ResponseEntity.ok(administrativoService.guardarAdministrativo(dto));
    }

    @GetMapping
    public ResponseEntity<Page<Administrativo>> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(administrativoService.listarAdministrativos(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrativo> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(administrativoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrativo> actualizar(@PathVariable Long id, @RequestBody AdministrativoDTO dto) {
        Administrativo administrativo = new Administrativo();
        Persona persona = new Persona();
        persona.setNombre(dto.getNombre());
        persona.setApellido(dto.getApellido());
        persona.setEmail(dto.getEmail());
        persona.setTelefono(dto.getTelefono());
        persona.setFechaNacimiento(dto.getFechaNacimiento());
        administrativo.setPersona(persona);
        administrativo.setCargo(dto.getCargo());
        administrativo.setDepartamento(dto.getDepartamento());

        Administrativo actualizado = administrativoService.actualizarAdministrativo(id, administrativo);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        administrativoService.eliminarAdministrativo(id);
        return ResponseEntity.noContent().build();
    }
}