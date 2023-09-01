import {ChangeDetectorRef, Component, OnInit} from '@angular/core';
import { AuthService } from '../services/auth.service';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {LoginReqMsgType} from "../model";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;

  constructor(
    private authService: AuthService,
    private formBuilder: FormBuilder,
    private changeDetectorRef: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login() {
    this.authService.login((this.loginForm.value as LoginReqMsgType));
    this.changeDetectorRef.detectChanges();
  }
}
