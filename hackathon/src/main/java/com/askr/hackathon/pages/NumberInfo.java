package com.askr.hackathon.pages;

import com.askr.hackathon.dal.CrudServiceDAO;
import com.askr.hackathon.entities.MessageEntity;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.RequestParameter;
import org.apache.tapestry5.ioc.annotations.Inject;

/**
 * Created by jamessharman on 19/10/2014.
 */
public class NumberInfo {

    @Inject
    private CrudServiceDAO dao;

    @Property
    private String isAvailable;

    @Property
    private String isRegistered;

    @Property
    private String phoneNumber;

    @Property
    private String carrier;

    @Property
    private String status;

    public void onActivate( @RequestParameter( value = "reachable", allowBlank = true)   String isAvailable,
                                      @RequestParameter( value = "valid", allowBlank = true)       String isRegistered,
                                      @RequestParameter( value = "number")      String phoneNumber,
                                      @RequestParameter( value = "status")      String status,
                                      @RequestParameter( value = "carrier", allowBlank = true ) String carrier ) {

        this.isAvailable = isAvailable;
        this.isRegistered = isRegistered;
        this.carrier = carrier;
        this.status = status;
        this.phoneNumber = phoneNumber;

        if( Integer.parseInt( this.status ) == 0 ){

            //dao.findWithNamedQuery(MessageEntity.ALL, )
        }
        
    }

}
