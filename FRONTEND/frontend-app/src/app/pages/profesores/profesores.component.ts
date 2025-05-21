import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { ModalformComponent } from '../../components/modalform/modalform.component';
import { environment } from '../../../environments/environment';
import { ReactiveFormsModule } from '@angular/forms';
import { PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'app-profesores',
  standalone: true,
  imports: [CommonModule, TableComponent, ModalformComponent, ReactiveFormsModule, PaginatorModule],
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

  formData: any = {};
  loading = false;
  modalVisible = false;
  modalTitle = 'Agregar Profesor';
  selectedProfesor: any = null;

  totalElements = 0;
  pageSize = 10;
  currentPage = 0;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.cargarProfesores();
  }

  cargarProfesores(page: number = 0, size: number = 10): void {
    this.loading = true;
    const params = new HttpParams()
      .set('page', page)
      .set('size', size);

    this.http.get(`${environment.apiUrl}/profesores`, { params }).subscribe({
      next: (res: any) => {
        this.profesores = res.content.map((prof: any) => ({
          ...prof,
          nombre: prof.persona?.nombre || '',
          apellido: prof.persona?.apellido || ''
        }));
        this.totalElements = res.totalElements;
        this.pageSize = res.size;
        this.currentPage = res.number;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        console.error('Error al cargar profesores');
      }
    });
  }

  onPageChange(event: any): void {
    this.cargarProfesores(event.page, event.rows);
  }

  abrirModal(profesor: any = null): void {
    this.selectedProfesor = profesor;
    this.formData = profesor ? {
      nombre: profesor.persona?.nombre || '',
      apellido: profesor.persona?.apellido || '',
      especialidad: profesor.especialidad || '',
      fechaContratacion: profesor.fechaContratacion || '',
      id: profesor.id
    } : {};

    this.modalTitle = profesor ? 'Editar Profesor' : 'Agregar Profesor';
    this.modalVisible = true;
  }


  cerrarModal(): void {
    this.modalVisible = false;
    this.selectedProfesor = null;
  }

  guardarProfesor(data: any): void {
    const dto = {
      nombre: data.nombre,
      apellido: data.apellido,
      especialidad: data.especialidad,
      fechaContratacion: data.fechaContratacion
    };

    if (this.selectedProfesor) {
      this.http.put(`${environment.apiUrl}/profesores/${this.selectedProfesor.id}`, dto).subscribe({
        next: () => {
          this.cargarProfesores();
          this.cerrarModal();
        },
        error: (err) => {
          console.error('❌ Error al actualizar profesor', err);
        }
      });
    } else {
      this.http.post(`${environment.apiUrl}/profesores`, dto).subscribe({
        next: () => {
          this.cargarProfesores();
          this.cerrarModal();
        },
        error: (err) => {
          console.error('❌ Error al crear profesor', err);
        }
      });
    }
  }

  eliminar(profesor: any): void {
    if (!confirm('¿Estás seguro de eliminar este profesor?')) return;

    this.http.delete(`${environment.apiUrl}/profesores/${profesor.id}`).subscribe({
      next: () => this.cargarProfesores(),
      error: (err) => console.error('Error al eliminar profesor', err)
    });
  }
}