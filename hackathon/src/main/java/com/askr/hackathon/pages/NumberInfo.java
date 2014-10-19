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
                                      @RequestParameter( value = "carrier_network_name", allowBlank = true ) String carrier ) {

        this.isAvailable = isAvailable;
        this.isValid = isValid;
        this.carrier = carrier;
        this.status = status;
        this.phoneNumber = phoneNumber;

        if( "0".equals( this.status ) ){

            Map<String, Object> params = new HashMap<String, Object>();
            params.put( "number", this.phoneNumber );

            if( "reachable".equals(this.isAvailable) ){
                this.isAvailable = "true";
            }else{
                this.isAvailable = "false";
            }

            if( "valid".equals(this.isValid) ){
                this.isValid = "true";
            }else{
                this.isValid = "false";
            }

            List<MessageEntity> messages = dao.findWithNamedQuery( MessageEntity.BY_NUMBER, params );

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
