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
@Entity(name = "CharLog")
@Table(name = "charlog")
public class CharLog implements Serializable {

    public CharLog() {
    }
    @Id
    @Column(name = "account_id")
    private long accountId;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "`time`")
    private Date time;
    @Column(name = "char_msg")
    private String charMessage;
    @Column(name = "char_num")
    private int charPosition;
    @Column(name = "name")
    private String name;
    @Column(name = "str")
    private int STR;
    @Column(name = "agi")
    private int AGI;
    @Column(name = "vit")
    private int VIT;
    @Column(name = "`int`")
    private int INT;
    @Column(name = "dex")
    private int DEX;
    @Column(name = "luk")
    private int LUK;
    @Column(name = "hair")
    private int hair;
    @Column(name = "hair_color")
    private int hairColor;

    /* Getters */
    public long getAccountId() {
        return accountId;
    }

    public Date getTime() {
        return time;
    }

    public String getCharMessage() {
        return charMessage;
    }

    public int getCharPosition() {
        return charPosition;
    }

    public String getName() {
        return name;
    }

    public int getStr() {
        return STR;
    }

    public int getAgi() {
        return AGI;
    }

    public int getVit() {
        return VIT;
    }

    public int getInt() {
        return INT;
    }

    public int getDex() {
        return DEX;
    }

    public int getLuk() {
        return LUK;
    }

    public int getHair() {
        return hair;
    }

    public int getHairColor() {
        return hairColor;
    }

    /* Setters */
    public void setAccountId(long id) {
        this.accountId = id;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public void setCharMessage(String message) {
        this.charMessage = message;
    }

    public void setCharPosition(int position) {
        this.charPosition = position;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStr(int STR) {
        this.STR = STR;
    }

    public void setAgi(int AGI) {
        this.AGI = AGI;
    }

    public void setVit(int VIT) {
        this.VIT = VIT;
    }

    public void setInt(int INT) {
        this.INT = INT;
    }

    public void setDex(int DEX) {
        this.DEX = DEX;
    }

    public void setLuk(int LUK) {
        this.LUK = LUK;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }
}
