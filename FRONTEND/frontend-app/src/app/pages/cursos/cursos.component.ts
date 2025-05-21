import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { ModalformComponent } from '../../components/modalform/modalform.component';
import { ReactiveFormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';
import { PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'app-cursos',
  standalone: true,
  imports: [CommonModule, TableComponent, ModalformComponent, ReactiveFormsModule, PaginatorModule],
  templateUrl: './cursos.component.html',
  styleUrl: './cursos.component.css'
})
export class CursosComponent implements OnInit {
  cursos: any[] = [];
  profesores: any[] = [];

  columns = [
    { field: 'nombre', header: 'Nombre' },
    { field: 'descripcion', header: 'Descripción' },
    { field: 'creditos', header: 'Créditos' },
    { field: 'profesorNombre', header: 'Profesor' }
  ];

  fields = [
    { name: 'nombre', label: 'Nombre', type: 'text', required: true },
    { name: 'descripcion', label: 'Descripción', type: 'text', required: true },
    { name: 'creditos', label: 'Créditos', type: 'number', required: true },
    {
      name: 'idProfesor',
      label: 'Profesor',
      type: 'select',
      required: true,
      options: []
    }
  ];

  formData: any = {};
  loading = false;
  modalVisible = false;
  modalTitle = 'Agregar Curso';
  selectedCurso: any = null;

  totalElements = 0;
  pageSize = 10;
  currentPage = 0;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.cargarCursos();
    this.cargarProfesores();
  }

  cargarCursos(page: number = 0, size: number = 10): void {
    this.loading = true;
    this.http.get(`${environment.apiUrl}/curso`, {
      params: { page, size }
    }).subscribe({
      next: (res: any) => {
        this.cursos = res.content.map((curso: any) => ({
          ...curso,
          profesorNombre: `${curso.profesor?.persona?.nombre || ''} ${curso.profesor?.persona?.apellido || ''}`
        }));
        this.totalElements = res.totalElements;
        this.pageSize = res.size;
        this.currentPage = res.number;
        this.loading = false;
      },
      error: () => { this.loading = false; }
    });
  }

  private profesoresCargados = false;

  cargarProfesores(): void {
    if (this.profesoresCargados) return;

    this.http.get(`${environment.apiUrl}/profesores`).subscribe({
      next: (res: any) => {
        this.profesores = res.content;
        const opciones = res.content.map((prof: any) => ({
          label: `${prof.persona.nombre} ${prof.persona.apellido}`,
          value: prof.id
        }));
        const field = this.fields.find(f => f.name === 'idProfesor');
        if (field) field.options = opciones;
        this.profesoresCargados = true;
      }
    });
  }

  onPageChange(event: any): void {
    this.cargarCursos(event.page, event.rows);
  }

  abrirModal(curso: any = null): void {
    const profesorField = this.fields.find(f => f.name === 'idProfesor');
    if (!this.profesoresCargados) {
      this.cargarProfesores();
    }

    this.selectedCurso = curso;
    this.formData = curso
      ? {
        nombre: curso.nombre,
        descripcion: curso.descripcion,
        creditos: curso.creditos,
        idProfesor: curso.profesor?.id || ''
      }
      : {};

    this.modalTitle = curso ? 'Editar Curso' : 'Agregar Curso';
    this.modalVisible = true;
  }


  cerrarModal(): void {
    this.modalVisible = false;
    this.selectedCurso = null;
  }

  guardarCurso(data: any): void {
    const dto = {
      nombre: data.nombre,
      descripcion: data.descripcion,
      creditos: Number(data.creditos),
      idProfesor: Number(data.idProfesor)
    };

    if (this.selectedCurso) {
      this.http.put(`${environment.apiUrl}/curso/${this.selectedCurso.id}`, dto).subscribe({
        next: () => {
          this.cargarCursos();
          this.cerrarModal();
        }
      });
    } else {
      this.http.post(`${environment.apiUrl}/curso`, dto).subscribe({
        next: () => {
          this.cargarCursos();
          this.cerrarModal();
        }
      });
    }
  }

  eliminar(curso: any): void {
    if (!confirm('¿Estás seguro de eliminar este curso?')) return;

    this.http.delete(`${environment.apiUrl}/curso/${curso.id}`).subscribe({
      next: () => this.cargarCursos()
    });
  }
}