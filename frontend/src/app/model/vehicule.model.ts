import { AgenceDTO } from './agence.model';

export type StatutVehicule = 'Disponible' | 'Loue' | 'EnMaintenance';
export type TypeCarburant = 'Essence' | 'Diesel' | 'Hybride' | 'Electrique';
export type BoiteVitesse = 'Manuelle' | 'Automatique';
export type TypeMoto = 'Sportive' | 'Scooter' | 'Roadster' | 'Touring';

export interface VehiculeDTO {
  type: string;
  id: string;
  marque: string;
  modele: string;
  matricule: string;
  prixParJour: number;
  dateMiseEnService: string;
  statut: StatutVehicule;
  agenceDTO: AgenceDTO;
}

export interface VoitureDTO extends VehiculeDTO {
  nombrePortes: number;
  typeCarburant: TypeCarburant;
  boiteVitesse: BoiteVitesse;
}

export interface MotoDTO extends VehiculeDTO {
  cylindree: number;
  typeMoto: TypeMoto;
  casqueInclus: boolean;
}
