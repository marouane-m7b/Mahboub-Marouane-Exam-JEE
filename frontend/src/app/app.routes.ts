import { Routes } from '@angular/router';
import { Agences } from './agences/agences';
import { Vehicules } from './vehicules/vehicules';

export const routes: Routes = [
  { path: '', redirectTo: '/agences', pathMatch: 'full' },
  { path: 'agences', component: Agences },
  { path: 'vehicules', component: Vehicules },
];

