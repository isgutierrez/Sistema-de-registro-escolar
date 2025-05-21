package com.example.sistema_escolar.controller;

import com.example.sistema_escolar.dto.InscripcionDTO;
import com.example.sistema_escolar.model.Inscripcion;
import com.example.sistema_escolar.service.InscripcionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inscripciones")
public class InscripcionController {

    private final InscripcionService inscripcionService;

    public InscripcionController(InscripcionService inscripcionService) {
        this.inscripcionService = inscripcionService;
    }

    @PostMapping
    public ResponseEntity<Inscripcion> crearInscripcion(@RequestBody InscripcionDTO dto) {
        return ResponseEntity.ok(inscripcionService.guardar(dto));
    }

    @GetMapping
    public ResponseEntity<Page<Inscripcion>> listarInscripciones(Pageable pageable) {
        return ResponseEntity.ok(inscripcionService.listarInscripcion(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inscripcion> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(inscripcionService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Inscripcion> actualizarInscripcion(@PathVariable Long id, @RequestBody InscripcionDTO dto) {
        return ResponseEntity.ok(inscripcionService.actualizarInscripcion(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarInscripcion(@PathVariable Long id) {
        inscripcionService.eliminarInscripcion(id);
        return ResponseEntity.noContent().build();
    }
}