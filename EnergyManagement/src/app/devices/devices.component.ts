import {Component} from '@angular/core';
import {Router} from "@angular/router";
import {DeviceService} from "../_services/device.service";
import {Device, Role} from "../_models/Model";


@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.scss']
})
export class DevicesComponent {
  role = Role.NONE;

  devices: Device[] = [];

  showPage = true;

  ROLES = Role;

  constructor(private deviceService: DeviceService,
              private router: Router) {

  }

  ngOnInit() {
    this.showPage = this.isAllowed();
    if (!this.showPage) {
      return;
    }

    this.getDevices();
  }

  isAllowed() {
    const role = sessionStorage.getItem("role");
    if (role === Role.CLIENT) {
      this.role = Role.CLIENT;
    }
    if (role === Role.ADMIN) {
      this.role = Role.ADMIN;
    }
    return !!role;
  }

  getDevices() {
    if (this.role === Role.ADMIN) {
      this.deviceService.getDevices().subscribe({
        next: (devices) => {
          this.devices = devices;
        },
        error: (err) => {
          console.error(err);
        }
      })
      return;
    }

    const userId = sessionStorage.getItem("userId");
    if (userId) {
      this.deviceService.getDevicesByUserId(Number(userId)).subscribe({
        next: (devices) => {
          this.devices = devices;
        },
        error: (err) => {
          console.error(err);
        }
      })
    }
  }

  deleteDevice(id: number | undefined) {
    if (!id) {
      return;
    }
    this.deviceService.deleteDevice(id).subscribe({
      next: () => {
        this.getDevices();
      }
    });
  }

  editDevice(id: number | undefined) {
    if (!id) {
      return;
    }
    this.router.navigate(['devices/edit', id]).then();
  }

  addDevice() {
    this.router.navigate(['devices/add']).then();
  }

  seeDetails(id: number | undefined) {
    if (!id) {
      return;
    }
    this.router.navigate(['devices/details', id]).then();
  }
}
