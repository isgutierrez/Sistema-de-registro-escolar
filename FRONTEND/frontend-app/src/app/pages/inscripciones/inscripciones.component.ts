import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { ModalformComponent } from '../../components/modalform/modalform.component';
import { ReactiveFormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';
import { PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'app-inscripciones',
  standalone: true,
  imports: [CommonModule, TableComponent, ModalformComponent, ReactiveFormsModule, PaginatorModule],
  templateUrl: './inscripciones.component.html',
  styleUrl: './inscripciones.component.css'
})
export class InscripcionesComponent implements OnInit {
  inscripciones: any[] = [];
  formData: any = {};
  loading = false;
  modalVisible = false;
  modalTitle = 'Registrar Inscripción';
  selectedInscripcion: any = null;

  totalElements = 0;
  pageSize = 10;
  currentPage = 0;

  columns = [
    { field: 'estudianteNombre', header: 'Estudiante' },
    { field: 'cursoNombre', header: 'Curso' },
    { field: 'fechaInscripcion', header: 'Fecha Inscripción' },
  ];

  fields = [
    { name: 'idEstudiante', label: 'Estudiante', type: 'select', required: true, options: [] },
    { name: 'idCurso', label: 'Curso', type: 'select', required: true, options: [] },
    { name: 'fechaInscripcion', label: 'Fecha de Inscripción', type: 'date', required: true },
  ];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.cargarInscripciones();
    this.cargarCursosYEstudiantes();
  }

  cargarInscripciones(page: number = 0, size: number = 10): void {
    this.loading = true;
    this.http.get(`${environment.apiUrl}/inscripciones`, {
      params: { page, size }
    }).subscribe({
      next: (res: any) => {
        this.inscripciones = res.content.map((i: any) => ({
          ...i,
          estudianteNombre: `${i.estudiante?.persona?.nombre || ''} ${i.estudiante?.persona?.apellido || ''}`,
          cursoNombre: i.curso?.nombre || ''
        }));
        this.totalElements = res.totalElements;
        this.pageSize = res.size;
        this.currentPage = res.number;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        console.error('Error al cargar inscripciones');
      }
    });
  }

  cargarCursosYEstudiantes(): void {
    // Cursos
    this.http.get(`${environment.apiUrl}/curso`).subscribe({
      next: (res: any) => {
        const cursos = res.content || res; // Si no es paginado
        const cursoOptions = cursos.map((c: any) => ({
          label: c.nombre,
          value: c.id
        }));
        const cursoField = this.fields.find(f => f.name === 'idCurso');
        if (cursoField) cursoField.options = cursoOptions;
      },
      error: () => console.error('Error al cargar cursos')
    });

    // Estudiantes
    this.http.get(`${environment.apiUrl}/estudiantes`).subscribe({
      next: (res: any) => {
        const estudiantes = res.content || res; // Si no es paginado
        const estudianteOptions = estudiantes.map((e: any) => ({
          label: `${e.persona?.nombre} ${e.persona?.apellido}`,
          value: e.id
        }));
        const estudianteField = this.fields.find(f => f.name === 'idEstudiante');
        if (estudianteField) estudianteField.options = estudianteOptions;
      },
      error: () => console.error('Error al cargar estudiantes')
    });
  }

  onPageChange(event: any): void {
    this.cargarInscripciones(event.page, event.rows);
  }

  abrirModal(inscripcion: any = null): void {
    this.selectedInscripcion = inscripcion;
    this.formData = inscripcion
      ? {
        idEstudiante: inscripcion.estudiante?.id,
        idCurso: inscripcion.curso?.id,
        fechaInscripcion: inscripcion.fechaInscripcion,
        id: inscripcion.id
      }
      : { idEstudiante: '', idCurso: '', fechaInscripcion: '' };

    this.modalTitle = inscripcion ? 'Editar Inscripción' : 'Registrar Inscripción';
    this.modalVisible = true;
  }

  cerrarModal(): void {
    this.modalVisible = false;
    this.selectedInscripcion = null;
  }

  guardarInscripcion(data: any): void {
    const dto = {
      idEstudiante: data.idEstudiante,
      idCurso: data.idCurso,
      fechaInscripcion: data.fechaInscripcion
    };

    const peticion = this.selectedInscripcion
      ? this.http.put(`${environment.apiUrl}/inscripciones/${this.selectedInscripcion.id}`, dto)
      : this.http.post(`${environment.apiUrl}/inscripciones`, dto);

    peticion.subscribe({
      next: () => {
        this.cargarInscripciones();
        this.cerrarModal();
      },
      error: (err) => console.error('❌ Error al guardar inscripción', err)
    });
  }

  eliminar(inscripcion: any): void {
    if (!confirm('¿Eliminar esta inscripción?')) return;
    this.http.delete(`${environment.apiUrl}/inscripciones/${inscripcion.id}`).subscribe({
      next: () => this.cargarInscripciones(),
      error: (err) => console.error('Error al eliminar inscripción', err)
    });
  }
}
