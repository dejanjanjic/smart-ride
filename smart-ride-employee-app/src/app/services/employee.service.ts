import { inject, Injectable } from '@angular/core';
import { LoginDTO } from '../model/login.model';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Employee } from '../model/employee.model';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  private BASE_URL = 'http://localhost:8080/api/v1/employee';

  constructor(private http: HttpClient) {}

  public getAll() {
    return this.http.get(this.BASE_URL);
  }
}
