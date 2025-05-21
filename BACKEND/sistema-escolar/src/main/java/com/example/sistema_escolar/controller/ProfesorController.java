package com.example.sistema_escolar.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import com.example.sistema_escolar.model.Profesor;
import com.example.sistema_escolar.service.ProfesorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.sistema_escolar.dto.ProfesorDTO;

import java.util.List;

@RestController
@RequestMapping("/api/profesores")
public class ProfesorController {

    private final ProfesorService profesorService;

    public ProfesorController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    // Crear un profesor
    @PostMapping
    public ResponseEntity<Profesor> crear(@RequestBody ProfesorDTO dto) {
        Profesor nuevo = profesorService.guardarProfesor(dto);
        return ResponseEntity.ok(nuevo);
    }

    // Obtener todos los profesores
    @GetMapping
    public ResponseEntity<Page<Profesor>> listarProfesores(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Profesor> profesores = profesorService.listarProfesores(pageable);
        return ResponseEntity.ok(profesores);
    }

    // Obtener un profesor por ID
    @GetMapping("/{id}")
    public ResponseEntity<Profesor> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(profesorService.obtenerPorId(id));
    }

    // Actualizar un profesor
    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizarProfesor(@PathVariable Long id, @RequestBody Profesor profesor) {
        return ResponseEntity.ok(profesorService.actualizarProfesor(id, profesor));
    }

    // Eliminar un profesor
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarProfesor(@PathVariable Long id) {
        profesorService.eliminarProfesor(id);
        return ResponseEntity.noContent().build();
    }
}