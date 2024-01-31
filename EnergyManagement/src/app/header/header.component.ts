import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {Role} from "../_models/Model";
import {NotificationService} from "../_services/notification.service";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent {
  openNotifications = false;
  notifications: string[] = [];
  role = Role.NONE;
  showNotifications = false;

  ROLES = Role;

  constructor(private router: Router, private webSocketService: NotificationService) {
    const role = sessionStorage.getItem("role");
    const userId = sessionStorage.getItem("userId");
    if (role === Role.CLIENT) {
      this.role = Role.CLIENT;
    }
    if (role === Role.ADMIN) {
      this.role = Role.ADMIN;
    }

    if (!role || !userId) {
      return;
    }

    this.showNotifications = true;
    // this.webSocketService.connect(userId);
    // this.webSocketService.getMessage().subscribe({
    //   next: (message: string) => {
    //     this.notifications.push(message);
    //   }
    // })
  }

  goto(path: string) {
    this.router.navigate([path]).then();
  }

  logout() {
    this.role = Role.NONE;
    sessionStorage.removeItem("role");
    sessionStorage.removeItem("userId");
    this.goto('login');
  }

  clearNotifications(){
    this.notifications = [];
    this.openNotifications = false;
  }
}
