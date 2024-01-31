import {Component} from '@angular/core';
import {Message, Role, Status, StrippedClient, StrippedMessage, UserMessagePair} from "../_models/Model";
import {ChatService} from "../_services/chat.service";
import {UserService} from "../_services/users.service";

@Component({
  selector: 'app-chat',
  templateUrl: './chat.component.html',
  styleUrls: ['./chat.component.scss']
})
export class ChatComponent {
  role = Role.NONE;
  userId: string;
  showPage = true;
  newMessage = '';
  activeClientId = ''
  isAdminTyping = false;

  clients: StrippedClient[] = [];
  messages: UserMessagePair = {};

  ROLES = Role;

  constructor(private chatService: ChatService, private userService: UserService) {
    this.userId = sessionStorage.getItem("userId") ?? '';

    const role = sessionStorage.getItem("role");
    if (role === Role.CLIENT) {
      this.role = Role.CLIENT;
      this.activeClientId = '1';
      this.messages['1'] = [];
    } else if (role === Role.ADMIN) {
      this.role = Role.ADMIN;

      this.userService.getUsers().subscribe((users) => {
        this.clients = users
          .filter(user => user.role === Role.CLIENT)
          .map(user => ({
            id: user.id?.toLocaleString() ?? '-1',
            name: user.name,
            isTyping: false
          }));

        this.clients.map(client => this.messages[client.id] = []);
        this.activeClientId = this.clients[0].id;
      })
    } else {
      return;
    }
    this.showPage = true;

    this.chatService.connect(this.userId);

    this.chatService.getMessageSubject().subscribe(resp => {
      const strippedMessage: StrippedMessage = {
        message: resp.message,
        isReceived: true
      }
      if (this.role === Role.CLIENT ||
        this.role === Role.ADMIN && this.activeClientId === resp.senderId) {
        this.sendSeenMessage();
      }
      this.messages[resp.senderId].push(strippedMessage);
    })

    this.chatService.getSeenSubject().subscribe((userId) => {
      for (let i = 0; i < this.messages[userId].length; i++) {
        this.messages[userId][i].seen = true;
      }
    })

    this.chatService.getTypingSubject().subscribe((userId) => {
      if(this.role === Role.CLIENT && userId === "1"){
        this.isAdminTyping = true;
        setTimeout(() => {
          this.isAdminTyping = false;
        }, 3000)
        return;
      }
      let index = 0;
      for (let i = 0; i < this.clients.length; i++) {
        if (this.clients[i].id !== userId) {
          continue;
        }
        index = i;
        this.clients[i].isTyping = true;
        break;
      }
      setTimeout(() => {
        this.clients[index].isTyping = false;
      }, 3000)
    })
  }

  ngOnDestroy() {
    this.chatService.disconnect();
  }

  setActiveClient(id: string) {
    this.activeClientId = id;
    this.sendSeenMessage();
  }

  sendMessage() {
    const message: Message = {
      senderId: this.userId,
      receiverId: this.activeClientId,
      message: this.newMessage,
      status: Status.MESSAGE
    }

    this.messages[this.activeClientId].push({
      message: this.newMessage,
      isReceived: false
    })

    this.chatService.sendMessage(message);
    this.newMessage = '';
  }

  sendSeenMessage() {
    const message: Message = {
      senderId: this.userId,
      receiverId: this.activeClientId,
      message: '',
      status: Status.SEEN
    }

    this.chatService.sendMessage(message);
  }

  sendTypingMessage() {
    const message: Message = {
      senderId: this.userId,
      receiverId: this.activeClientId,
      message: '',
      status: Status.TYPING
    }

    this.chatService.sendMessage(message);
  }

}
