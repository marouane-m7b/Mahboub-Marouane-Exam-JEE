import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { VehiculeService } from '../services/vehicule.service';
import { VehiculeDTO } from '../model/vehicule.model';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { catchError, map, Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-vehicules',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './vehicules.html',
  styleUrl: './vehicules.css'
})
export class Vehicules implements OnInit {
  vehicules!: Observable<VehiculeDTO[]>;
  errorMessage!: string;
  searchFormGroup!: FormGroup;

  constructor(private vehiculeService: VehiculeService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: ['']
    });
    this.handleLoadVehicules();
  }

  handleSearchVehicules() {
    let kw = this.searchFormGroup.value.keyword;
    this.vehicules = this.vehiculeService.getVehicules().pipe(
      map(data => {
        return data.filter(v => 
          v.marque.toLowerCase().includes(kw.toLowerCase()) || 
          v.modele.toLowerCase().includes(kw.toLowerCase()) ||
          v.matricule.toLowerCase().includes(kw.toLowerCase())
        );
      }),
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(() => err);
      })
    );
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
    this.vehiculeService.deleteVehicule(v.id).subscribe({
      next: () => {
        this.handleLoadVehicules();
      },
      error: err => {
        console.log(err);
      }
    });
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
