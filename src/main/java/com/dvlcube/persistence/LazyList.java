package com.dvlcube.persistence;

import java.util.AbstractList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.hibernate.criterion.Order;

/**
 * http://www.ninthavenue.com.au/blog/paging-large-data-sets-with-a-lazylist
 *
 * This is a list backed by a JPA Query, but only loading the results of the
 * query one page at a time. It loads one page *ahead* for each fetch miss,
 * so if you are iterating through query results backwards you will get poor
 * performance.
 */
public class LazyList<T> extends AbstractList<T> {

    /** backing query */
    DAO query;
    /** cache of loaded items */
    Map<Integer, Object> loaded;
    /** total number of results expected */
    long numResults;
    /** number of results to fetch on cache miss */
    int pageSize;
    Order order;
    Class entity;

    /** default constructor */
    public LazyList() {
        loaded = new HashMap<Integer, Object>();
    }

    /**
     * Create a LazyList backed by the given query, using pageSize results
     * per page, and expecting numResults from the query.
     */
    public LazyList(DAO query, Class entity, Order order, int pageSize, long numResults) {
        this();
        this.query = query;
        this.pageSize = pageSize;
        this.numResults = numResults;
        this.entity = entity;
        this.order = order;
    }

    /**
     * Fetch an item, loading it from the query results if it hasn't already 
     * been.
     */
    @Override
    public T get(int i) {
        if (!loaded.containsKey(i)) {
            //List results = query.setFirstResult(firstResult).setMaxResults(pageSize).getResultList();
            List results = query.getList(entity, order, i, pageSize);
            for (int j = 0; j < results.size(); j++) {
                loaded.put(i + j, results.get(j));
            }
        }
        System.out.println("Lazy Size: "+loaded.size());
        return (T) loaded.get(i);
    }

    /**
     * Alternate version of get()
     * @param i
     * @return 
     */
    public Object get2(int i) {
        if (!loaded.containsKey(i)) {
            int pageIndex = i / pageSize;
            //List results = query.setFirstResult(pageIndex * pageSize).setMaxResults(pageSize).getResultList();
            List results = query.getList(entity, order, i, pageSize);
            for (int j = 0; j < results.size(); j++) {
                loaded.put(pageIndex * pageSize + j, results.get(j));
            }
        }
        return loaded.get(i);
    }

    /**
     * Return the total number of items in the list. This is done by
     * using an equivalent COUNT query for the backed query.
     */
    @Override
    public int size() {
        return (int) numResults;
    }

    /** update the number of results expected in this list */
    public void setNumResults(long numResults) {
        this.numResults = numResults;
    }
}