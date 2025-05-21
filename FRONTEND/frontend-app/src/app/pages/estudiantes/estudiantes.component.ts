import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { ModalformComponent } from '../../components/modalform/modalform.component';
import { ReactiveFormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';
import { PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'app-estudiantes',
  standalone: true,
  imports: [CommonModule, TableComponent, ModalformComponent, ReactiveFormsModule, PaginatorModule],
  templateUrl: './estudiantes.component.html',
  styleUrl: './estudiantes.component.css'
})
export class EstudiantesComponent implements OnInit {
  estudiantes: any[] = [];
  columns = [
    { field: 'nombre', header: 'Nombre' },
    { field: 'apellido', header: 'Apellido' },
    { field: 'numeroMatricula', header: 'Número Matrícula' },
    { field: 'grado', header: 'Grado' }
  ];

  fields = [
    { name: 'nombre', label: 'Nombre', type: 'text', required: true },
    { name: 'apellido', label: 'Apellido', type: 'text', required: true },
    { name: 'numeroMatricula', label: 'Número de Matrícula', type: 'text', required: true },
    { name: 'grado', label: 'Grado', type: 'number', required: true }
  ];

  formData: any = {};
  loading = false;
  modalVisible = false;
  modalTitle = 'Agregar Estudiante';
  selectedEstudiante: any = null;

  totalElements = 0;
  pageSize = 10;
  currentPage = 0;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.cargarEstudiantes();
  }

  cargarEstudiantes(page: number = 0, size: number = 10): void {
    this.loading = true;

    this.http.get(`${environment.apiUrl}/estudiantes`, {
      params: {
        page: page.toString(),
        size: size.toString()
      }
    }).subscribe({
      next: (res: any) => {
        if (!res || !res.content) {
          console.error('❌ Respuesta inesperada del backend:', res);
          this.estudiantes = [];
          this.loading = false;
          return;
        }

        this.estudiantes = res.content.map((est: any) => ({
          ...est,
          nombre: est.persona?.nombre || '',
          apellido: est.persona?.apellido || ''
        }));

        this.totalElements = res.totalElements;
        this.pageSize = res.size;
        this.currentPage = res.number;
        this.loading = false;
      }

    });
  }

  onPageChange(event: any): void {
    this.cargarEstudiantes(event.page, event.rows);
  }

  abrirModal(estudiante: any = null): void {
    this.selectedEstudiante = estudiante;
    this.formData = estudiante ? {
      nombre: estudiante.persona?.nombre,
      apellido: estudiante.persona?.apellido,
      numeroMatricula: estudiante.numeroMatricula,
      grado: estudiante.grado,
      id: estudiante.id
    } : {};

    this.modalTitle = estudiante ? 'Editar Estudiante' : 'Agregar Estudiante';
    this.modalVisible = true;
  }

  cerrarModal(): void {
    this.modalVisible = false;
    this.selectedEstudiante = null;
  }

  guardarEstudiante(data: any): void {
    const estudianteDTO = {
      nombre: data.nombre,
      apellido: data.apellido,
      numeroMatricula: data.numeroMatricula,
      grado: Number(data.grado)
    };

    if (this.selectedEstudiante) {
      this.http.put(`${environment.apiUrl}/estudiantes/${this.selectedEstudiante.id}`, estudianteDTO).subscribe({
        next: () => {
          this.cargarEstudiantes();
          this.cerrarModal();
        },
        error: (err) => console.error('❌ Error actualizando estudiante', err)
      });
    } else {
      this.http.post(`${environment.apiUrl}/estudiantes`, estudianteDTO).subscribe({
        next: () => {
          this.cargarEstudiantes();
          this.cerrarModal();
        },
        error: (err) => console.error('❌ Error creando estudiante', err)
      });
    }
  }

  eliminar(estudiante: any): void {
    if (!confirm('¿Estás seguro de eliminar este estudiante?')) return;

    this.http.delete(`${environment.apiUrl}/estudiantes/${estudiante.id}`).subscribe({
      next: () => this.cargarEstudiantes(),
      error: (err) => console.error('Error al eliminar estudiante', err)
    });
  }

  editar(estudiante: any): void {
    this.abrirModal(estudiante);
  }
}