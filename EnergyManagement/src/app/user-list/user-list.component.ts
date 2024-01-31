import {Component, OnInit} from '@angular/core';
import {UserService} from "../_services/users.service";
import {Role, User} from "../_models/Model";
import {Router} from "@angular/router";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {
  users: User[] = [];

  showPage = true;

  constructor(private userService: UserService,
              private router: Router) {

  }

  ngOnInit() {
    this.showPage = this.isAllowed();
    if (!this.showPage) {
      return;
    }
    this.getUsers()
  }

  isAllowed() {
    const role = sessionStorage.getItem("role");
    return role === Role.ADMIN;
  }

  getUsers() {
    this.userService.getUsers().subscribe({
      next: (users) => {
        this.users = users;
      },
      error: (err) => {
        console.error(err);
      }
    })
  }

  deleteUser(id: number | undefined) {
    if (!id) {
      return;
    }
    this.userService.deleteUser(id).subscribe({
      next: () => {
        this.getUsers();
        this.userService.deleteUserId(id).subscribe(() => {
          console.log("Deleted user id");
        })
      }
    });
  }

  editUser(id: number | undefined) {
    if (!id) {
      return;
    }
    this.router.navigate(['users/edit', id]).then();
  }

  addUser() {
    this.router.navigate(['users/add']).then();
  }
}
