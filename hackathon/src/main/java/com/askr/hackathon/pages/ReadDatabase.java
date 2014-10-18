package com.askr.hackathon.pages;

import com.askr.hackathon.dal.CrudServiceDAO;
import com.askr.hackathon.entities.MessageEntity;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.hibernate.annotations.CommitAfter;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Session;

import java.util.List;

public class ReadDatabase {

    @Inject
    private CrudServiceDAO dao;

    @Property
    private MessageEntity message;


    public List<MessageEntity> getAllMessages() {
        return dao.findWithNamedQuery(MessageEntity.ALL);
    }
}
