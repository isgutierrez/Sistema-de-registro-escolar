import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { ModalformComponent } from '../../components/modalform/modalform.component';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-profesores',
  standalone: true,
  imports: [CommonModule, TableComponent, ModalformComponent],
  templateUrl: './profesores.component.html',
  styleUrl: './profesores.component.css'
})
export class ProfesoresComponent implements OnInit {
  profesores: any[] = [];
  columns = [
    { field: 'nombre', header: 'Nombre' },
    { field: 'apellido', header: 'Apellido' },
    { field: 'especialidad', header: 'Especialidad' },
    { field: 'fechaContratacion', header: 'Fecha Contratación' },
  ];

  fields = [
    { name: 'nombre', label: 'Nombre', type: 'text', required: true },
    { name: 'apellido', label: 'Apellido', type: 'text', required: true },
    { name: 'especialidad', label: 'Especialidad', type: 'text', required: true },
    { name: 'fechaContratacion', label: 'Fecha Contratación', type: 'date', required: true }
  ];

  loading = false;
  modalVisible = false;
  selectedProfesor: any = null;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarProfesores();
  }

  cargarProfesores() {
    this.loading = true;
    this.http.get(`${environment.apiUrl}/profesores`).subscribe({
      next: (res: any) => {
        this.profesores = res;
        this.loading = false;
      },
      error: () => (this.loading = false)
    });
  }

  abrirModal(profesor?: any) {
    this.selectedProfesor = profesor || null;
    this.modalVisible = true;
  }

  cerrarModal() {
    this.modalVisible = false;
    this.selectedProfesor = null;
  }

  guardarProfesor(data: any) {
    const persona = {
      nombre: data.nombre,
      apellido: data.apellido,
      email: data.email,
      telefono: data.telefono,
      fechaNacimiento: data.fechaNacimiento
    };

    this.http.post(`${environment.apiUrl}/personas`, persona).subscribe({
      next: (nuevaPersona: any) => {
        const profesor = {
          persona: { id: nuevaPersona.id },
          especialidad: data.especialidad,
          fechaContratacion: data.fechaContratacion
        };

        const request$ = this.selectedProfesor
          ? this.http.put(`${environment.apiUrl}/profesores/${this.selectedProfesor.id}`, profesor)
          : this.http.post(`${environment.apiUrl}/profesores`, profesor);

        request$.subscribe({
          next: () => {
            this.cargarProfesores();
            this.cerrarModal();
          },
          error: (err) => console.error('Error al guardar profesor', err)
        });
      },
      error: (err) => console.error('Error al guardar persona', err)
    });
  }

  eliminar(profesor: any) {
    if (!confirm('¿Estás seguro de eliminar este profesor?')) return;
    this.http.delete(`${environment.apiUrl}/profesores/${profesor.id}`).subscribe({
      next: () => this.cargarProfesores()
    });
  }
}