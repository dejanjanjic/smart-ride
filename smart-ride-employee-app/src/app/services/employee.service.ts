import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AddEmployeeDTO, EmployeeSimple } from '../model/employee.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private BASE_URL = 'http://localhost:8080/api/v1/employee';

  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get(this.BASE_URL);
  }

  public getById(id: number): Observable<any> {
    return this.http.get(`${this.BASE_URL}/${id}`);
  }
  public filter(keyword: string): Observable<any> {
    return this.http.get(`${this.BASE_URL}/search/${keyword}`);
  }

  public add(employee: AddEmployeeDTO): Observable<any> {
    return this.http.post(this.BASE_URL, employee);
  }

  public update(employee: EmployeeSimple): Observable<any> {
    return this.http.put(this.BASE_URL, employee);
  }

  public deleteById(id: number) {
    return this.http.delete(`${this.BASE_URL}/${id}`);
  }
}
