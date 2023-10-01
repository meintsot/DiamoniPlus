import {Component, OnInit} from '@angular/core';
import { AuthService } from '../services/auth.service';
import {ImageFileType, RegisterReqMsgType} from '../model';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Utils} from "../Utils";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  avatar!: ImageFileType;
  roles!: any[];

  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.initRoles();
    this.initForm();
  }

  initRoles() {
    this.roles = [
      { label: 'Host', value: 'host' },
      { label: 'Tenant', value: 'tenant' }
    ];
  }

  initForm() {
    this.registerForm = this.fb.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required],
      passwordConfirmation: ['', Validators.required],
      phone: ['', Validators.required],  // Change Validators.email to Validators.required
      desiredRole: [null, Validators.required]
    });
  }

  onUpload(event: Event): void {
    const reader = new FileReader();
    if (event.target && (event.target as HTMLInputElement).files && (event.target as HTMLInputElement).files!.length > 0) {
      const file = (event.target as HTMLInputElement).files![0];
      const mime = file.type; // Get the MIME type
      const filename = file.name; // Get the filename
      reader.readAsDataURL(file);

      reader.onload = () => {
        const dataURL = reader.result as string;
        this.avatar = {
          data: dataURL,
          filename,
          mime
        }
      };
    }
  }

  register() {
    const payload = (this.registerForm.value as RegisterReqMsgType);
    payload.avatar = this.avatar;
    if (payload.avatar) {
      payload.avatar.data = Utils.removeDataURL(payload.avatar.data.slice());
    }
    this.authService.register(payload);
  }
}
