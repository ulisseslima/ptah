package com.dvlcube.persistence;

import java.util.List;
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Wonka
 */
public class Query extends Base {

    public Query() {
    }

    @Deprecated
    public static Query getInstance() {
        return new Query();
    }

    /**
     * Initializes a list of SelfPersistent objects.
     * @param objects The objects to initialize.
     */
    public static void initialize(SelfPersistent... objects) {
        for (SelfPersistent object : objects) {
            if (object != null) {
                object.setDataAccessObject(new Query());
            }
        }
    }

    /**
     * Since the original DB design doesn't allow null values and has zeroes for default values,
     * this method has to be used to get OneToMany associations instead of the default Hibernate method.
     * @param id The desired ObjectId;
     * @param object The desired Object Transient reference;
     * @param entity The entity.
     * @return An Object association, or <code>null</code> if the objectId doesn't have one.
     */
    public static <T> T getAssociation(long id, T object, Class entity) {
        if (id > 0) {
            if (object != null) {
                return object;
            } else {
                DAO dao = new Query();
                return (T) dao.get(entity, id);
            }
        }
        return null;
    }

    /**
     * @param entity The desired entity.
     * @return all the rows in the given entity.
     */
    public static <T> List<T> list(Class entity) {
        DAO dao = new Query();
        List<T> list = dao.getList(entity);
        return list;
    }

    /**
     * NOTE: only works with MySQL databases.
     * @param entity The desired entity;
     * @param limit The maximum number of results to return;
     * @param restrictions The query restrictions.
     * @return a random list of results of size <code>limit</code>.
     */
    public static <T> List<T> randomList(Class entity, int limit, Criterion... restrictions) {
        return new Query().getRandomList(
                entity,
                limit,
                restrictions);
    }

    /**
     * Persists a newly created Object. No Session handling needed.
     * @param object The object to be persisted.
     */
    public static void save(Object object) {
        try {
            if (object == null) {
                throw new IllegalArgumentException("Object is null");
            }
            DAO dao = new Query();
            dao.open();
            dao.set(object);
            dao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an object.
     * @param object The object to delete.
     */
    public static void del(Object object) {
        try {
            if (object == null) {
                throw new IllegalArgumentException("Object is null");
            }
            DAO dao = new Query();
            dao.open();
            dao.delete(object);
            dao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param entity The desired entity;
     * @param id The desired id.
     * @return a previously persisted object from the DB by its id.
     */
    public static <T> T load(Class entity, long id) {
        try {
            DAO dao = new Query();
            T object = (T) dao.get(entity, id);
            if (object instanceof SelfPersistent) {
                ((SelfPersistent) object).setDataAccessObject(dao);
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Updates a previously persisted object.
     * @param object The object to be updated.
     */
    public static void merge(Object object) {
        try {
            DAO dao = new Query();
            dao.open();
            dao.update(object);
            dao.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param entity the entity to select from.
     * @return the total number of records stored in an entity.
     */
    public static long countRows(String entity) {
        long rowCount = 0;
        try {
            DAO dao = new Query();
            dao.open();
            rowCount = dao.getListSize(entity);
            dao.close();
            return rowCount;
        } catch (Exception e) {
            return rowCount;
        }
    }
}
