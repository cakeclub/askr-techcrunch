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
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

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

    @InjectComponent
    private Zone thread_zone;

    @InjectComponent
    private Zone overallZone;

    @Persist
    @Property
    private int clickCount;

    @Inject
    private AlertManager alertManager;

    @Property
    private MessageEntity sms;

    @Property
    private MessageEntity threadSMS;

    @Persist
    private List<MessageEntity> messageStream;

    @Persist
    private List<MessageEntity> threadStream;

    @Property
    private String replyMsg;

    @Component
    private Form replyForm;

    @Inject
    private Request request;

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Persist
    private MessageEntity selectedSMS;

    public Boolean getSmsSelected() {
        return smsSelected;
    }

    @Persist
    private Boolean smsSelected;

    public Object onSuccess() {
        saveReply(selectedSMS, replyMsg);
        return overallZone;
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

    public List<MessageEntity> getThreadStream() {
        return threadStream;
    }

    public void saveReply(MessageEntity entity, String reply) {
        entity.setTimeOut(new Date().getTime());
        entity.setReply(reply);
        dao.update(entity);
        TextReplyer.sendMessage(reply, entity.getPhoneNumber());
        selectedSMS = null;
        smsSelected = false;
    }

    public void onActionFromViewThread(MessageEntity sms) {
        this.smsSelected = true;
        this.selectedSMS = sms;
        this.threadStream = getThreadByNumber(sms.getPhoneNumber());
        ajaxResponseRenderer.addRender("msg_zone", msg_zone).addRender("thread_zone", thread_zone);
    }

    public List<MessageEntity> getThreadByNumber(String number) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("number", number);
        List<MessageEntity> withNamedQuery = dao.findWithNamedQuery(MessageEntity.BY_NUMBER, params);
        Collections.sort(withNamedQuery);
        return withNamedQuery;
    }

    public int getSMSCount() {
        return messageStream.size();
    }
}
