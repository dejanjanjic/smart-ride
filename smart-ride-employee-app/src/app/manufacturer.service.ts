import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class ManufacturerService {
  private BASE_URL = 'http://localhost:8080/api/v1/manufacturer';
  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get(this.BASE_URL);
  }
  public getAllNames() {
    return this.http.get(`${this.BASE_URL}/names`);
  }
}
