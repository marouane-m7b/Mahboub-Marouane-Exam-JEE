import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AgenceDTO } from '../model/agence.model';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AgenceService {
  constructor(private http: HttpClient) {}

  public getAgences(): Observable<AgenceDTO[]> {
    return this.http.get<AgenceDTO[]>(environment.backendHost + '/agences');
  }

  public getAgence(id: string): Observable<AgenceDTO> {
    return this.http.get<AgenceDTO>(environment.backendHost + '/agences/' + id);
  }

  public saveAgence(agence: AgenceDTO): Observable<AgenceDTO> {
    return this.http.post<AgenceDTO>(environment.backendHost + '/agences', agence);
  }

  public updateAgence(agence: AgenceDTO): Observable<AgenceDTO> {
    return this.http.put<AgenceDTO>(environment.backendHost + '/agences/' + agence.id, agence);
  }

  public deleteAgence(id: string): Observable<void> {
    return this.http.delete<void>(environment.backendHost + '/agences/' + id);
  }
}
