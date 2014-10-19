package com.askr.hackathon.pages;

import com.askr.hackathon.dal.CrudServiceDAO;
import com.askr.hackathon.entities.MessageEntity;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.util.TextStreamResponse;

import javax.validation.constraints.Null;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jamessharman on 19/10/2014.
 */
public class NumberInfo {

    @Inject
    private CrudServiceDAO dao;

    @Property
    private String isAvailable;

    @Property
    private String isValid;

    @Property
    private String phoneNumber;

    @Property
    private String carrier;

    @Property
    private String status;

    public StreamResponse onActivate( @RequestParameter( value = "reachable", allowBlank = true)   String isAvailable,
                                      @RequestParameter( value = "valid", allowBlank = true)       String isValid,
                                      @RequestParameter( value = "number")      String phoneNumber,
                                      @RequestParameter( value = "status")      String status,
                                      @RequestParameter( value = "carrier", allowBlank = true ) String carrier ) {

        this.isAvailable = isAvailable;
        this.isValid = isValid;
        this.carrier = carrier;
        this.status = status;
        this.phoneNumber = phoneNumber;

        if( Integer.parseInt( this.status ) == 0 ){

            Map<String, Object> params = new HashMap<String, Object>();
            params.put( "number", this.phoneNumber );

            List<MessageEntity> messages = dao.findWithNamedQuery( MessageEntity.BY_NUMBER, params );

            if( this.isAvailable == null ){
                this.isAvailable = "true";
            }

            if( this.isValid == null ){
                this.isValid = "true";
            }

            if( this.carrier == null ){
                this.carrier = "Unknown";
            }


            for( MessageEntity message: messages  ){
                message.setAvailable( Boolean.parseBoolean(this.isAvailable) );
                message.setValid( Boolean.parseBoolean(this.isValid));
                message.setCarrier( this.carrier );
                dao.update( message );
            }
        }

        return new TextStreamResponse("text/plain", "" );
    }

}
