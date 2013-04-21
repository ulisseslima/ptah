package com.dvlcube.model.admin;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity(name = "GlobalRegValue")
@Table(name = "global_reg_value")
public class GlobalRegValue implements Serializable {

    public GlobalRegValue() {
    }
    @Id    //PRIMARY KEY  (`char_id`,`str`,`account_id`),
    @Column(name = "char_id")
    private long charId;
    @Id
    @Column(name = "str")
    private String string;
    @Id
    @Column(name = "account_id")
    private long accountId;
    @Column(name = "`value`")
    private String value;
    @Column(name = "type")
    private int type;

    /* Getters */
    public long getCharId() {
        return charId;
    }

    public String getString() {
        return string;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    /* Setters */
    public void setCharId(long id) {
        this.charId = id;
    }

    public void setString(String string) {
        this.string = string;
    }

    public void setAccountId(long id) {
        this.accountId = id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setType(int type) {
        this.type = type;
    }
}
