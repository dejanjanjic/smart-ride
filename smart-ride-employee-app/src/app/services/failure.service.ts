import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Failure } from '../model/failure.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FailureService {
  private BASE_URL = 'http://localhost:8080/api/v1/failure';
  constructor(private http: HttpClient) {}

  public getAllByVehicleId(id: string) {
    return this.http.get(`${this.BASE_URL}/vehicle/${id}`);
  }

  public add(failure: Failure): Observable<any> {
    return this.http.post(this.BASE_URL, failure);
  }
}
