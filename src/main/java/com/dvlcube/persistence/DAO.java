package com.dvlcube.persistence;

import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;

/**
 *
 * @author Wonka
 */
public interface DAO {

    /**
     * Sets the Hibernate Session Factory that should be used.
     * @param factory A Hibernate Session Factory.
     */
    void setFactory(SessionFactory factory);

    /**
     * Gets the current Session Factory.
     * @return the current Session Factory.
     */
    SessionFactory getFactory();

    /**
     * Gets the current Session. It is used to perform the most basic operations, like creating a Criteria or a Query.
     * @return the current Session.
     */
    Session getSession();

    /**
     * This method shoud be used to test whether the current session has any non-persisted or pending transactions.
     * @return whether the current session has any non-persisted or pending transactions.
     */
    boolean isDirty();

    /**
     * Opens a session and begins a transaction.
     */
    void open() throws HibernateException;

    /**
     * Opens a Hibernate Session.
     * @throws HibernateException if a Hibernate Exception occurs.
     */
    void openSession() throws HibernateException;

    /**
     * Starts a Hibernate Transaction.
     * @throws HibernateException if a Hibernate Exception occurs.
     */
    void beginTransaction() throws HibernateException;

    /**
     * Commits a previously started transaction.
     * @throws HibernateException if a Hibernate Exception occurs. eg: Transaction not open.
     */
    void commit() throws HibernateException;

    /**
     * Closes a Hibernate Session. The method should always be called at the end of the transaction, in a <code>finally</code> block.
     * @throws HibernateException if a Hibernate Exception occurs.
     */
    void closeSession() throws HibernateException;

    /**
     * Commits and closes the session, if it is open.
     */
    void close() throws HibernateException;

    boolean isOpen();

    /**
     * Persists an Object into the Database.
     * Note that the Object will only be persisted after a successful transaction commit.
     * @param object The Object to be persisted.
     */
    void set(Object object);

    /**
     * Updates a previously persisted Object.
     * Note that the Object will only be updated after a successful transaction commit.
     * @param object The Object to be upadted.
     */
    void update(Object object);

    void saveOrUpdate(Object object);

    /**
     * Removes (unpersists) an Object from the Database.
     * Note that the Object will only be unpersisted after a successful transaction commit.
     * @param object The Object to be deleted.
     */
    void delete(Object object);

    void flush();

    /**
     * Rolls back a transaction. This method should be used to safelly roll back a transaction after an exception occurs.
     * @return <code>true</code> if the transaction was rolled back successfully, <code>false</code> otherwise.
     */
    boolean rollBack();

    /**
     * Gets a single Object from a specific Entity class by its visible id.
     * The visible id is a String representation of the desired object composed by the Entity name and the Object id, separated by an <code>at</code> sign.
     * eg: <code>com.dvlcube.Entity@1</code> would return the first row of the <code>Entity</code> entity.
     * @param visibleId The desired Object's visible id.
     * @return The desired Object.
     */
    <T> T get(String visibleId);

    /**
     * Loads a previously saved Object from the database.
     * @param entity Full class name, as String.
     * @param id The id of the Object to be loaded, as String.
     * @return An Object.
     */
    <T> T get(Class entity, String stringId);

    /**
     * Loads a previously saved Object from the database.     *
     * @param entity Full class name;
     * @param id The id of the Object to be loaded, as long.
     * @return An Object.
     */
    <T> T get(Class entity, long id);
    
    /**
     * Gets all the Objects in the given Entity.
     * Requires an open connection.
     * @param entity The desired entity.
     * @return All the Objects in the passed Entity.
     */
    <T> List<T> getList(Class entity);

    /**
     * Gets all the Objects in the given Entity, ordered by the Order object.
     * Requires an open connection.
     * @param entity The desired entity;
     * @param order The desired Order.
     * @return All the Objects in the passed Entity.
     */
    <T> List<T> getList(Class entity, Order order);

