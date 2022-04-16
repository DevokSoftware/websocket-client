package com.devok.websocket.resources.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class ChatSessionController implements java.io.Serializable {

    private Map<String, String> users = null;

    public ChatSessionController() {
    }

    @PostConstruct
    public void init() {
        users = new HashMap<>();
    }

    /**
     * @return the users
     */
    public Map<String, String> getUsers() {
        return users;
    }

    /**
     * @param for the users
     */
    public void setUsers(Map<String, String> users) {
        this.users = users;
    }

}