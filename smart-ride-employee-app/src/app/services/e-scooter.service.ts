import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EScooter } from '../model/escooter.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EScooterService {
  private BASE_URL = 'http://localhost:8080/api/v1/e-scooter';
  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get(this.BASE_URL);
  }
  public add(eScooter: EScooter): Observable<any> {
    return this.http.post(this.BASE_URL, eScooter);
  }
  public deleteById(id: string) {
    return this.http.delete(`${this.BASE_URL}/${id}`);
  }
}
