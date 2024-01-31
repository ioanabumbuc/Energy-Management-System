import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserListComponent} from "./user-list/user-list.component";
import {HomeComponent} from "./home/home.component";
import {UserFormComponent} from "./user-list/user-form/user-form.component";
import {DevicesComponent} from "./devices/devices.component";
import {DeviceFormComponent} from "./devices/device-form/device-form.component";
import {LoginComponent} from "./login/login.component";
import {
  DeviceConsumptionDetailsComponent
} from "./devices/device-consumption-details/device-consumption-details.component";
import {ChatComponent} from "./chat/chat.component";

const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'users', component: UserListComponent},
  {path: 'devices', component: DevicesComponent},
  {path: 'devices/details/:id', component: DeviceConsumptionDetailsComponent},
  {path: 'users/add', component: UserFormComponent},
  {path: 'users/edit/:id', component: UserFormComponent},
  {path: 'devices/add', component: DeviceFormComponent},
  {path: 'devices/edit/:id', component: DeviceFormComponent},
  {path: 'login', component: LoginComponent},
  {path: 'chat', component: ChatComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
