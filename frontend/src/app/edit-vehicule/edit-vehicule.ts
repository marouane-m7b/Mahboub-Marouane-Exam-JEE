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
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './edit-vehicule.html',
  styleUrl: './edit-vehicule.css'
})
export class EditVehicule implements OnInit {
  vehiculeId!: string;
  vehicule!: VehiculeDTO;
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
    this.vehiculeService.getVehicule(this.vehiculeId).subscribe({
      next: (data) => {
        this.vehicule = data;
        if (this.vehicule.type === 'Voiture') {
          let voiture = this.vehicule as VoitureDTO;
          this.editVehiculeFormGroup = this.fb.group({
            id: [voiture.id],
            type: [voiture.type],
            marque: [voiture.marque, Validators.required],
            modele: [voiture.modele, Validators.required],
            matricule: [voiture.matricule, Validators.required],
            prixParJour: [voiture.prixParJour, [Validators.required, Validators.min(0)]],
            statut: [voiture.statut, Validators.required],
            agenceDTO: [voiture.agenceDTO, Validators.required],
            nombrePortes: [voiture.nombrePortes, Validators.required],
            typeCarburant: [voiture.typeCarburant, Validators.required],
            boiteVitesse: [voiture.boiteVitesse, Validators.required]
          });
        } else {
          let moto = this.vehicule as MotoDTO;
          this.editVehiculeFormGroup = this.fb.group({
            id: [moto.id],
            type: [moto.type],
            marque: [moto.marque, Validators.required],
            modele: [moto.modele, Validators.required],
            matricule: [moto.matricule, Validators.required],
            prixParJour: [moto.prixParJour, [Validators.required, Validators.min(0)]],
            statut: [moto.statut, Validators.required],
            agenceDTO: [moto.agenceDTO, Validators.required],
            cylindree: [moto.cylindree, Validators.required],
            typeMoto: [moto.typeMoto, Validators.required],
            casqueInclus: [moto.casqueInclus]
          });
        }
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
