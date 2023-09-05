import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {ShellComponent} from "./shell/shell.component";
import {MenubarModule} from "primeng/menubar";
import {ButtonModule} from "primeng/button";
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
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

@NgModule({
  declarations: [
    AppComponent,
    ShellComponent,
    LoginComponent,
    RegisterComponent,
  ],
  imports: [
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
    DropdownModule
  ],
  providers: [
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
