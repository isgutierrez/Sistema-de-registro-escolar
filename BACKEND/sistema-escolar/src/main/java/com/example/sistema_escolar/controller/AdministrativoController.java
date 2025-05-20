package com.example.sistema_escolar.controller;

import com.example.sistema_escolar.model.Administrativo;
import com.example.sistema_escolar.service.AdministrativoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/administrativos")
public class AdministrativoController {

    private final AdministrativoService administrativoService;

    public AdministrativoController(AdministrativoService administrativoService) {
        this.administrativoService = administrativoService;
    }

    @PostMapping
    public ResponseEntity<Administrativo> crear(@RequestBody Administrativo administrativo) {
        Administrativo nuevo = administrativoService.guardarAdministrativo(administrativo);
        return ResponseEntity.ok(nuevo);
    }

    @GetMapping
    public ResponseEntity<List<Administrativo>> listar() {
        return ResponseEntity.ok(administrativoService.listarAdministrativos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrativo> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(administrativoService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrativo> actualizar(@PathVariable Long id, @RequestBody Administrativo administrativoActualizado) {
        Administrativo actualizado = administrativoService.actualizarAdministrativo(id, administrativoActualizado);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        administrativoService.eliminarAdministrativo(id);
        return ResponseEntity.noContent().build();
    }
}