package com.devok.websocket.resources.encoders;

import com.devok.websocket.resources.model.Message;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
public class MessageDecoder implements Decoder.Text<Message> {

    @Override
    public Message decode(String jsonMessage)  {

        JsonObject jsonObject = javax.json.Json
                .createReader(new StringReader(jsonMessage)).readObject();
        Message message = new Message();
        message.setUsername(jsonObject.getString("username"));
        message.setMessage(jsonObject.getString("message"));
        return message;

    }

    @Override
    public boolean willDecode(String jsonMessage) {
        try {
            // Check if incoming message is valid JSON
            Json.createReader(new StringReader(jsonMessage)).readObject();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("Initializing message decoder");
    }

    @Override
    public void destroy() {
        System.out.println("Destroyed message decoder");
    }

}