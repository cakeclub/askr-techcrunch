package com.askr.hackathon.pages;

import java.util.*;

import com.askr.hackathon.dal.CrudServiceDAO;
import com.askr.hackathon.dal.QueryParameters;
import com.askr.hackathon.entities.MessageEntity;
import com.askr.hackathon.tools.TextReplyer;
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

    @Persist
    private MessageEntity selectedSMS;

    public Object onSuccess()
    {
        saveReply(selectedSMS, replyMsg);
        return msg_zone;
    }

    public void onActivate() {
        List<MessageEntity> messages = dao.findWithNamedQuery(MessageEntity.NO_REPLY);
        Collections.sort(messages, Collections.reverseOrder());
        messageStream = messages;
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
    }

    public void saveReply(MessageEntity entity, String reply) {
        entity.setTimeOut(new Date().getTime());
        entity.setReply(reply);
        dao.update(entity);
        TextReplyer.sendMessage(reply, entity.getPhoneNumber());
    }

    public void onActionFromViewThread(MessageEntity sms) {
        this.selectedSMS = sms;
    }

    public List<MessageEntity> getThreadByNumber(String number) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", number);
        List<MessageEntity> withNamedQuery = dao.findWithNamedQuery(MessageEntity.BY_NUMBER, params);
        Collections.sort(withNamedQuery);
        return withNamedQuery;
    }

}
