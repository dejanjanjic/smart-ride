import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Car } from '../model/car.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class CarService {
  private BASE_URL = 'http://localhost:8080/api/v1/car';
  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get(this.BASE_URL);
  }
  public add(car: Car): Observable<any> {
    return this.http.post(this.BASE_URL, car);
  }
  public deleteById(id: string) {
    return this.http.delete(`${this.BASE_URL}/${id}`);
  }
}
