import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Client } from '../model/client.model';

@Injectable({
  providedIn: 'root',
})
export class ClientService {
  private BASE_URL = 'http://localhost:8080/api/v1/client';
  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get(this.BASE_URL);
  }

  public update(client: Client) {
    return this.http.put(this.BASE_URL, client);
  }
}
