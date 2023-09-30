package com.tedi.websocket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint("/discussions/messages/{discussionReference}")
@ApplicationScoped
public class DiscussionsWebsocket {

    private static final Map<String, ConcurrentHashMap<String, Session>> groupSessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("discussionReference") String discussionReference) {
        System.out.println("Session opened, ID = " + session.getId());
        groupSessions
                .computeIfAbsent(discussionReference, s -> new ConcurrentHashMap<>())
                .put(session.getId(), session);
    }

    @OnClose
    public void onClose(Session session, @PathParam("discussionReference") String discussionReference) {
        System.out.println("Session closed, ID = " + session.getId());
        ConcurrentHashMap<String, Session> sessions = groupSessions.get(discussionReference);
        if (sessions != null) {
            sessions.remove(session.getId());
        }
    }

    @OnError
    public void onError(Session session, @PathParam("discussionReference") String discussionReference, Throwable throwable) {
        System.err.println("Session error, ID = " + session.getId());
        System.err.println(throwable.getMessage());
        ConcurrentHashMap<String, Session> sessions = groupSessions.get(discussionReference);
        if (sessions != null) {
            sessions.remove(session.getId());
        }
    }

    @OnMessage
    public void onMessage(String message, @PathParam("discussionReference") String discussionReference) {
        sendToGroup(discussionReference, message);
    }

    private void sendToGroup(String discussionReference, String message) {
        ConcurrentHashMap<String, Session> sessions = groupSessions.get(discussionReference);
        if (sessions != null) {
            for (Session s : sessions.values()) {
                if (s.isOpen()) {
                    s.getAsyncRemote().sendText(message);
                }
            }
        }
    }
}
