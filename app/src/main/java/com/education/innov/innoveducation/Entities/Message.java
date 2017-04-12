package com.education.innov.innoveducation.Entities;

import java.security.Timestamp;

import co.intentservice.chatui.models.ChatMessage;

/**
 * Created by maher on 12/04/2017.
 */

public class Message extends ChatMessage{
    private String id;
    private String senderId;
    private String reciverId;

    public Message(String message, long timestamp, Type type, String senderId, String reciverId) {
        super(message, timestamp, type);
        this.senderId = senderId;
        this.reciverId = reciverId;
    }
    public Message(){
        super("hello",System.currentTimeMillis(),Type.RECEIVED);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReciverId() {
        return reciverId;
    }

    public void setReciverId(String reciverId) {
        this.reciverId = reciverId;
    }


}
