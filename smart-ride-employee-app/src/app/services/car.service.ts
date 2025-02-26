import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  private BASE_URL = 'http://localhost:8080/api/v1/car';
  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get(this.BASE_URL);
  }
}
