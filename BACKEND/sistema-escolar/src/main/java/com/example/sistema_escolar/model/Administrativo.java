package com.example.sistema_escolar.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class Administrativo {

    @Id 
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Persona persona;

    @Column(name="cargo", nullable=false)
    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @Column(nullable = false)
    @NotBlank(message = "El departamento es obligatorio")
    private String departamento;

    //Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}