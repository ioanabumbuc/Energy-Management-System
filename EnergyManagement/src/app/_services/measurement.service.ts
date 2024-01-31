import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Measurement} from "../_models/Model";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class MeasurementService {
  url = 'http://localhost:8082/measurements';

  constructor(private http: HttpClient) {
  }

  private getHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  public getMeasurements(deviceId: number): Observable<Measurement[]> {
    return this.http.get<Measurement[]>(this.url + `/${deviceId}`, {headers: this.getHeaders()});
  }

  public getMeasurementsByDate(deviceId: number, date: string): Observable<Measurement[]> {
    const request = {
      deviceId,
      date
    }
    return this.http.post<Measurement[]>(this.url + "/date", request, {headers: this.getHeaders()});
  }
}
