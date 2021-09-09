package com.example.inchat.Models;

public class Message {

    private String messageid, message, senderid;
    private long timestamp;

    public Message() {
    }

    public Message(String message, String senderid) {
        this.message = message;
        this.senderid = senderid;
    }

    public Message(String message, String senderid, long timestamp) {
        this.message = message;
        this.senderid = senderid;
        this.timestamp = timestamp;
    }

    public String getMessageid() {
        return messageid;
    }

    public void setMessageid(String messageid) {
        this.messageid = messageid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderid() {
        return senderid;
    }

    public void setSenderid(String senderid) {
        this.senderid = senderid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
