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
@Entity(name = "IPBanList")
@Table(name = "ipbanlist")
public class IPBanList implements Serializable {

    public IPBanList() {
    }
    @Id
    @Column(name = "list")
    private String list;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "btime")
    private Date banTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "rtime")
    private Date rTime;
    @Column(name = "reason")
    private String reason;

    /* Getters */
    public String getList() {
        return list;
    }

    public Date getBanTime() {
        return banTime;
    }

    public Date getRTime() {
        return rTime;
    }

    public String getReason() {
        return reason;
    }

    /* Setters */
    public void setList(String list) {
        this.list = list;
    }

    public void setBanTime(Date banTime) {
        this.banTime = banTime;
    }

    public void setRTime(Date rTime) {
        this.rTime = rTime;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
