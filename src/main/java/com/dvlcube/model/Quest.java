package com.dvlcube.model;

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
@Table(name = "quest")
public class Quest implements Serializable {

    public Quest() {
    }
    @Id //PRIMARY KEY  (`char_id`,`quest_id`)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "char_id")
    private long charId;
    @Column(name = "quest_id")
    private int questId;
    @Column(name = "`state`")
    private String state;
    @Column(name = "`time`")
    private long time;
    @Column(name = "count1")
    private int count1;
    @Column(name = "count2")
    private int count2;
    @Column(name = "count3")
    private int count3;

    /* Getters */
    public long getCharId() {
        return charId;
    }

    public int getQuestId() {
        return questId;
    }

    public String getState() {
        return state;
    }

    public long getTime() {
        return time;
    }

    public int getCount1() {
        return count1;
    }

    public int getCount2() {
        return count2;
    }

    public int getCount3() {
        return count3;
    }

    /* Setters */
    public void setCharId(long id) {
        this.charId = id;
    }

    public void setQuestId(int id) {
        this.questId = id;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setCount1(int count) {
        this.count1 = count;
    }

    public void setCount2(int count) {
        this.count2 = count;
    }

    public void setCount3(int count) {
        this.count3 = count;
    }
}
