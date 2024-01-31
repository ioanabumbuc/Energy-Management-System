import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Role, User} from "../../_models/Model";
import {UserService} from "../../_services/users.service";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent {
  id: number = -1;

  isEditing = false;
  success = false;
  error = false;
  showPage = true;

  user: User = {
    name: '',
    password: '',
    role: Role.CLIENT
  };

  ROLES = Role;

  constructor(private route: ActivatedRoute,
              private userService: UserService) {

  }

  ngOnInit() {
    this.showPage = this.isAllowed();
    if(!this.showPage) {
      return;
    }
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.id) {
      this.user.id = this.id;
      this.isEditing = true;
      this.getUserDetails();
    }
  }

  isAllowed() {
    const role = sessionStorage.getItem("role");
    return role === Role.ADMIN;
  }

  getUserDetails() {
    this.userService.getUserDetails(this.id).subscribe({
      next: (user) => {
        this.user = user;
      }
    })
  }

  print() {
    console.log(this.user.role);
  }

  submit() {
    this.success = false;
    this.error = false;

    if (this.isEditing) {
      this.userService.updateUser(this.user).subscribe({
        next: (user) => {
          this.success = true;
        },
        error: (err) => {
          console.error(err);
          this.error = true;
        }
      })
      return;
    }

    this.userService.addUser(this.user).subscribe({
      next: (user) => {
        this.success = true;
        this.userService.addUserId(user.id).subscribe((resp)=>{
          console.log("User id added: ", resp.userId)
        })
      },
      error: (err) => {
        console.error(err);
        this.error = true;
      }
    })
  }
}
