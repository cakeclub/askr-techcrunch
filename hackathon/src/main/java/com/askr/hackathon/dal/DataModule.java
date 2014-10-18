package com.askr.hackathon.dal;

import org.apache.tapestry5.ioc.annotations.Startup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Demo Data Loader - This can be used to load demo data into the system
 * 
 */
public class DataModule
{
    private static final Logger LOGGER = LoggerFactory.getLogger(DataModule.class);

    private final CrudServiceDAO dao;

    public DataModule(CrudServiceDAO dao)
    {
        super();
        this.dao = dao;
    }

    @Startup
    public void initialize()
    {
//        List<User> users = new ArrayList<User>();
//
//        users.add(new User("Christophe Cordenier", "cordenier", "ccordenier@example.com",
//                "cordenier"));
//        users.add(new User("Katia Aresti", "karesti", "karesti@example.com", "karesti"));
//
//        LOGGER.info("-- Loading initial demo data");
//        create(users);
//
//        List<User> userList = dao.findWithNamedQuery(User.ALL);
//        LOGGER.info("Users " + userList);
//        LOGGER.info("-- Data Loaded. Exit");
    }

    private void create(List<?> entities)
    {
        for (Object e : entities)
        {
            dao.create(e);
        }
    }

}
