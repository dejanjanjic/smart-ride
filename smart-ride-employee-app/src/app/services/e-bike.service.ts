import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { EBike } from '../model/ebike.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EBikeService {
  private BASE_URL = 'http://localhost:8080/api/v1/e-bike';
  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get(this.BASE_URL);
  }
  public add(eBike: EBike): Observable<any> {
    return this.http.post(this.BASE_URL, eBike);
  }
  public deleteById(id: string) {
    return this.http.delete(`${this.BASE_URL}/${id}`);
  }
}
