import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { ModalformComponent } from '../../components/modalform/modalform.component';
import { ReactiveFormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-estudiantes',
  standalone: true,
  imports: [CommonModule, TableComponent, ModalformComponent, ReactiveFormsModule],
  templateUrl: './estudiantes.component.html',
  styleUrl: './estudiantes.component.css'
})
export class EstudiantesComponent implements OnInit {
  estudiantes: any[] = [];
  columns = [
    { field: 'nombre', header: 'Nombre' },
    { field: 'apellido', header: 'Apellido' },
    { field: 'numeroMatricula', header: 'Número Matricula' },
    { field: 'grado', header: 'Grado' }
  ];

  fields = [
    { name: 'nombre', label: 'Nombre', type: 'text', required: true },
    { name: 'apellido', label: 'Apellido', type: 'text', required: true },
    { name: 'numeroMatricula', label: 'Número de Matrícula', type: 'text', required: true },
    { name: 'grado', label: 'Grado', type: 'text', required: true }
  ];

  formData: any = {};
  loading = false;
  modalVisible = false;
  modalTitle = 'Agregar Estudiante';

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.cargarEstudiantes();
  }

  cargarEstudiantes() {
    this.loading = true;
    this.http.get(`${environment.apiUrl}/estudiantes`).subscribe({
      next: (res: any) => {
        this.estudiantes = res;
        this.loading = false;
      },
      error: () => (this.loading = false)
    });
  }

  abrirModal(estudiante: any = null) {
    this.formData = estudiante ? { ...estudiante } : {};
    this.modalTitle = estudiante ? 'Editar Estudiante' : 'Agregar Estudiante';
    this.modalVisible = true;
  }

  cerrarModal() {
    this.modalVisible = false;
  }

  guardarEstudiante(data: any) {
    const peticion = data.id
      ? this.http.put(`${environment.apiUrl}/estudiantes/${data.id}`, data)
      : this.http.post(`${environment.apiUrl}/estudiantes`, data);

    peticion.subscribe({
      next: () => {
        this.cargarEstudiantes();
        this.cerrarModal();
      }
    });
  }

  eliminar(estudiante: any) {
    if (!confirm('¿Estás seguro de eliminar este estudiante?')) return;

    this.http.delete(`${environment.apiUrl}/estudiantes/${estudiante.id}`).subscribe({
      next: () => this.cargarEstudiantes()
    });
  }

  editar(estudiante: any) {
    this.abrirModal(estudiante);
  }
}