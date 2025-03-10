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
  public getById(id: string): Observable<any> {
    return this.http.get(`${this.BASE_URL}/${id}`);
  }
  public getImageById(id: string): Observable<Blob> {
    return this.http.get(`${this.BASE_URL}/${id}/image`, {
      responseType: 'blob',
    });
  }

  public add(eBike: EBike): Observable<any> {
    return this.http.post(this.BASE_URL, eBike);
  }

  public uploadImage(id: string, image: File) {
    const formData = new FormData();
    formData.append('file', image);
    return this.http.put<void>(`${this.BASE_URL}/${id}/image`, formData);
  }

  public deleteById(id: string) {
    return this.http.delete(`${this.BASE_URL}/${id}`);
  }
}
