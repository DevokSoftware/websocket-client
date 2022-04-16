import { Component } from '@angular/core';
import * as moment from 'moment';
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
      msg.timestamp = moment.unix(Number(msg.timestamp)).format("HH:mm")
      console.log("Response from websocket: " + msg);
    });
  }

  sendMsg() {
    let message = {
      username: '',
      message: '',
      timestamp: ''
    };
    message.message = this.content;
    this.content = '';

    this.sent.push(message);
    this.WebsocketService.messages.next(message);
  }
}
