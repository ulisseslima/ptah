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
@Table(name = "mercenary")
public class Mercenary implements Serializable {
    public Mercenary(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "mer_id")
    private long id;
    @Column(name = "char_id")
    private long charId;
    @Column(name = "`class`")
    private int classType;
    @Column(name = "hp")
    private int hp;
    @Column(name = "sp")
    private int sp;
    @Column(name = "kill_counter")
    private int killCounter;
    @Column(name = "life_time")
    private int lifeTime;

    /* Getters */
    public long getId() {
        return id;
    }
    public long getCharId(){
        return charId;
    }
    public int getClassType(){
        return classType;
    }
    public int getHP(){
        return hp;
    }
    public int getSP(){
        return sp;
    }
    public int getKillCounter(){
        return killCounter;
    }
    public int getLifeTime(){
        return lifeTime;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }
    public void setCharId(long id){
        this.charId = id;
    }
    public void setClassType(int type){
        this.classType = type;
    }
    public void setHP(int hp){
        this.hp = hp;
    }
    public void setSP(int sp){
        this.sp = sp;
    }
    public void setKillCounter(int count){
        this.killCounter = count;
    }
    public void setLifeTime(int lifeTime){
        this.lifeTime = lifeTime;
    }
}