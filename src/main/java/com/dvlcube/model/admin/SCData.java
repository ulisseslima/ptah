package com.dvlcube.model.admin;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity(name = "SCData")
@Table(name = "sc_data")
public class SCData implements Serializable {

    public SCData() {
    }
    @Id
    @Column(name = "account_id")
    private long accountId;
    @Id
    @Column(name = "char_id")
    private long charId;
    @Column(name = "type")
    private int type;
    @Column(name = "tick")
    private int tick;
    @Column(name = "val1")
    private int val1;
    @Column(name = "val2")
    private int val2;
    @Column(name = "val3")
    private int val3;
    @Column(name = "val4")
    private int val4;

    /* Getters */
    public long getAccountId() {
        return accountId;
    }

    public long getCharId() {
        return charId;
    }

    public int getType() {
        return type;
    }

    public int getTick() {
        return tick;
    }

    public int getVal1() {
        return val1;
    }

    public int getVal2() {
        return val2;
    }

    public int getVal3() {
        return val3;
    }

    public int getVal4() {
        return val4;
    }

    /* Setters */
    public void setAccountId(long id) {
        this.accountId = id;
    }

    public void setCharId(long id) {
        this.charId = id;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setTick(int tick) {
        this.tick = tick;
    }

    public void setVal1(int val) {
        this.val1 = val;
    }

    public void setVal2(int val) {
        this.val2 = val;
    }

    public void setVal3(int val) {
        this.val3 = val;
    }

    public void setVal4(int val) {
        this.val4 = val;
    }
}
