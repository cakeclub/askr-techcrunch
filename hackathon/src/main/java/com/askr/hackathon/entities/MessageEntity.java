package com.askr.hackathon.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NamedQueries({@NamedQuery(name = MessageEntity.ALL, query = "Select m from MessageEntity m")})
@Table(name = "messages")
public class MessageEntity implements Serializable {

    public static final String ALL = "MessageEntity.all";

    @Id
    private String id;
    private String phoneNumber;
    private long timeRecieved;
    private String message;
    private String reply;
    private long timeOut;

    public MessageEntity(String phoneNumber, long timeRecieved, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.timeRecieved = timeRecieved;
        this.id = phoneNumber + ":" + String.valueOf(timeRecieved);
    }

    public MessageEntity() {}

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimeRecieved() {
        return timeRecieved;
    }

    public void setTimeRecieved(long timeRecieved) {
        this.timeRecieved = timeRecieved;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }
}
