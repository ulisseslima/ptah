package com.dvlcube.model.admin;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity(name = "SStatus")
@Table(name = "sstatus")
public class SStatus implements Serializable {

    public SStatus() {
    }
    @Id
    @Column(name = "index")
    private int index;
    @Column(name = "name")
    private String name;
    @Column(name = "`user`")
    private long user;

    /* Getters */
    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public long getUser() {
        return user;
    }

    /* Setters */
    public void setIndex(int id) {
        this.index = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUser(long user) {
        this.user = user;
    }
}
