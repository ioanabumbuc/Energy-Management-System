import {Component} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Device, Role} from "../../_models/Model";
import {DeviceService} from "../../_services/device.service";
import {UserService} from "../../_services/users.service";

@Component({
  selector: 'app-device-form',
  templateUrl: './device-form.component.html',
  styleUrls: ['./device-form.component.scss']
})
export class DeviceFormComponent {
  id: number = -1;

  isEditing = false;
  success = false;
  error = false;
  showPage = true;

  device: Device = {
    description: '',
    address: '',
    maxConsumption: 0,
    userId: 0
  };
  userIds: number[] = [];

  constructor(private route: ActivatedRoute,
              private deviceService: DeviceService,
              private userService: UserService) {

  }

  ngOnInit() {
    this.showPage = this.isAllowed();
    if(!this.showPage) {
      return;
    }

    this.getUserIds();

    this.id = Number(this.route.snapshot.paramMap.get('id'));
    if (this.id) {
      this.device.id = this.id;
      this.isEditing = true;
      this.getDeviceDetails();
    }
  }

  isAllowed() {
    const role = sessionStorage.getItem("role");
    return role === Role.ADMIN;
  }


  getDeviceDetails() {
    this.deviceService.getDeviceDetails(this.id).subscribe({
      next: (device) => {
        this.device = device;
      }
    })
  }

  getUserIds(){
    this.userService.getUsers().subscribe({
      next:(users) => {
        this.userIds = users.map(user => user.id ?? 0);
      }
    })
  }

  submit() {
    this.success = false;
    this.error = false;

    if (this.isEditing) {
      this.deviceService.updateDevice(this.device).subscribe({
        next: (device) => {
          this.success = true;
        },
        error: (err) => {
          console.error(err);
          this.error = true;
        }
      })
      return;
    }

    this.deviceService.addDevice(this.device).subscribe({
      next: (device) => {
        this.success = true;
      },
      error: (err) => {
        console.error(err);
        this.error = true;
      }
    })
  }
}
