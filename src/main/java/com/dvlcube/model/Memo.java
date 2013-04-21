package com.dvlcube.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Wonka
 */
@Entity
@Table(name = "memo")
public class Memo implements Serializable {

    public Memo() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "memo_id")
    private long id;
    @Column(name = "char_id")
    private long charId;
    @Column(name = "map")
    private String map;
    @Column(name = "x")
    private int x;
    @Column(name = "y")
    private int y;
    @Transient
    public static final String[] CAPITALS = {"prontera", "morocc", "geffen", "payon", "alberta", "rachel",
        "yuno", "lighthalzen", "einbroch", "amatsu", "gonryun", "izlude", "louyang", "niflheim"};

    /* Getters */
    public long getId() {
        return id;
    }

    public long getCharId() {
        return charId;
    }

    public String getMap() {
        return map;
    }

    public int getXCoordinates() {
        return x;
    }

    public int getYCoordinates() {
        return y;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setCharId(long id) {
        this.charId = id;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public void setXCoordinates(int x) {
        this.x = x;
    }

    public void setYCoordinates(int y) {
        this.y = y;
    }
}
