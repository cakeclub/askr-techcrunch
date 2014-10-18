package com.askr.hackathon.pages;

import org.apache.tapestry5.Link;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.internal.services.ResponseImpl;
import org.apache.tapestry5.services.Response;

/**
 * Created by jamessharman on 18/10/2014.
 */
public class Inbound {

    @Property
    private String smsType;

    @Property
    private String smsToNumber;

    @Property
    private String smsSenderId;

    @Property
    private String smsMessageId;

    @Property
    private String smsTimestamp;

    @Property
    private String smsText;

    // For concatenated messages, ie over two pages
    @Property
    private String smsIsConcat;

    @Property
    private String smsConcatRef;

    @Property
    private String smsConcatTotal;

    @Property
    private String smsConcatPart;

    public void onActivate( @RequestParameter( value = "type")   String smsType,
                            @RequestParameter( value = "to")     String smsToNumber,
                            @RequestParameter( value = "msisdn") String smsSenderId,
                            @RequestParameter( value = "messageId") String smsMessageId,
                            @RequestParameter( value = "message-timestamp") String smsTimestamp,
                            @RequestParameter( value = "text") String smsText,
                            @RequestParameter( value = "concat", allowBlank = true ) String smsIsConcat,
                            @RequestParameter( value = "concat-ref", allowBlank = true ) String smsConcatRef,
                            @RequestParameter( value = "concat-total", allowBlank = true ) String smsConcatTotal,
                            @RequestParameter( value = "concat-part", allowBlank = true ) String smsConcatPart) {
        this.smsType = smsType;
        this.smsToNumber = smsToNumber;
        this.smsSenderId = smsSenderId;
        this.smsMessageId = smsMessageId;
        this.smsTimestamp = smsTimestamp;
        this.smsText = smsText;
        this.smsIsConcat = smsIsConcat;
        this.smsConcatRef = smsConcatRef;
        this.smsConcatTotal = smsConcatTotal;
        this.smsConcatPart = smsConcatPart;

        //return new ResponseImpl()
    }

}
