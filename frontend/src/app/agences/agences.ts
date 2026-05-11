import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AgenceService } from '../services/agence.service';
import { AgenceDTO } from '../model/agence.model';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { catchError, map, Observable, throwError } from 'rxjs';

@Component({
  selector: 'app-agences',
  standalone: true,
  imports: [CommonModule, RouterLink, ReactiveFormsModule],
  templateUrl: './agences.html',
  styleUrl: './agences.css'
})
export class Agences implements OnInit {
  agences!: Observable<AgenceDTO[]>;
  errorMessage!: string;
  searchFormGroup!: FormGroup;

  constructor(private agenceService: AgenceService, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.searchFormGroup = this.fb.group({
      keyword: ['']
    });
    this.handleLoadAgences();
  }

  handleSearchAgences() {
    let kw = this.searchFormGroup.value.keyword;
    this.agences = this.agenceService.getAgences().pipe(
      map(data => {
        return data.filter(a => a.nom.toLowerCase().includes(kw.toLowerCase()) || a.ville.toLowerCase().includes(kw.toLowerCase()));
      }),
      catchError(err => {
        this.errorMessage = err.message;
        return throwError(() => err);
      })
    );
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
    this.agenceService.deleteAgence(agence.id).subscribe({
      next: () => {
        this.handleLoadAgences();
      },
      error: err => {
        console.log(err);
      }
    });
  }
}
