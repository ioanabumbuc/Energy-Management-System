import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {Device} from "../_models/Model";

@Injectable({
  providedIn: 'root'
})
export class DeviceService {
  url: string = "http://localhost:8081/devices"

  constructor(private http: HttpClient) {
  }

  private getHeaders(): HttpHeaders {
    const token = sessionStorage.getItem('token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  public getDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.url + "/all", {headers: this.getHeaders()});
  }

  public getDevicesByUserId(id: number): Observable<Device[]> {
    return this.http.get<Device[]>(this.url + `/user=${id}`, {headers: this.getHeaders()});
  }

  public getDeviceDetails(id: number): Observable<Device> {
    return this.http.get<Device>(this.url + `/${id}`, {headers: this.getHeaders()});
  }

  public updateDevice(device: Device): Observable<Device> {
    return this.http.put<Device>(this.url + `/update/${device.id}`, device,
      {headers: this.getHeaders()});
  }


  public addDevice(device: Device): Observable<any> {
    const request = {
      description: device.description,
      address: device.address,
      maxConsumption: device.maxConsumption,
      userId: device.userId
    };
    return this.http.post<Device>(this.url + '/add', request, {headers: this.getHeaders()});
  }

  public deleteDevice(deviceId: number): Observable<any> {
    return this.http.delete(this.url + `/delete/${deviceId}`, {headers: this.getHeaders()});
  }
}
