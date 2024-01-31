import {Component} from '@angular/core';
import {Role} from "../_models/Model";

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent {

  role = Role.NONE;

  ROLES = Role;


  constructor() {
  }

  ngOnInit() {
    const role = sessionStorage.getItem("role");
    if (!role) {
      return;
    }
    if (role === Role.CLIENT) {
      this.role = Role.CLIENT;
    } else if (role === Role.ADMIN) {
      this.role = Role.ADMIN;
    }
  }
}
