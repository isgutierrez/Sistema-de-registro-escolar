import { Routes } from '@angular/router';
import { MainComponent } from './layout/main/main.component'
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { PersonasComponent } from './pages/personas/personas.component';
import { EstudiantesComponent } from './pages/estudiantes/estudiantes.component';
import { ProfesoresComponent } from './pages/profesores/profesores.component';
import { AdministrativosComponent } from './pages/administrativos/administrativos.component';
import { CursosComponent } from './pages/cursos/cursos.component';
import { InscripcionesComponent } from './pages/inscripciones/inscripciones.component';

export const routes: Routes = [
    {
        path: '',
        component: MainComponent,
        children: [
            {
                path: '',
                redirectTo: 'dashboard',
                pathMatch: 'full',
            },
            {
                path: 'dashboard',
                component: DashboardComponent,
            },
            {
                path: 'personas',
                component: PersonasComponent,
            },
            {
                path: 'estudiantes',
                component: EstudiantesComponent,
            },
            {
                path: 'profesores',
                component: ProfesoresComponent,
            },
            {
                path: 'administrativos',
                component: AdministrativosComponent,
            },
            {
                path: 'cursos',
                component: CursosComponent,
            },
            {
                path: 'inscripciones',
                component: InscripcionesComponent,
            },
            {
                path: '**',
                redirectTo: 'dashboard'
            }
        ]
    }
];