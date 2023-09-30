import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject, takeUntil, tap} from "rxjs";
import {AbstractControl, FormArray, FormBuilder, FormGroup} from "@angular/forms";
import {DiscussionsService} from "../services/discussions.service";
import {ProfileService} from "../services/profile.service";
import {ActivatedRoute} from "@angular/router";
import {DiscussionType} from "../model";

@Component({
  selector: 'app-discussions',
  templateUrl: './discussions.component.html',
  styleUrls: ['./discussions.component.css']
})
export class DiscussionsComponent implements OnInit, OnDestroy {
  private destroy = new Subject<void>();
  discussionImages: string[] = [];
  discussionForm!: FormArray;
  selectedDiscussion!: string;
  selectedUser!: string;

  constructor(
    private discussionsService: DiscussionsService,
    private profileService: ProfileService,
    private route: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.discussionsService.retrieveDiscussions().pipe(
      takeUntil(this.destroy),
      tap(res => {
        const usernames = res.discussions.map(discussion => discussion.username);
        this.profileService.retrieveUserProfileImages(usernames).pipe(takeUntil(this.destroy)).subscribe(images=> {
          this.discussionImages = images;
        });
      })
    ).subscribe(res => {
      this.patchDiscussionsForm(res.discussions);
    });
  }

  selectDiscussion(discussion: AbstractControl<any>) {
    const discussionForm = (discussion as FormGroup);
    this.selectedDiscussion = discussionForm.value.discussionReference;
  }

  ngOnDestroy() {
    this.destroy.next();
    this.destroy.complete();
  }

  private patchDiscussionsForm(discussions: DiscussionType[]) {
    this.discussionForm = this.fb.array([]);
    discussions.forEach((discussion, index) => {
      const discussionForm = this.fb.group({
        username: [discussion.username],
        discussionReference: [discussion.discussionReference],
        isSelected: [index === 0]
      });
      this.discussionForm.push(discussionForm);
    });
    this.discussionForm.valueChanges.subscribe(value => {
      alert('test')
    });
    this.selectedDiscussion = this.discussionForm.value[0]?.discussionReference;
    this.selectedUser = this.discussionForm.value[0]?.username;
  }

  protected readonly FormGroup = FormGroup;
}
