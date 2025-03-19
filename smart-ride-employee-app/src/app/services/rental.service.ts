import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class RentalService {
  private BASE_URL = 'http://localhost:8080/api/v1/rental';
  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get(this.BASE_URL);
  }

  public getAllByVehicleId(id: string) {
    return this.http.get(`${this.BASE_URL}/vehicle/${id}`);
  }
}
