package com.dvlcube.persistence;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Ulisses 
 */
public abstract class Base implements DAO {

    public Base() {
        if (BaseFactory.getFactory() == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            BaseFactory.setFactory(sessionFactory);
        } else {
            sessionFactory = BaseFactory.getFactory();
        }
    }
    protected SessionFactory sessionFactory = null;
    protected Session session = null;
    protected Transaction transaction = null;
    protected boolean isOpen = false;

    @Override
    public void setFactory(SessionFactory factory) {
        this.sessionFactory = factory;
    }

    @Override
    public SessionFactory getFactory() {
        return sessionFactory;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public boolean isDirty() {
        try {
            if (session.isDirty()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return true;
        }
    }

    @Override
    public void open() throws HibernateException {
        Session newSession = sessionFactory.openSession();
        this.session = newSession;
        this.isOpen = true;
        Transaction newTransaction = null;
        try {
            newTransaction = newSession.beginTransaction();
            this.transaction = newTransaction;
        } catch (org.hibernate.SessionException e) {
            if (newTransaction != null) {
                newTransaction.rollback();
            }
            session.close();
            this.isOpen = false;
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws HibernateException {
        if (this.isOpen) {
            try {
                transaction.commit();
            } catch (org.hibernate.TransactionException e) {
                if (transaction != null) {
                    transaction.rollback();
                }
                e.printStackTrace();
            } finally {
                session.close();
                this.isOpen = false;
            }
        }
    }

    @Override
    public void openSession() throws HibernateException {
        Session newSession = sessionFactory.openSession();
        this.session = newSession;
        this.isOpen = true;
    }

    @Override
    public void beginTransaction() throws HibernateException {
        Transaction newTransaction = null;
        try {
            newTransaction = session.beginTransaction();
            this.transaction = newTransaction;
        } catch (org.hibernate.SessionException e) {
            if (newTransaction != null) {
                newTransaction.rollback();
            }
            session.close();
            this.isOpen = false;
            e.printStackTrace();
        }
    }

    @Override
    public void commit() throws HibernateException {
        try {
            transaction.commit();
        } catch (org.hibernate.TransactionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void closeSession() throws HibernateException {
        try {
            session.close();
            this.isOpen = false;
        } catch (org.hibernate.TransactionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isOpen() {
        if (this.isOpen) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void set(Object object) {
        try {
            session.save(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Object object) {
        //session.update(object);
        try {
            session.merge(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveOrUpdate(Object object) {
        try {
            session.saveOrUpdate(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Object object) {
        try {
            session.delete(object);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void flush() {
        try {
            session.flush();
        } catch (Exception e) {
        }
    }

    @Override
    public boolean rollBack() {
        try {
            transaction.rollback();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public <T> T get(Class entity, String stringId) {
        return (T) get(
                entity,
                Long.parseLong(stringId));
    }

    @Override
    public <T> T get(String visibleId) {
        String[] entity = visibleId.split("@");
        String className = entity[0];
        long id = Long.parseLong(entity[1]);
        Class classObject = null;
        try {
            classObject = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (classObject != null) {
            return (T) get(
                    classObject,
                    id);
        } else {
            return null;
        }
    }

    @Override
    public <T> T get(Class entity, long id) {
        open();
        try {
            T object = (T) session.get(entity, id);
            commit();
            if (object instanceof SelfPersistent) {
                ((SelfPersistent) object).setDataAccessObject(this);
            }
            return object;
        } catch (Exception e) {
            rollBack();
        } finally {
            closeSession();
        }
        return null;
    }

    @Override
    public <T> T getUniqueResult(Class entity, Criterion... restrictions) {
        open();
        try {
            Criteria criteria = getSession().createCriteria(entity);
            if (restrictions != null) {
                for (Criterion restriction : restrictions) {
                    criteria.add(restriction);
                }
            }
            T uniqueResult = (T) criteria.uniqueResult();
            commit();
            if (uniqueResult != null) {
                if (uniqueResult instanceof SelfPersistent) {
                    ((SelfPersistent) uniqueResult).setDataAccessObject(this);
                }
                return uniqueResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollBack();
        } finally {
            closeSession();
        }
        return null;
    }

    @Override
    public Long getListSize(String entity) {
        try {
            Long listSize = (Long) session.createQuery(
                    "SELECT COUNT(id) FROM " + entity).uniqueResult();
            return listSize;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> List<T> getList(Class entity) {
        return getList(entity, Order.asc("id"), 0, 0);
    }

    @Override
    public <T> List<T> getList(Class entity, Order order) {
        return getList(entity, order, 0, 0);
    }

    @Override
    public <T> List<T> getList(Class entity, Order order, int maxResults) {
        return getList(entity, order, 0, maxResults);
    }

    @Override
    public <T> List<T> getList(Class entity, Order order, Criterion... restrictions) {
        return getList(entity, order, 0, 0, restrictions);
    }

    @Override
    public <T> T getFirstResult(Class entity, Order order, Criterion... restrictions) {
        return (T) getList(entity, order, 0, 1, restrictions).get(0);
    }

    @Override
    public <T> List<T> getList(Class entity, Order order, int start, int maxResults, Criterion... restrictions) {
        open();
        try {
            Criteria criteria = session.createCriteria(entity);
            if (restrictions != null) {
                for (Criterion restriction : restrictions) {
                    criteria.add(restriction);
                }
            }
            criteria.addOrder(order);
            criteria.setFirstResult(start);
            if (maxResults > 0) {
                criteria.setMaxResults(maxResults);
            }
            List<T> results = criteria.list();
            commit();
            if (results.size() > 0) {
                return results;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollBack();
        } finally {
            closeSession();
        }
        return null;
    }

    @Override
    public boolean hasConstraintViolation(Class entity, String fieldName, String value) {
        return getList(
                entity,
                Order.asc("id"),
                Restrictions.eq(fieldName, value)).size()
                > 0;
    }

    @Override
    public List queryList(String queryString) {
        return queryList(queryString, 0);
    }

    @Override
    public <T> List<T> queryList(String queryString, int maxResults) {
        open();
        try {
            org.hibernate.Query query = getSession().createQuery(queryString);
            if (maxResults > 0) {
                query.setMaxResults(maxResults);
            }
            List results = query.list();
            commit();
            if (results.size() > 0) {
                return results;
            }
        } catch (Exception e) {
            e.printStackTrace();
            rollBack();
        } finally {
            closeSession();
        }
        return null;
    }

    @Override
    public List sqlList(String sqlString, Class type) {
        open();
        try {
            org.hibernate.Query query = getSession().createSQLQuery(sqlString);
            List results = query.list();
            commit();
            if (results.size() > 0) {
                return typeResults(results, type);
            }
        } catch (Exception e) {
            rollBack();
        } finally {
            closeSession();
        }
        return null;
    }

    @Override
    public List typeResults(List<Object[]> sqlResultSet, Class type) {
        try {
            List<Object> reflectedList = new ArrayList<Object>();
            for (Object[] columns : sqlResultSet) {
                Class className = Class.forName(type.getName());
                Object object = className.newInstance();
                List<Method> setters = new ArrayList<Method>();
                for (Method method : object.getClass().getMethods()) {
                    if (method.getName().startsWith("set")) {
                        setters.add(method);
                    }
                }
                for (int i = 0; i < columns.length; i++) {
                    setters.get(i).invoke(object, columns[i]);
                }
                reflectedList.add(object);
            }
            return reflectedList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T> T query(String queryString) {
        open();
        try {
            org.hibernate.Query query = getSession().createQuery(queryString);
            T result = (T) query.uniqueResult();
            if (result != null) {
                return result;
            }
            commit();
        } catch (Exception e) {
            e.printStackTrace();
            rollBack();
        } finally {
            closeSession();
        }
        return null;
    }

    public <T> List<T> getRandomList(Class entity, int maxResults, Criterion... restrictions) {
        open();
        try {
            Criteria criteria = getSession().createCriteria(entity);
            for (Criterion criterion : restrictions) {
                criteria.add(criterion);
            }
            criteria.add(Restrictions.sqlRestriction("1=1 order by rand() limit " + maxResults));
            List<T> list = criteria.list();
            commit();
            return list;
        } catch (Exception e) {
            rollBack();
        } finally {
            closeSession();
        }
        return null;
    }
}
