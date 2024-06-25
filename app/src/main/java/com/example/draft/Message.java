package com.example.draft;

public class Message {

    private String senderEmail;
    private String receiverEmail;
    private String message;
    private long timestamp;

    public Message() {
        // Default constructor required for Firebase
    }

    public Message(String senderEmail, String receiverEmail, String message, long timestamp) {
        this.senderEmail = senderEmail;
        this.receiverEmail = receiverEmail;
        this.message = message;
        this.timestamp = timestamp;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}