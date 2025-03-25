import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class VehicleService {
  private BASE_URL = 'http://localhost:8080/api/v1/vehicle';

  constructor(private http: HttpClient) {}

  getAll(): Observable<any> {
    return this.http.get(this.BASE_URL);
  }
  getVehicleIds(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/ids`);
  }
}
