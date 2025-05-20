import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { TableComponent } from '../../components/table/table.component';
import { ModalformComponent } from '../../components/modalform/modalform.component';
import { PersonaService } from '../../services/persona.service';

@Component({
  selector: 'app-personas',
  standalone: true,
  imports: [CommonModule, TableComponent, ModalformComponent, ReactiveFormsModule],
  templateUrl: './personas.component.html',
  styleUrl: './personas.component.css'
})
export class PersonasComponent implements OnInit {
  personas: any[] = [];
  columns = [
    { field: 'nombre', header: 'Nombre' },
    { field: 'apellido', header: 'Apellido' },
    { field: 'email', header: 'Email' },
    { field: 'telefono', header: 'Teléfono' },
    { field: 'fechaNacimiento', header: 'Fecha Nacimiento' }
  ];

  loading = false;
  modalVisible = false;
  selectedPersona: any = null;
  modalTitle = 'Registrar Persona';

  fields = [
    { name: 'nombre', label: 'Nombre', type: 'text', required: true },
    { name: 'apellido', label: 'Apellido', type: 'text', required: true },
    { name: 'email', label: 'Email', type: 'email', required: true },
    { name: 'telefono', label: 'Teléfono', type: 'tel', required: true },
    { name: 'fechaNacimiento', label: 'Fecha de Nacimiento', type: 'date', required: true }
  ];

  constructor(private personaService: PersonaService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.cargarPersonas();
  }

  cargarPersonas(): void {
    this.loading = true;
    this.personaService.getAll().subscribe({
      next: (res) => {
        this.personas = res;
        this.loading = false;
      },
      error: () => (this.loading = false)
    });
  }

  abrirModal(persona?: any): void {
    this.selectedPersona = persona || null;
    this.modalTitle = persona ? 'Editar Persona' : 'Registrar Persona';
    this.modalVisible = true;
  }

  cerrarModal(): void {
    this.modalVisible = false;
    this.selectedPersona = null;
  }

  guardarPersona(data: any): void {
    const peticion = this.selectedPersona
      ? this.personaService.update(this.selectedPersona.id, data)
      : this.personaService.create(data);

    peticion.subscribe({
      next: () => {
        this.cargarPersonas();
        this.cerrarModal();
      }
    });
  }

  eliminar(persona: any): void {
    if (!confirm('¿Estás seguro de eliminar esta persona?')) return;

    this.personaService.delete(persona.id).subscribe({
      next: () => this.cargarPersonas()
    });
  }
}