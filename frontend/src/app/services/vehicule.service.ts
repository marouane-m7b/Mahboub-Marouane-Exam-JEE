import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { VehiculeDTO } from '../model/vehicule.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class VehiculeService {
  constructor(private http: HttpClient) {}

  public getVehicules(): Observable<VehiculeDTO[]> {
    return this.http.get<VehiculeDTO[]>(environment.backendHost + '/vehicules');
  }

  public getVehicule(id: string): Observable<VehiculeDTO> {
    return this.http.get<VehiculeDTO>(environment.backendHost + '/vehicules/' + id);
  }

  public deleteVehicule(id: string): Observable<void> {
    return this.http.delete<void>(environment.backendHost + '/vehicules/' + id);
  }
}
