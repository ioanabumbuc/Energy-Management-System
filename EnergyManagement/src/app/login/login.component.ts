import {Component} from '@angular/core';
import {Role, LoginResponse, User} from "../_models/Model";
import {UserService} from "../_services/users.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  user: User = {
    name: '',
    password: '',
    role: Role.CLIENT
  }

  error = false;

  constructor(private userService: UserService,
              private router: Router) {
  }

  login() {
    this.error = false;
    this.userService.login(this.user).subscribe({
      next: (resp: LoginResponse) => {
        sessionStorage.setItem("token", resp.token);
        sessionStorage.setItem("role", resp.role);
        sessionStorage.setItem("userId", String(resp.userId));

        this.router.navigate(['/']).then(() => location.reload());
      },
      error: (err) => {
        console.error(err);
        this.error = true;
      }
    })
  }

}
