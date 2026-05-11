import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AgenceService } from '../services/agence.service';
import { AgenceDTO } from '../model/agence.model';

@Component({
  selector: 'app-edit-agence',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
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
    this.agenceService.getAgence(this.agenceId).subscribe({
      next: (agence) => {
        this.editAgenceFormGroup = this.fb.group({
          id: [agence.id],
          nom: [agence.nom, Validators.required],
          adresse: [agence.adresse, Validators.required],
          ville: [agence.ville, Validators.required],
          telephone: [agence.telephone, Validators.required]
        });
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
