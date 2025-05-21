import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { TableComponent } from '../../components/table/table.component';
import { ModalformComponent } from '../../components/modalform/modalform.component';
import { ReactiveFormsModule } from '@angular/forms';
import { environment } from '../../../environments/environment';
import { PaginatorModule } from 'primeng/paginator';

@Component({
  selector: 'app-administrativos',
  standalone: true,
  imports: [CommonModule, TableComponent, ModalformComponent, ReactiveFormsModule, PaginatorModule],
  templateUrl: './administrativos.component.html',
  styleUrl: './administrativos.component.css'
})
export class AdministrativosComponent implements OnInit {

  administrativos: any[] = [];
  columns = [
    { field: 'nombre', header: 'Nombre' },
    { field: 'apellido', header: 'Apellido' },
    { field: 'cargo', header: 'Cargo' },
    { field: 'departamento', header: 'Departamento' }
  ];

  fields = [
    { name: 'nombre', label: 'Nombre', type: 'text', required: true },
    { name: 'apellido', label: 'Apellido', type: 'text', required: true },
    { name: 'cargo', label: 'Cargo', type: 'text', required: true },
    { name: 'departamento', label: 'Departamento', type: 'text', required: true }
  ];

  formData: any = {};
  loading = false;
  modalVisible = false;
  modalTitle = 'Agregar Administrativo';
  selectedAdministrativo: any = null;

  totalElements = 0;
  pageSize = 10;
  currentPage = 0;

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.cargarAdministrativos();
  }

  cargarAdministrativos(page: number = 0, size: number = 10): void {
    this.loading = true;

    this.http.get(`${environment.apiUrl}/administrativos`, {
      params: { page: page.toString(), size: size.toString() }
    }).subscribe({
      next: (res: any) => {
        this.administrativos = res.content.map((adm: any) => ({
          ...adm,
          nombre: adm.persona?.nombre || '',
          apellido: adm.persona?.apellido || ''
        }));
        this.totalElements = res.totalElements;
        this.pageSize = res.size;
        this.currentPage = res.number;
        this.loading = false;
      },
      error: () => {
        this.loading = false;
        console.error('Error al cargar administrativos');
      }
    });
  }

  onPageChange(event: any): void {
    this.cargarAdministrativos(event.page, event.rows);
  }

  abrirModal(administrativo: any = null): void {
    this.selectedAdministrativo = administrativo;

    this.formData = administrativo ? {
      nombre: administrativo.persona?.nombre || '',
      apellido: administrativo.persona?.apellido || '',
      cargo: administrativo.cargo || '',
      departamento: administrativo.departamento || '',
      id: administrativo.id
    } : {
      nombre: '',
      apellido: '',
      cargo: '',
      departamento: ''
    };

    this.modalTitle = administrativo ? 'Editar Administrativo' : 'Agregar Administrativo';
    this.modalVisible = true;
  }

  cerrarModal(): void {
    this.modalVisible = false;
    this.selectedAdministrativo = null;
  }

  guardarAdministrativo(data: any): void {
    const dto = {
      nombre: data.nombre,
      apellido: data.apellido,
      cargo: data.cargo,
      departamento: data.departamento
    };

    const peticion = this.selectedAdministrativo
      ? this.http.put(`${environment.apiUrl}/administrativos/${this.selectedAdministrativo.id}`, dto)
      : this.http.post(`${environment.apiUrl}/administrativos`, dto);

    peticion.subscribe({
      next: () => {
        this.cargarAdministrativos();
        this.cerrarModal();
      },
      error: (err) => console.error('❌ Error guardando administrativo', err)
    });
  }

  eliminar(administrativo: any): void {
    if (!confirm('¿Estás seguro de eliminar este administrativo?')) return;

    this.http.delete(`${environment.apiUrl}/administrativos/${administrativo.id}`).subscribe({
      next: () => this.cargarAdministrativos(),
      error: (err) => console.error('Error al eliminar administrativo', err)
    });
  }
}