import { Routes } from '@angular/router';
import { MainComponent } from './layout/main/main.component'
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { PersonasComponent } from './pages/personas/personas.component';
import { EstudiantesComponent } from './pages/estudiantes/estudiantes.component';
import { ProfesoresComponent } from './pages/profesores/profesores.component';

export const routes: Routes = [
    {
        path: '',
        component: MainComponent,
        children: [
            {
                path: '',
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
        ]
    }
];
