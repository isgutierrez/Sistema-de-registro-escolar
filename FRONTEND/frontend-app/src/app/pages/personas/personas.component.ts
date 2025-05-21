import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { PaginatorModule } from 'primeng/paginator';
import { TableComponent } from '../../components/table/table.component';
import { ModalformComponent } from '../../components/modalform/modalform.component';
import { PersonaService } from '../../services/persona.service';

@Component({
  selector: 'app-personas',
  standalone: true,
  imports: [CommonModule, TableComponent, ModalformComponent, ReactiveFormsModule, PaginatorModule],
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

  formData: any = {};
  loading = false;
  modalVisible = false;
  selectedPersona: any = null;
  modalTitle = 'Registrar Persona';

  totalElements = 0;
  pageSize = 10;
  currentPage = 0;

  fields = [
    { name: 'nombre', label: 'Nombre', type: 'text', required: true },
    { name: 'apellido', label: 'Apellido', type: 'text', required: true },
    { name: 'email', label: 'Email', type: 'email', required: true },
    { name: 'telefono', label: 'Teléfono', type: 'tel', required: true },
    { name: 'fechaNacimiento', label: 'Fecha de Nacimiento', type: 'date', required: true }
  ];

  constructor(private personaService: PersonaService, private fb: FormBuilder) { }

  ngOnInit(): void {
    this.cargarPersonas();
  }

  cargarPersonas(page: number = 0, size: number = 10): void {
    this.loading = true;
    this.personaService.getPaginated(page, size).subscribe({
      next: (res: any) => {
        this.personas = res.content;
        this.totalElements = res.totalElements;
        this.pageSize = res.size;
        this.currentPage = res.number;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
      }
    });
  }

  onPageChange(event: any): void {
    this.cargarPersonas(event.page, event.rows);
  }

  abrirModal(persona?: any): void {
    this.selectedPersona = persona || null;
    this.modalTitle = persona ? 'Editar Persona' : 'Registrar Persona';

    this.formData = persona
      ? {
        id: persona.id, 
        nombre: persona.nombre,
        apellido: persona.apellido,
        email: persona.email,
        telefono: persona.telefono,
        fechaNacimiento: persona.fechaNacimiento
      }
      : {
        nombre: '',
        apellido: '',
        email: '',
        telefono: '',
        fechaNacimiento: ''
      };

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