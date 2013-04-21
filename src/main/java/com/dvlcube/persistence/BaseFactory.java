package com.dvlcube.persistence;

import org.hibernate.SessionFactory;

/**
 *
 * @author Wonka
 */
public class BaseFactory {
    protected static SessionFactory factory = null;

    protected static void setFactory(SessionFactory sessionFactory){
        factory = sessionFactory;
    }

    protected static SessionFactory getFactory(){
        return factory;
    }
}
