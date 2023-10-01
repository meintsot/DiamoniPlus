import {
  Component,
  OnInit,
  OnDestroy,
  ElementRef,
  ViewChild,
  Input,
  AfterViewInit,
  OnChanges
} from '@angular/core';
import { DiscussionsService } from '../../services/discussions.service';
import { ProfileService } from '../../services/profile.service'
import { MessageType, RetrieveMessagesRespMsgType } from '../../model';
import {Subject, takeUntil} from 'rxjs';
import { webSocket, WebSocketSubject } from 'rxjs/webSocket';
import {AuthService} from "../../services/auth.service";
import {Utils} from "../../Utils";
import {InfoService} from "../../services/info.service";

@Component({
  selector: 'app-discussion',
  templateUrl: './discussion.component.html',
  styleUrls: ['./discussion.component.css']
})
export class DiscussionComponent implements OnInit, AfterViewInit, OnChanges, OnDestroy {

  @ViewChild('chatContainer') private chatContainer!: ElementRef;
  @Input() selectedDiscussion!: string;
  @Input() selectedUser!: string;

  messages: MessageType[] = [];
  isLoading = false;
  hasMoreMessages = true;
  myUsername!: string;
  myAvatar!: string;
  myLabel!: string;
  otherAvatar!: string;
  otherLabel!: string;
  newMessage!: string;
  displayDialog = false;
  selectedMessage!: MessageType;
  showDeleteIcon = false;
  isHost = false;

  private pageNumber = 1;
  private pageSize = 10; // Adjust as needed
  private destroy = new Subject<void>();
  private socket!: WebSocketSubject<MessageType>;

  constructor(
    private discussionsService: DiscussionsService,
    private profileService: ProfileService,
    private authService: AuthService,
    private infoService: InfoService
  ) {}

  ngOnInit() {
    this.init();
  }

  ngOnChanges() {
    this.init();
    this.chatContainer.nativeElement.addEventListener('scroll', this.onScroll.bind(this));
  }

  init() {
    this.isLoading = false;
    this.hasMoreMessages = true;
    this.destroy.next();
    this.destroy = new Subject<void>();
    this.messages = [];
    this.pageNumber = 1;
    if (this.socket) {
      this.socket.complete();
    }

    this.profileService.retrieveUserProfileImage(this.selectedUser).pipe(takeUntil(this.destroy)).subscribe(res => {
      if (!Utils.isNullOrUndefined(res.avatar)) {
        this.otherAvatar = Utils.createDataUrl(res.avatar);
      } else {
        this.otherLabel = this.selectedUser.slice(0, 1);
      }
    })
    this.authService.userInfo$.pipe(takeUntil(this.destroy)).subscribe(userInfo => {
      this.myUsername = userInfo.username;
      this.isHost = userInfo.role === 'host';
      this.profileService.retrieveUserProfileImage(this.myUsername).pipe(takeUntil(this.destroy))
        .subscribe(resp => {
          if (!Utils.isNullOrUndefined(resp.avatar)) {
            this.myAvatar = Utils.createDataUrl(resp.avatar);
          } else {
            this.myLabel = userInfo.username.slice(0, 1);
          }
        });
    });
    this.loadMessages();
    this.initializeWebSocket();
  }

  ngAfterViewInit() {
    this.chatContainer.nativeElement.addEventListener('scroll', this.onScroll.bind(this));
  }

  ngOnDestroy() {
    this.destroy.next();
    this.destroy.complete();
    this.socket.complete();
  }

  confirmDeleteMessage(message: MessageType) {
    this.selectedMessage = message;
    this.displayDialog = true;
  }

  deleteMessage(message: MessageType) {
    this.displayDialog = false;
    message.deleted = true;
    this.discussionsService.deleteMessage(message.messageId as string).pipe(takeUntil(this.destroy)).subscribe(() => {
      this.infoService.sendSuccess(`Message with id ${message.messageId} has been deleted!`);
    });
  }

  loadMessages() {
    this.isLoading = true;
    this.discussionsService.retrieveMessages({
      discussionReference: this.selectedDiscussion,
      page: this.pageNumber,
      pageSize: this.pageSize
    })
      .subscribe(
        (res: RetrieveMessagesRespMsgType) => {
          this.messages = res.messages.reverse();
          this.isLoading = false;
          if (res.messages.length < this.pageSize) {
            this.hasMoreMessages = false;
          } else {
            this.pageNumber++;
          }
        }
      );
  }


  initializeWebSocket() {
    this.socket = webSocket(`wss://localhost/discussions/messages/${this.selectedDiscussion}`);
    this.socket.subscribe(
      message => this.handleIncomingMessage(message)
    );
  }

  handleIncomingMessage(message: MessageType) {
    this.messages.push(message);
  }

  onScroll(event: any): void {
    if (this.hasMoreMessages && event.target.scrollTop === 0) {
      this.loadMoreMessages();
    }
  }

  sendMessage() {
    if (this.newMessage.trim() !== '') {
      const messageToSend: MessageType = {
        sender: this.myUsername,
        receiver: this.selectedUser,
        messageText: this.newMessage.trim(),
        deleted: false,
        createdAt: new Date().toISOString()
      };
      this.discussionsService.saveMessage({
        message: messageToSend, discussionReference: this.selectedDiscussion
      }).pipe(takeUntil(this.destroy)).subscribe(() => {
        this.socket.next(messageToSend);
        this.newMessage = '';
        this.scrollToBottom();
      });
    }
  }

  scrollToBottom(): void {
    try {
      this.chatContainer.nativeElement.scrollTop = this.chatContainer.nativeElement.scrollHeight;
    } catch (err) {}
  }

  loadMoreMessages() {
    this.isLoading = true;
    this.discussionsService.retrieveMessages({
      discussionReference: this.selectedDiscussion,
      page: this.pageNumber,
      pageSize: this.pageSize
    }).subscribe(res => {
        this.messages = res.messages.reverse().concat(this.messages);
        if (res.messages.length < this.pageSize) {
          this.hasMoreMessages = false; // Set flag to false if no more messages
        } else {
          this.pageNumber++;
        }
        this.isLoading = false;
      });
  }
}
