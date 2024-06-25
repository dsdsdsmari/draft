package com.example.draft;

public class Conversation {
    private String userEmail;
    private String lastMessage;
    private long timestamp;

    public Conversation(String userEmail, String lastMessage, long timestamp) {
        this.userEmail = userEmail;
        this.lastMessage = lastMessage;
        this.timestamp = timestamp;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
