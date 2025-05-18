import { Component, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { NgIf, NgFor, CommonModule } from '@angular/common';

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterLink, NgIf, NgFor],
  templateUrl: './sidebar.component.html',
  styleUrl: './sidebar.component.css'
})
export class SidebarComponent {
  isOpen = signal(true);

  navItems = [
    {to: '/dashboard', label: 'Dashboard', icon: 'pi pi-home' },
    {to: '/personas', label: 'Personas', icon: 'pi pi-users' },
    {to: '/estudiantes', label: 'Estudiantes', icon: 'pi pi-id-card' },
    {to: '/profesores', label: 'Profesores', icon: 'pi pi-briefcase' },
    {to: '/administrativos', label: 'Administrativos', icon: 'pi pi-user-edit' },
    {to: '/cursos', label: 'Cursos', icon: 'pi pi-book' },
    {to: '/inscripciones', label: 'Inscripciones', icon: 'pi pi-bookmark' },
  ]

  constructor(public router:Router) {}

  toggleSidebar() {
    this.isOpen.set(!this.isOpen());
  }

  isActive(route: string): boolean {
    return this.router.url === route;
  }
}
