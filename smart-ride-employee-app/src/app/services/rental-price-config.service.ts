import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { RentalConfig } from '../model/rental-config.model';

@Injectable({
  providedIn: 'root',
})
export class RentalPriceConfigService {
  private BASE_URL = 'http://localhost:8080/api/v1/rental-price-config';
  constructor(private http: HttpClient) {}

  public getAll(): Observable<any> {
    return this.http.get(this.BASE_URL);
  }

  public update(rentalConfig: RentalConfig) {
    return this.http.put(this.BASE_URL, rentalConfig);
  }
}
