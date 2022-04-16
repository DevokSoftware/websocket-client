package com.devok.websocket.resources.encoders;

import com.devok.websocket.resources.model.Message;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class MessageEncoder implements Encoder.Text<Message> {

    @Override
    public String encode(Message message) {

        JsonObject jsonObject = Json.createObjectBuilder()
                .add("username", message.getUsername())
                .add("message", message.getMessage()).build();
        return jsonObject.toString();

    }

    @Override
    public void init(EndpointConfig ec) {
        System.out.println("Initializing message encoder");
    }

    @Override
    public void destroy() {
        System.out.println("Destroying encoder...");
    }
}