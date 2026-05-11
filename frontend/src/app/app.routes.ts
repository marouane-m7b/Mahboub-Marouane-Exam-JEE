import { Routes } from '@angular/router';
import { Agences } from './agences/agences';
import { Vehicules } from './vehicules/vehicules';
import { EditAgence } from './edit-agence/edit-agence';
import { EditVehicule } from './edit-vehicule/edit-vehicule';

export const routes: Routes = [
  { path: '', redirectTo: '/agences', pathMatch: 'full' },
  { path: 'agences', component: Agences },
  { path: 'vehicules', component: Vehicules },
  { path: 'edit-agence/:id', component: EditAgence },
  { path: 'edit-vehicule/:id', component: EditVehicule },
];

