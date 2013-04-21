package com.dvlcube.model.guild;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity
@Table(name = "guild_storage")
public class GuildStorage implements Serializable {

    public GuildStorage() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "guild_id")
    private long guildId;
    @Column(name = "nameid")
    private long itemId;
    @Column(name = "amount")
    private long amount;
    @Column(name = "equip")
    private int equip;
    @Column(name = "identify")
    private boolean isIdentified;
    @Column(name = "refine")
    private int refineRate;
    @Column(name = "attribute")
    private int attribute;
    @Column(name = "card0")
    private long card0;
    @Column(name = "card1")
    private long card1;
    @Column(name = "card2")
    private long card2;
    @Column(name = "card3")
    private long card3;
    @Column(name = "expire_time")
    private long expireTime;

    /* Getters */
    public long getId() {
        return id;
    }

    public long getGuildId() {
        return guildId;
    }

    public long getItemId() {
        return itemId;
    }

    public long getAmount() {
        return amount;
    }

    public long getEquip() {
        return equip;
    }

    public boolean isIdentified() {
        return isIdentified;
    }

    public int getRefineRate() {
        return refineRate;
    }

    public int getAttribute() {
        return attribute;
    }

    public long getCard0() {
        return card0;
    }

    public long getCard1() {
        return card1;
    }

    public long getCard2() {
        return card2;
    }

    public long getCard3() {
        return card3;
    }

    public long getExpireTime() {
        return expireTime;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public void setEquip(int equip) {
        this.equip = equip;
    }

    public void setIdentified(boolean isIdentified) {
        this.isIdentified = isIdentified;
    }

    public void setRefineRate(int refineRate) {
        this.refineRate = refineRate;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public void setCard0(long cardId) {
        this.card0 = cardId;
    }

    public void setCard1(long cardId) {
        this.card1 = cardId;
    }

    public void setCard2(long cardId) {
        this.card2 = cardId;
    }

    public void setCard3(long cardId) {
        this.card3 = cardId;
    }

    public void setExpireTime(long time) {
        this.expireTime = time;
    }
}
