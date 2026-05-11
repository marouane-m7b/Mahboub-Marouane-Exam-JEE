import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AgenceService } from '../services/agence.service';
import { AgenceDTO } from '../model/agence.model';
import { catchError, Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-agences',
  imports: [CommonModule, RouterLink],
  templateUrl: './agences.html',
  styleUrl: './agences.css'
})
export class Agences implements OnInit {
  agences!: Observable<AgenceDTO[]>;
  errorMessage!: string;

  constructor(private agenceService: AgenceService) {}

  ngOnInit(): void {
    this.handleLoadAgences();
  }

  handleLoadAgences() {
    this.agences = this.agenceService.getAgences().pipe(
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(() => err);
      })
    );
  }

  handleDeleteAgence(agence: AgenceDTO) {
    let conf = confirm(`Supprimer l'agence "${agence.nom}" ?`);
    if (!conf) return;
    // Delete button is wired but backend call not yet connected
    console.log('Delete agence:', agence.id);
    // Will be implemented next step
  }
}
