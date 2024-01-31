import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {LoginResponse, User} from "../_models/Model";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  url: string = "http://localhost:8080/users"

  constructor(private http: HttpClient) {
  }

  private getHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  public getUsers(): Observable<User[]> {
    return this.http.get<User[]>(this.url + '/all', { headers: this.getHeaders() });
  }

  public getUserDetails(id: number): Observable<User> {
    return this.http.get<User>(this.url + `/${id}`, { headers: this.getHeaders() });
  }

  public updateUser(user: User): Observable<User> {
    return this.http.put<User>(this.url + `/update/${user.id}`, user, { headers: this.getHeaders() });
  }

  public deleteUser(userId: number): Observable<any> {
    return this.http.delete(this.url + `/delete/${userId}`, { headers: this.getHeaders() });
  }

  public addUser(user: User): Observable<any> {
    const request = {
      name: user.name,
      password: user.password,
      role: user.role
    };
    return this.http.post<User>(this.url + '/add', request, { headers: this.getHeaders() });
  }

  public login(user:User): Observable<LoginResponse>{
    const request = {
      name: user.name,
      password: user.password,
    };
    return this.http.post<LoginResponse>(this.url + '/login', request);
  }

  public addUserId(userId: number): Observable<any> {
    const url = "http://localhost:8081/users/add"
    const request = {
      userId
    }
    return this.http.post<User>(url, request, {headers: this.getHeaders()});
  }

  public deleteUserId(userId: number): Observable<any>{
    const url = `http://localhost:8081/users/delete/${userId}`;
    return this.http.delete<User>(url, {headers: this.getHeaders()});
  }

}
