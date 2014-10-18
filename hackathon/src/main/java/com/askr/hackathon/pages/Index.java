package com.askr.hackathon.pages;

import java.util.Date;
import java.util.List;

import com.askr.hackathon.dal.CrudServiceDAO;
import com.askr.hackathon.entities.MessageEntity;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.ioc.annotations.*;
import org.apache.tapestry5.corelib.components.*;
import org.apache.tapestry5.SymbolConstants;
import org.apache.tapestry5.alerts.AlertManager;

/**
 * Start page of application hackathon.
 */
public class Index
{
    @Property
    @Inject
    @Symbol(SymbolConstants.TAPESTRY_VERSION)
    private String tapestryVersion;

    @Inject
    private CrudServiceDAO dao;

    @InjectComponent
    private Zone msg_zone;

    @Persist
    @Property
    private int clickCount;

    @Inject
    private AlertManager alertManager;

    @Property
    private MessageEntity sms;

    private List<MessageEntity> messageStream;

    @Property
    private String replyMsg;

    @Component
    private Form replyForm;

    private MessageEntity selectedSMS;

    public Object onSuccess()
    {
        //saveReply(selectedSMS.getId(),replyMsg);
        return msg_zone;
    }

    public void onActivate() {
        messageStream = dao.findWithNamedQuery(MessageEntity.ALL);
    }

    public Date getCurrentTime()
    {
        return new Date();
    }

    public void updateView(MessageEntity ms) {
        messageStream.add(ms);
    }

    public List<MessageEntity> getMessageStream() {
        return messageStream;
        //return dao.findWithNamedQuery(MessageEntity.ALL);
    }

    public void saveReply(String id, String reply) {
        MessageEntity messageEntity = dao.find(MessageEntity.class, id);
        messageEntity.setTimeOut(new Date().getTime());
        messageEntity.setReply(reply);
        dao.update(messageEntity);
    }

    public void onActionFromViewThread(MessageEntity sms) {
        this.selectedSMS = sms;
    }

}
