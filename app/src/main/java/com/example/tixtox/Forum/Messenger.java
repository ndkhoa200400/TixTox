package com.example.tixtox.Forum;

import java.util.Date;

public class Messenger {
    private String messageText;
    private String messageUser;
    private long messageTime;
    private String userID;

    public Messenger(String messageText, String messageUser, String userID) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.userID = userID;
        messageTime = new Date().getTime();
    }
    public Messenger(){

    }

    public String getMessageText() {
        return messageText;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }

}
