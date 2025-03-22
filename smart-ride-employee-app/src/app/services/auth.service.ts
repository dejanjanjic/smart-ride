import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginDTO } from '../model/login.model';
import { Observable } from 'rxjs';
import { EmployeeSimple } from '../model/employee.model';
import { Role } from '../enum/role.enum';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private STORAGE_KEY = 'AUTH';
  private BASE_URL = 'http://localhost:8080/api/v1/auth';

  constructor(private http: HttpClient) {}

  public login(loginDTO: LoginDTO): Observable<any> {
    return this.http.post(`${this.BASE_URL}`, loginDTO);
  }
  public store(employee: EmployeeSimple) {
    localStorage.setItem(this.STORAGE_KEY, JSON.stringify(employee));
  }
  logout(): void {
    localStorage.removeItem(this.STORAGE_KEY);
  }

  isAuthenticated(): boolean {
    return !!localStorage.getItem(this.STORAGE_KEY);
  }

  getUserRole(): Role | null {
    const temp = localStorage.getItem(this.STORAGE_KEY);
    return temp ? (JSON.parse(temp) as EmployeeSimple).role : null;
  }
  getUsername(): string | null {
    const temp = localStorage.getItem(this.STORAGE_KEY);
    return temp ? (JSON.parse(temp) as EmployeeSimple).username : null;
  }
}
