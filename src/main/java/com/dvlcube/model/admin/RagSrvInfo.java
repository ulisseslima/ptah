package com.dvlcube.model.admin;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity(name = "RagSrvInfo")
@Table(name = "ragsrvinfo")
public class RagSrvInfo implements Serializable {

    public RagSrvInfo() {
    }
    @Id
    @Column(name = "index")
    private long index;
    @Column(name = "name")
    private String name;
    @Column(name = "exp")
    private long exp;
    @Column(name = "jexp")
    private long jobExp;
    @Column(name = "`drop`")
    private long drop;
    @Column(name = "motd")
    private String motd;

    /* Getters */
    public long getId() {
        return index;
    }

    public String getName() {
        return name;
    }

    public long getExp() {
        return exp;
    }

    public long getJobExp() {
        return jobExp;
    }

    public long getDropRate() {
        return drop;
    }

    public String getMsgOfTheDay() {
        return motd;
    }

    /* Setters */
    public void setId(long id) {
        this.index = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public void setJobExp(long jobExp) {
        this.jobExp = jobExp;
    }

    public void setDropRate(long rate) {
        this.drop = rate;
    }

    public void setMsgOfTheDay(String message) {
        this.motd = message;
    }
}
