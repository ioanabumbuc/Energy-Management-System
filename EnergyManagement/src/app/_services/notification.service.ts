import {Injectable} from '@angular/core';
import {Observable, Subject} from "rxjs";

// @ts-ignore
import * as SockJS from 'sockjs-client/dist/sockjs';
import * as Stomp from 'stompjs'

@Injectable({
  providedIn: 'root'
})
export class NotificationService {

  constructor() {
  }

  private socket: any;
  private stompClient: any;
  private messageSubject: Subject<any> = new Subject<any>();

  connect(userId: string): void {
    // Create a SockJS instance
    this.socket = new SockJS('http://localhost:8082/user');

    //Create a Stomp client instance
    this.stompClient = Stomp.over(this.socket);

    // Connect to the WebSocket
    this.stompClient.connect({}, (frame: any) => {
      // Subscribe to a destination
      this.stompClient.subscribe(`/notifications/${userId}`, (message: any) => {
        console.log("message: ",message.body);
        this.messageSubject.next(message.body);
      });
    });
  }

  sendMessage(destination: string, message: any): void {
    // Send a message to the specified destination
    this.stompClient.send(destination, {}, JSON.stringify(message));
  }

  getMessage(): Observable<any> {
    return this.messageSubject.asObservable();
  }

  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }

}

