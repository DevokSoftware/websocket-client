package com.devok.websocket.resources;


import com.devok.websocket.resources.encoders.MessageDecoder;
import com.devok.websocket.resources.encoders.MessageEncoder;
import com.devok.websocket.resources.model.Message;
import com.devok.websocket.resources.service.ChatSessionController;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/message/{username}",
        encoders = {MessageEncoder.class},
        decoders = {MessageDecoder.class})
public class MessageServer {
    private static Set<Session> chatters = new CopyOnWriteArraySet<>();

    @Inject
    ChatSessionController chatSessionController;

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException, EncodeException {
        System.out.println("Opening Session");
        Map<String, String> chatUsers = chatSessionController.getUsers();
        chatUsers.put(session.getId(), username);
        chatSessionController.setUsers(chatUsers);
        chatters.add(session);
    }

    @OnMessage
    public void messageReceiver(Session session,
                                Message message) throws IOException, EncodeException {
        System.out.println("Received Message");
        Map<String, String> chatUsers = chatSessionController.getUsers();
        message.setUsername(chatUsers.get(session.getId()));
        message.setTimestamp(String.valueOf(Instant.now().getEpochSecond()));
        broadcast(message);
    }

    @OnClose
    public void onClose(Session session) throws IOException {
        System.out.println("Closing Session");
        chatters.remove(session);
        Message message = new Message();
        Map<String, String> chatUsers = chatSessionController.getUsers();
        String chatUser = chatUsers.get(session.getId());
        message.setUsername(chatUser);
        chatUsers.remove(chatUser);
        message.setMessage("Disconnected from server");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("There has been an error with session " + session.getId());
        throwable.printStackTrace();
    }

    private static void broadcast(Message message)
            throws IOException, EncodeException {

        chatters.forEach(session -> {
            synchronized (session) {
                try {
                    session.getBasicRemote().
                            sendObject(message);
                } catch (IOException | EncodeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
