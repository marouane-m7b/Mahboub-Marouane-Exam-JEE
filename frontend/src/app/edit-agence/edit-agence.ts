import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AgenceService } from '../services/agence.service';
import { AgenceDTO } from '../model/agence.model';

@Component({
  selector: 'app-edit-agence',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './edit-agence.html',
  styleUrl: './edit-agence.css'
})
export class EditAgence implements OnInit {
  agenceId!: string;
  editAgenceFormGroup!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private agenceService: AgenceService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.agenceId = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.editAgenceFormGroup = this.fb.group({
      id: [''],
      nom: ['', Validators.required],
      adresse: ['', Validators.required],
      ville: ['', Validators.required],
      telephone: ['', Validators.required]
    });

    this.agenceService.getAgence(this.agenceId).subscribe({
      next: (agence) => {
        this.editAgenceFormGroup.patchValue(agence);
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  handleUpdateAgence() {
    let agence: AgenceDTO = this.editAgenceFormGroup.value;
    this.agenceService.updateAgence(agence).subscribe({
      next: (data) => {
        alert("Agence mise à jour avec succès !");
        this.router.navigateByUrl("/agences");
      },
      error: (err) => {
        console.log(err);
      }
    });
  }
}
