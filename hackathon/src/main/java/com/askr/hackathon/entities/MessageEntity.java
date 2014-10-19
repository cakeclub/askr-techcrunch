package com.askr.hackathon.entities;

import com.askr.hackathon.tools.QuestionCategory;
import com.askr.hackathon.tools.QuestionSentiment;
import com.askr.hackathon.tools.SentimentClassifier;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Entity
@NamedQueries({@NamedQuery(name = MessageEntity.ALL, query = "Select m from MessageEntity m"),
                @NamedQuery(name = MessageEntity.NO_REPLY, query = "Select m from MessageEntity m where m.replied = false"),
                @NamedQuery(name = MessageEntity.BY_NUMBER, query = "Select m from MessageEntity m where m.phoneNumber = :number")})
@Table(name = "messages")
public class MessageEntity implements Serializable, Comparable<MessageEntity> {

    public static final String ALL = "MessageEntity.all";
    public static final String BY_NUMBER = "MessageEntity.by_number";
    public static final String NO_REPLY = "MessageEntity.no_reply";

    public static final DateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.ENGLISH);

    @Id
    private String id;
    private String phoneNumber;
    private long timeRecieved;
    private String message;
    private String reply;
    private long timeOut;
    private boolean replied;
    private boolean valid;
    private boolean available;
    private String carrier;
    private QuestionCategory category;
    private QuestionSentiment sentiment;

    public MessageEntity(String phoneNumber, long timeRecieved, String message) {
        this.phoneNumber = phoneNumber;
        this.message = message;
        this.timeRecieved = timeRecieved;
        this.id = phoneNumber + ":" + String.valueOf(timeRecieved);
        this.replied = false;
        this.valid = true;
        this.available = true;
        this.carrier = "NA";
        SentimentClassifier classifier = new SentimentClassifier();
        this.category = classifier.getQuestionCategory(message);
        this.sentiment = classifier.getQuestionSentiment(message);
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
        this.replied = true;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public int compareTo(MessageEntity o) {
        return Long.compare(this.getTimeRecieved(), o.getTimeRecieved());
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
    }

    public boolean isReplied() {
        return replied;
    }

    public QuestionCategory getCategory() {
        return category;
    }

    public void setCategory(QuestionCategory category) {
        this.category = category;
    }

    public QuestionSentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(QuestionSentiment sentiment) {
        this.sentiment = sentiment;
    }

    public String getTimeSince() {
        return format.format(new Date(getTimeRecieved()));
    }

    public String getSentimentColour() {
        if (getSentiment().equals(QuestionSentiment.ANGRY))
            return "redbg";
        else if(getSentiment().equals(QuestionSentiment.HAPPY))
            return "greenbg";
        else
            return "";
    }

    public String getCatSrc() {
        if (getCategory().equals(QuestionCategory.TV))
            return "tv";
        else if (getCategory().equals(QuestionCategory.BROADBAND))
            return "broadband";
        else if (getCategory().equals(QuestionCategory.PHONE))
            return "phone";
        else if (getCategory().equals(QuestionCategory.MULTI))
            return "multi";
        else
            return "other";
    }

    public String getAvailability() {
        if (isAvailable())
            return "online";
        else
            return "offline";
    }

    public String getValidity() {
        if (isValid())
            return "tick";
        else
            return "cross";
    }
}
