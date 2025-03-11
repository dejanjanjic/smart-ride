import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Manufacturer } from '../model/manufacturer.model';

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
  public getById(id: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}/${id}`);
  }

  public filter(keyword: string): Observable<any> {
    return this.http.get(`${this.BASE_URL}/search/${keyword}`);
  }

  public add(manufacturer: Manufacturer): Observable<any> {
    return this.http.post(this.BASE_URL, manufacturer);
  }
  public deleteById(id: number) {
    return this.http.delete(`${this.BASE_URL}/${id}`);
  }
}
