import {Injectable} from "@angular/core";
// @ts-ignore
import * as SockJS from 'sockjs-client/dist/sockjs';
import * as Stomp from 'stompjs'
import {Subject} from "rxjs";
import {Message, Status} from "../_models/Model";

@Injectable({
  providedIn: 'root'
})
export class ChatService {

  constructor() {
  }

  private socket: any;
  private stompClient: any;
  private messageSubject: Subject<Message> = new Subject();
  private typingSubject: Subject<string> = new Subject();
  private seenSubject: Subject<string> = new Subject();

  connect(userId: string): void {
    // Create a SockJS instance
    this.socket = new SockJS('http://localhost:8083/chat');

    //Create a Stomp client instance
    this.stompClient = Stomp.over(this.socket);

    // Connect to the WebSocket
    this.stompClient.connect(
      {},
      (frame: any) => {
        // Subscribe to private messages
        this.stompClient.subscribe(`/user/${userId}/private`, (message: any) => {
          console.log('Private message: ', message.body);
          const messageObj: Message = JSON.parse(message.body);
          if (messageObj.status === Status.MESSAGE) {
            this.messageSubject.next(messageObj);
            return;
          }
          if (messageObj.status === Status.SEEN) {
            this.seenSubject.next(messageObj.senderId);
            return;
          }

          this.typingSubject.next(messageObj.senderId);

        });
      },
      (error: any) => {
        console.error('Error connecting to WebSocket:', error);
      }
    );

  }

  disconnect(): void {
    if (this.stompClient) {
      this.stompClient.disconnect();
    }
  }

  sendMessage(message: Message): void {
    const destination = `/private-message`;
    this.stompClient.send(`/app${destination}`, {}, JSON.stringify(message));
  }

  getMessageSubject(): Subject<Message> {
    return this.messageSubject;
  }

  getSeenSubject(): Subject<string>{
    return this.seenSubject;
  }

  getTypingSubject(): Subject<string>{
    return this.typingSubject;
  }

}
