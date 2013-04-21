package com.dvlcube.model.guild;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity
@Table(name = "guild_castle")
public class GuildCastle implements Serializable {

    public GuildCastle() {
    }
    @Id
    @Column(name = "castle_id")
    private long id;
    @Column(name = "guild_id")
    private long guildId;
    @Column(name = "economy")
    private long economy;
    @Column(name = "defense")
    private long defense;
    @Column(name = "triggerE")
    private long triggerE;
    @Column(name = "triggerD")
    private long triggerD;
    @Column(name = "nextTime")
    private long nextTime;
    @Column(name = "payTime")
    private long payTime;
    @Column(name = "createTime")
    private long createTime;
    @Column(name = "visibleC")
    private long visibleC;
    @Column(name = "visibleG0")
    private long visibleG0;
    @Column(name = "visibleG1")
    private long visibleG1;
    @Column(name = "visibleG2")
    private long visibleG2;
    @Column(name = "visibleG3")
    private long visibleG3;
    @Column(name = "visibleG4")
    private long visibleG4;
    @Column(name = "visibleG5")
    private long visibleG5;
    @Column(name = "visibleG6")
    private long visibleG6;
    @Column(name = "visibleG7")
    private long visibleG7;

    /* Getters */
    public long getId() {
        return id;
    }

    public long getGuildId() {
        return guildId;
    }

    public long getEconomy() {
        return economy;
    }

    public long getDefense() {
        return defense;
    }

    public long getTriggerE() {
        return triggerE;
    }

    public long getTriggerD() {
        return triggerD;
    }

    public long getNextTime() {
        return nextTime;
    }

    public long getPayTime() {
        return payTime;
    }

    public long getCreateTime() {
        return createTime;
    }

    public long getVisibleC() {
        return visibleC;
    }

    public long getVisibleG0() {
        return visibleG0;
    }

    public long getVisibleG1() {
        return visibleG1;
    }

    public long getVisibleG2() {
        return visibleG2;
    }

    public long getVisibleG3() {
        return visibleG3;
    }

    public long getVisibleG4() {
        return visibleG4;
    }

    public long getVisibleG5() {
        return visibleG5;
    }

    public long getVisibleG6() {
        return visibleG6;
    }

    public long getVisibleG7() {
        return visibleG7;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public void setEconomy(long economy) {
        this.economy = economy;
    }

    public void setDefense(long defense) {
        this.defense = defense;
    }

    public void setTriggerE(long triggerE) {
        this.triggerE = triggerE;
    }

    public void setTriggerD(long triggerD) {
        this.triggerD = triggerD;
    }

    public void setNextTime(long nextTime) {
        this.nextTime = nextTime;
    }

    public void setPayTime(long payTime) {
        this.payTime = payTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public void setVisibleC(long visibleC) {
        this.visibleC = visibleC;
    }

    public void setVisibleG0(long visibleG) {
        this.visibleG0 = visibleG;
    }

    public void setVisibleG1(long visibleG) {
        this.visibleG1 = visibleG;
    }

    public void setVisibleG2(long visibleG) {
        this.visibleG2 = visibleG;
    }

    public void setVisibleG3(long visibleG) {
        this.visibleG3 = visibleG;
    }

    public void setVisibleG4(long visibleG) {
        this.visibleG4 = visibleG;
    }

    public void setVisibleG5(long visibleG) {
        this.visibleG5 = visibleG;
    }

    public void setVisibleG6(long visibleG) {
        this.visibleG6 = visibleG;
    }

    public void setVisibleG7(long visibleG) {
        this.visibleG7 = visibleG;
    }
}
