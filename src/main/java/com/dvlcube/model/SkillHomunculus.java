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
@Table(name = "skill_homunculus")
public class SkillHomunculus implements Serializable {

    public SkillHomunculus() {
    }
    @Id //PRIMARY KEY  (`homun_id`,`id`),
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "homun_id")
    private long homunId;
    @Column(name = "id")
    private long id;
    @Column(name = "lv")
    private int level;

    /* Getters */
    public long getHomunId() {
        return homunId;
    }

    public long getId() {
        return id;
    }

    public long getLevel(){
        return level;
    }

    /* Setters */
    public void setHomunId(long id) {
        this.homunId = id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setLevel(int level){
        this.level = level;
    }
}
