import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ClientSimple } from '../model/client.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private BASE_URL = 'http://localhost:8080/api/v1/client';
  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get(this.BASE_URL);
  }
  public filter(keyword: string): Observable<any> {
    return this.http.get(`${this.BASE_URL}/search/${keyword}`);
  }
  public update(client: ClientSimple) {
    return this.http.put(this.BASE_URL, client);
  }
}
