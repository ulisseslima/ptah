package com.dvlcube.persistence;

/**
 * Classes implementing the Persistent interface have to ability of saving to and loading from a database.
 * @author wonka
 */
public interface SelfPersistent {

    /**
     * Defines the Object used for DB operations.
     * @param dao The Data Access Object
     */
    void setDataAccessObject(DAO dao);
}
