import {Component, OnDestroy, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import {AuthService} from "../services/auth.service";
import {Router} from "@angular/router";
import {catchError, EMPTY, Subject, switchMap, takeUntil} from "rxjs";
import {ProfileService} from "../services/profile.service";
import {GetUserProfileRespMsgType, ImageFileType} from "../model";
import {Utils} from "../Utils";
import {InfoService} from "../services/info.service";

@Component({
  selector: 'app-edit-profile',
  templateUrl: './edit-profile.component.html',
  styleUrls: ['./edit-profile.component.css']
})
export class EditProfileComponent implements OnInit, OnDestroy {
  editProfileForm!: FormGroup;
  profile!: GetUserProfileRespMsgType;
  avatar!: string;
  avatarChanged = false;
  roles!: any[];
  isLoading = true;

  private destroy = new Subject<void>();

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private authService: AuthService,
    private profileService: ProfileService,
    private infoService: InfoService
  ) {}

  ngOnInit() {
    this.initForm();
    this.initRoles();
    this.authService.userInfo$.pipe(
      takeUntil(this.destroy),
      switchMap(userInfo => {
        return this.profileService.getUserProfile(userInfo.username)
      }),
      catchError(err => {
        this.isLoading = false;
        return EMPTY;
      })
    ).subscribe(profile => {
      this.profile = profile;
      this.isLoading = false;
      this.avatar = Utils.createDataUrl(profile.avatar);
      this.patchUserProfile();
    });
  }

  editProfile() {
    this.profileService.updateUserProfile({
      ...this.editProfileForm.value,
      avatar: this.profile.avatar
    }).pipe(takeUntil(this.destroy)).subscribe(() => {
      this.authService.retrieveUserInfo().pipe(takeUntil(this.destroy)).subscribe(res => {
        this.router.navigate(['/']);
        this.infoService.sendSuccess('Successfully updated your profile!');
      });
    });
  }

  isFormTheSame() {
    return Utils.areObjectsEqual(this.editProfileForm.value, this.profile) && !this.avatarChanged;
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
        this.profile.avatar = {
          data: Utils.removeDataURL(dataURL),
          filename,
          mime
        }
        this.avatar = dataURL;
        this.avatarChanged = true;
      };
    }
  }

  ngOnDestroy() {
    this.destroy.next();
    this.destroy.complete();
  }

  private initRoles() {
    this.roles = [
      { label: 'Host', value: 'host' },
      { label: 'Tenant', value: 'tenant' },
      { label: 'Admin', value: 'admin' },
    ];
  }

  private patchUserProfile() {
    this.editProfileForm.patchValue(this.profile);
  }

  private initForm() {
    this.editProfileForm = this.fb.group({
      firstName: [null, Validators.required],
      lastName: [null, Validators.required],
      username: [{value: null, disabled: true}, Validators.required],
      email: [{value: null, disabled: true}, Validators.required],
      desiredRole: [{value: null, disabled: true}, Validators.required],
      phone: [null, Validators.required]
    });
  }
}