    /**
     * Gets all the Objects in the passed Entity, ordered by the Order object, with <code>maxResults</code> max results.
     * Requires an open connection.
     * @param entity The desired entity;
     * @param order The desired Order;
     * @param maxResults The number of max results that should be returned.
     * @return All the Objects in the passed Entity.
     */
    <T> List<T> getList(Class entity, Order order, int maxResults);
    
    /**
     * Generates a List from a Criteria object.
     * Does not require an open connection.
     * @param entity The desired entity to load values from;     
     * @param order The query results order;
     * @param restrictions The query restrictions;
     * @return The query results.
     */
    <T> List<T> getList(Class entity, Order order, Criterion... restrictions);
    
    /**
     * Gets all the Objects in the passed Entity, ordered by the Order object, 
     * starting from <code>start</code>, with <code>maxResults</code> max results.
     * Requires an open connection.
     * @param entity The desired entity;
     * @param order The desired Order;
     * @param start Where to start the query;
     * @param maxResults The number of max results that should be returned;
     * @param restrictions The query restrictions.
     * @return All the Objects in the passed Entity.
     */
    <T> List<T> getList(Class entity, Order order, int start, int maxResults, Criterion... restrictions);    

    /**
     * NOTE: only works with mysql databases.
     * @param entity The desired entity;
     * @param maxResults The maximum number of results;
     * @param restrictions The query restrictions.
     * @return a list of randomly selected rows ordered by the given parameter.
     */
    <T> List<T> getRandomList(Class entity, int maxResults, Criterion... restrictions);

    /**
     * Creates a Query from a HQL Query String. The query should return only one result.
     * @param queryString The Query String.
     * @return The Query result Object.
     */
    <T> T query(String queryString);

    /**
     * Creates a Query from a HQL Query String.
     * @param queryString The Query String.
     * @return The Query result List.
     */
    <T> List<T> queryList(String queryString);
    
    /**
     * Creates a Query from a HQL Query String, with a maximum amount of rows to return.
     * @param queryString The Query String.
     * @param maxResults Max results to return.
     * @return The Query result List.
     */
    <T> List<T> queryList(String queryString, int maxResults);

    /**
     * Creates a Query from a SQL Query String.
     * @param sqlString The Query String (in SQL syntax);
     * @param type The desired return type.
     * @return The Query result List.
     */
    List sqlList(String sqlString, Class type);

    /**
     * Casts a SQL result set made with the sqlList() method to the given type.
     * Warning: the SQL rults must be in the right order.
     * @param sqlResultSet The SQL result set (as Object[]);
     * @param type The desired type.
     * @return A typed List of results.
     */
    List typeResults(List<Object[]> sqlResultSet, Class type);

    /**
     * Creates a Criteria object that should return only one result.
     * @param entity The desired Entity;
     * @param restriction The Restriction Objects.
     * @return An Object representing this Criteria's uniqueResult() value.
     */
    <T> T getUniqueResult(Class entity, Criterion... restrictions);

    /**
     * Gets the first result in this Criteria's results.
     * @param entity The desired Entity;
     * @param restriction The Restriction Objects;
     * @param order The desired Order. Since only the first result will be returned, the Order have the highest importance in this method.
     * @return The first result in this Criteria's results.
     */
    <T> T getFirstResult(Class entity, Order order, Criterion... restrictions);

    /**
     * Gets the number of rows persisted from the given Entity.
     * Uses pure SQL to achieve high performance.
     * @param entity The Entity name, as String.
     * @return The number of rows persisted, as Long.
     */
    Long getListSize(String entity);

    /**
     * Tests if the passed field value already exists in the given Entity.
     * @param entity The target Entity;
     * @param fieldName The target field name;
     * @param check The target field value.
     * @return <code>true</code> if the value already exists, <code>false</code> otherwise.
     */
    boolean hasConstraintViolation(Class entity, String fieldName, String check);
}
