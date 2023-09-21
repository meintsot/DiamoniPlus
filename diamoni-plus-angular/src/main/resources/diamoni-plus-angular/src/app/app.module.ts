import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ShellComponent} from "./shell/shell.component";
import {MenubarModule} from "primeng/menubar";
import {ButtonModule} from "primeng/button";
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {AvatarModule} from "primeng/avatar";
import {AuthInterceptor} from "./interceptors/auth.interceptor";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {ReactiveFormsModule} from "@angular/forms";
import {PasswordModule} from "primeng/password";
import {RadioButtonModule} from "primeng/radiobutton";
import {CardModule} from "primeng/card";
import {ChipsModule} from "primeng/chips";
import {FileUploadModule} from "primeng/fileupload";
import {DropdownModule} from "primeng/dropdown";
import {MyRentalSpacesComponent} from './my-rental-spaces/my-rental-spaces.component';
import {RentalSpaceResultsComponent} from './shared/rental-space-results/rental-space-results.component';
import {TableModule} from "primeng/table";
import {SubmitRentalSpaceComponent} from './submit-rental-space/submit-rental-space.component';
import {CheckboxModule} from "primeng/checkbox";
import {CalendarModule} from "primeng/calendar";
import {InputTextareaModule} from "primeng/inputtextarea";
import {PaginatorModule} from "primeng/paginator";
import {FieldsetModule} from "primeng/fieldset";
import {StepsModule} from "primeng/steps";
import {LeafletModule} from "@asymmetrik/ngx-leaflet";
import {CommonModule, DatePipe, NgOptimizedImage} from "@angular/common";
import {RatingModule} from "primeng/rating";
import {DataViewModule} from "primeng/dataview";
import {SearchRentalSpacesComponent} from './search-rental-spaces/search-rental-spaces.component';
import {DividerModule} from "primeng/divider";
import {TriStateCheckboxModule} from "primeng/tristatecheckbox";
import {RentalSpaceDetailsComponent} from './rental-space-details/rental-space-details.component';
import {CarouselModule} from "primeng/carousel";
import {SkeletonModule} from "primeng/skeleton";
import {StarRatingComponent} from './shared/star-rating/star-rating.component';
import {MyBookingsComponent} from './my-bookings/my-bookings.component';
import {DialogModule} from "primeng/dialog";
import {ListboxModule} from "primeng/listbox";
import {EditProfileComponent} from "./edit-profile/edit-profile.component";

@NgModule({
  declarations: [
    AppComponent,
    ShellComponent,
    LoginComponent,
    RegisterComponent,
    MyRentalSpacesComponent,
    RentalSpaceResultsComponent,
    SubmitRentalSpaceComponent,
    SearchRentalSpacesComponent,
    RentalSpaceDetailsComponent,
    StarRatingComponent,
    MyBookingsComponent,
    EditProfileComponent
  ],
  imports: [
    CommonModule,
    BrowserModule,
    AppRoutingModule,
    MenubarModule,
    ButtonModule,
    HttpClientModule,
    AvatarModule,
    ToastModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    PasswordModule,
    RadioButtonModule,
    CardModule,
    ChipsModule,
    FileUploadModule,
    DropdownModule,
    TableModule,
    CheckboxModule,
    CalendarModule,
    InputTextareaModule,
    PaginatorModule,
    FieldsetModule,
    StepsModule,
    LeafletModule,
    RatingModule,
    DataViewModule,
    DividerModule,
    TriStateCheckboxModule,
    CarouselModule,
    SkeletonModule,
    NgOptimizedImage,
    DialogModule,
    ListboxModule
  ],
  providers: [
    DatePipe,
    MessageService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
