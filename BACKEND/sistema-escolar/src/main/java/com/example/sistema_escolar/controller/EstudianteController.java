package com.example.sistema_escolar.controller;

import com.example.sistema_escolar.dto.EstudianteDTO;
import com.example.sistema_escolar.model.Estudiante;
import com.example.sistema_escolar.service.EstudianteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/estudiantes")
public class EstudianteController {


    private final EstudianteService estudianteService;

    @Autowired
    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @PostMapping
    public ResponseEntity<Estudiante> crear(@RequestBody EstudianteDTO dto) {
        return ResponseEntity.ok(estudianteService.guardarEstudiante(dto));
    }

    @GetMapping
    public ResponseEntity<List<Estudiante>> listarEstudiantes() {
        return ResponseEntity.ok(estudianteService.listarEstudiante());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiante> obtenerEstudiante(@PathVariable Long id) {
        Estudiante estudiante = estudianteService.obtenerPorId(id);
        return ResponseEntity.ok(estudiante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiante> actualizarEstudiante(
            @PathVariable Long id,
            @RequestBody Estudiante estudiante) {
        Estudiante actualizado = estudianteService.actualizarEstudiante(id, estudiante);
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        estudianteService.eliminarEstudiante(id);
        return ResponseEntity.noContent().build();
    }
}