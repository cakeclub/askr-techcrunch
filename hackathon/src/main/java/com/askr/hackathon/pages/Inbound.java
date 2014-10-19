package com.askr.hackathon.pages;

import com.askr.hackathon.dal.CrudServiceDAO;
import com.askr.hackathon.entities.MessageEntity;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.TextStreamResponse;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * Created by jamessharman on 18/10/2014.
 */
public class Inbound {


    @Inject
    private CrudServiceDAO dao;

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

    public StreamResponse onActivate( @RequestParameter( value = "type")   String smsType,
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

        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        try {
            Date timestampParse = sdf.parse(smsTimestamp);
            MessageEntity ms = new MessageEntity( smsSenderId, timestampParse.getTime(), smsText );
            dao.create( ms );

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new TextStreamResponse("text/plain", "" ); // return 200 to ensure complete
    }

}
