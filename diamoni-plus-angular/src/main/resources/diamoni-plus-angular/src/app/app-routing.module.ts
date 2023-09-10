import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthGuard} from './services/auth.guard';
import {LoginComponent} from "./login/login.component";
import {RegisterComponent} from "./register/register.component";
import {LogoutGuard} from "./services/logout.guard";
import {MyRentalSpacesComponent} from "./my-rental-spaces/my-rental-spaces.component";
import {SubmitRentalSpaceComponent} from "./submit-rental-space/submit-rental-space.component";
import {SearchRentalSpacesComponent} from "./search-rental-spaces/search-rental-spaces.component";
import {RentalSpaceDetailsComponent} from "./rental-space-details/rental-space-details.component";
import {MyBookingsComponent} from "./my-bookings/my-bookings.component";

const routes: Routes = [
  {
    path: '',
    component: SearchRentalSpacesComponent
  },
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
  },
  {
    path: 'my-rental-spaces',
    component: MyRentalSpacesComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'submit-rental-space',
    component: SubmitRentalSpaceComponent,
    canActivate: [AuthGuard]
  },
  {
    path: 'rental-space-details',
    component: RentalSpaceDetailsComponent
  },
  {
    path: 'my-bookings',
    component: MyBookingsComponent,
    canActivate: [AuthGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
