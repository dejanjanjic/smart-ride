import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';

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
    return this.http
      .get(`${this.BASE_URL}/vehicle/${id}`)
      .pipe(tap((result) => console.log('API Response:', result)));
  }

  public getRevenueByDay(start: Date, end: Date): Observable<any> {
    return this.http.post(`${this.BASE_URL}/revenue-by-day`, {
      start: start.toISOString(),
      end: end.toISOString(),
    });
  }
  public getRevenueByVehicleType(): Observable<any> {
    return this.http.get(`${this.BASE_URL}/revenue-by-vehicle-type`);
  }
}
