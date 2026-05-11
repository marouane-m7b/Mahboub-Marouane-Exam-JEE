import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VehiculeService } from '../services/vehicule.service';
import { AgenceService } from '../services/agence.service';
import { AgenceDTO } from '../model/agence.model';
import { VehiculeDTO, VoitureDTO, MotoDTO } from '../model/vehicule.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-edit-vehicule',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './edit-vehicule.html',
  styleUrl: './edit-vehicule.css'
})
export class EditVehicule implements OnInit {
  vehiculeId!: string;
  vehicule?: VehiculeDTO;
  editVehiculeFormGroup!: FormGroup;
  agences$!: Observable<AgenceDTO[]>;

  constructor(
    private route: ActivatedRoute,
    private vehiculeService: VehiculeService,
    private agenceService: AgenceService,
    private fb: FormBuilder,
    private router: Router
  ) {
    this.vehiculeId = this.route.snapshot.params['id'];
  }

  ngOnInit(): void {
    this.agences$ = this.agenceService.getAgences();
    this.editVehiculeFormGroup = this.fb.group({
      id: [''],
      type: [''],
      marque: ['', Validators.required],
      modele: ['', Validators.required],
      matricule: ['', Validators.required],
      prixParJour: [0, [Validators.required, Validators.min(0)]],
      statut: ['', Validators.required],
      agenceDTO: [null, Validators.required],
      // Optional fields initialized to prevent errors
      nombrePortes: [0],
      typeCarburant: [''],
      boiteVitesse: [''],
      cylindree: [0],
      typeMoto: [''],
      casqueInclus: [false]
    });

    this.vehiculeService.getVehicule(this.vehiculeId).subscribe({
      next: (data) => {
        this.vehicule = data;
        this.editVehiculeFormGroup.patchValue(data);
        // Ensure specific validators are set if needed
        if (this.vehicule.type === 'Voiture') {
          this.editVehiculeFormGroup.get('nombrePortes')?.setValidators(Validators.required);
          this.editVehiculeFormGroup.get('typeCarburant')?.setValidators(Validators.required);
          this.editVehiculeFormGroup.get('boiteVitesse')?.setValidators(Validators.required);
        } else {
          this.editVehiculeFormGroup.get('cylindree')?.setValidators(Validators.required);
          this.editVehiculeFormGroup.get('typeMoto')?.setValidators(Validators.required);
        }
        this.editVehiculeFormGroup.updateValueAndValidity();
      },
      error: (err) => {
        console.log(err);
      }
    });
  }

  handleUpdateVehicule() {
    let v = this.editVehiculeFormGroup.value;
    if (v.type === 'Voiture') {
      this.vehiculeService.updateVoiture(this.vehiculeId, v).subscribe({
        next: () => {
          alert("Voiture mise à jour !");
          this.router.navigateByUrl("/vehicules");
        },
        error: (err) => console.log(err)
      });
    } else {
      this.vehiculeService.updateMoto(this.vehiculeId, v).subscribe({
        next: () => {
          alert("Moto mise à jour !");
          this.router.navigateByUrl("/vehicules");
        },
        error: (err) => console.log(err)
      });
    }
  }

  compareAgences(a1: AgenceDTO, a2: AgenceDTO): boolean {
    return a1 && a2 ? a1.id === a2.id : a1 === a2;
  }
}
