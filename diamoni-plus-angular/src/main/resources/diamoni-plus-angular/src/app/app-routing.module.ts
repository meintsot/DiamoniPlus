import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AuthGuard} from './services/auth.guard';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {LogoutGuard} from "./services/logout.guard";

const routes: Routes = [
  {
    path: 'login',
    component: LoginComponent // This route is not protected
  },
  {
    path: 'register',
    component: RegisterComponent // This route is not protected
  },
  {
    path: 'logout',
    canActivate: [LogoutGuard],
    component: LoginComponent
  }
  // {
  //   path: 'protected-route',
  //   component: ProtectedComponent,
  //   canActivate: [AuthGuard] // This route is protected
  // },
  // ...other routes
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
