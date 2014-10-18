package com.askr.hackathon.dal;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

/**
 * Hibernate CrudService
 *
 */
public class HibernateCrudServiceDAO implements CrudServiceDAO
{

    @Inject
    private Session session;
    
    public HibernateCrudServiceDAO(Session session) {
		this.session = session;
	}

    public <T> T create(T t)
    {
        session.persist(t);
        session.flush();
        session.refresh(t);
        return t;
    }

    @SuppressWarnings("unchecked")
    public <T, PK extends Serializable> T find(Class<T> type, PK id)
    {
        return (T) session.get(type, id);
    }

    public <T> T update(T type)
    {
        session.merge(type);
        return type;
    }

    public <T, PK extends Serializable> void delete(Class<T> type, PK id)
    {
        @SuppressWarnings("unchecked")
        T ref = (T) session.get(type, id);
        session.delete(ref);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findWithNamedQuery(String queryName)
    {
        return session.getNamedQuery(queryName).list();
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findWithNamedQuery(String queryName, Map<String, Object> params)
    {
        Set<Entry<String, Object>> rawParameters = params.entrySet();
        Query query = session.getNamedQuery(queryName);

        for (Entry<String, Object> entry : rawParameters)
        {
            query.setParameter(entry.getKey(), entry.getValue());

        }
        return query.list();
    }

    @SuppressWarnings("unchecked")
    public <T> T findUniqueWithNamedQuery(String queryName)
    {
        return (T) session.getNamedQuery(queryName).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public <T> T findUniqueWithNamedQuery(String queryName, Map<String, Object> params) {
        Set<Entry<String, Object>> rawParameters = params.entrySet();
        Query query = session.getNamedQuery(queryName);

        for (Entry<String, Object> entry : rawParameters) {
            query.setParameter(entry.getKey(), entry.getValue());

        }
        return (T) query.uniqueResult();
    }

    public <T> T findAll() {
        return null;
    }
}
