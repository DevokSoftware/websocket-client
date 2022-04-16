import { Component } from '@angular/core';
import { WebsocketService } from './service/websocket-service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  content = '';
  received: any = [];
  sent: any = [];

  constructor(private WebsocketService: WebsocketService) {
    WebsocketService.messages.subscribe(msg => {
      this.received.push(msg);
      console.log("Response from websocket: " + msg);
    });
  }

  sendMsg() {
    let message = {
      username: '',
      message: ''
    };
    message.username = 'localhost';
    message.message = this.content;

    this.sent.push(message);
    this.WebsocketService.messages.next(message);
  }
}
