import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { VehiculeService } from '../services/vehicule.service';
import { VehiculeDTO } from '../model/vehicule.model';
import { catchError, Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-vehicules',
  imports: [CommonModule, RouterLink],
  templateUrl: './vehicules.html',
  styleUrl: './vehicules.css'
})
export class Vehicules implements OnInit {
  vehicules!: Observable<VehiculeDTO[]>;
  errorMessage!: string;

  constructor(private vehiculeService: VehiculeService) {}

  ngOnInit(): void {
    this.handleLoadVehicules();
  }

  handleLoadVehicules() {
    this.vehicules = this.vehiculeService.getVehicules().pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(() => err);
      })
    );
  }

  handleDeleteVehicule(v: VehiculeDTO) {
    let conf = confirm(`Supprimer le véhicule "${v.marque} ${v.modele}" ?`);
    if (!conf) return;
    // Delete button is wired but backend call not yet connected
    console.log('Delete vehicule:', v.id);
    // Will be implemented next step
  }

  getStatutClass(statut: string): string {
    switch (statut) {
      case 'Disponible': return 'badge bg-success';
      case 'Loue': return 'badge bg-danger';
      case 'EnMaintenance': return 'badge bg-warning text-dark';
      default: return 'badge bg-secondary';
    }
  }

  getTypeIcon(type: string): string {
    return type === 'Moto' ? 'bi-bicycle' : 'bi-car-front';
  }
}
