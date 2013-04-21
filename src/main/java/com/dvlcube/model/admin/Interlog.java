package com.dvlcube.model.admin;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * @author Wonka
 */
@Entity(name = "Interlog")
@Table(name = "interlog")
public class Interlog implements Serializable {

    public Interlog() {
    }
    @Id
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "`time`")
    private Date time;
    @Column(name = "log")
    private String log;

    /* Getters */
    public Date getTime() {
        return time;
    }

    public String getLog() {
        return log;
    }

    /* Setters */
    public void setId(Date date) {
        this.time = date;
    }

    public void setLog(String log) {
        this.log = log;
    }
}
