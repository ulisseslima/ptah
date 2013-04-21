package com.dvlcube.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Wonka
 */
@Entity
@Table(name = "skill_db")
public class Skill implements Serializable {

    public Skill() {
    }

    public Skill(String[] column) {
        this.id = Long.parseLong(column[0]);
        this.range = column[1];
        this.hit = Integer.parseInt(column[2]);
        this.info = Integer.parseInt(column[3]);
        this.element = column[4];
        this.damageType = column[5];
        this.splash = column[6];
        this.maxLevel = Integer.parseInt(column[7]);
        this.hitNum = column[8];
        this.isCancelable = column[9];
        this.castDefense = Integer.parseInt(column[10]);
        this.info2 = column[11];
        this.maxInstances = column[12];
        this.skillType = column[13];
        this.blowCount = column[14];
        this.internalName = column[15];
        this.name = column[16];
    }
    @Id
    @Column(name = "id")
    private long id;
    @Column(name = "`range`")
    private String range;
    @Column(name = "hit")
    private int hit;
    @Column(name = "inf")
    private int info;
    @Column(name = "element")
    private String element;
    @Column(name = "nk")
    private String damageType;
    @Column(name = "splash")
    private String splash;
    @Column(name = "max_lv")
    private int maxLevel;
    @Column(name = "list_num")
    private String hitNum;
    @Column(name = "castcancel")
    private String isCancelable;
    @Column(name = "cast_defense_rate")
    private int castDefense;
    @Column(name = "inf2")
    private String info2;
    @Column(name = "maxcount")
    private String maxInstances;
    @Column(name = "skill_type")
    private String skillType;
    @Column(name = "blow_count")
    private String blowCount;
    @Column(name = "name")
    private String internalName;
    @Column(name = "description")
    private String name;

    /* Getters */
    public long getId() {
        return id;
    }

    public String getRange() {
        return range;
    }

    public int getHit() {
        return hit;
    }

    /**
     * @return 0- passive, 1- enemy, 2- place, 4- self, 16- friend, 32- trap
     */
    public int getInfo() {
        return info;
    }

    /**
     * @return whether this Skill is registered as passive.
     */
    public boolean isPassive() {
        return info == 0 ? true : false;
    }

    /**
     * @return element (0- neutral, 1- water, 2- earth, 3- fire, 4- wind, 5- poison,
     *                  6- holy, 7- dark, 8- ghost, 9- undead, -1- use weapon element)
     */
    public int getElement(int skillLevel) {
        if (element.contains(":")) {
            int fixedElement = Integer.parseInt(element.split(":")[skillLevel]);
            return fixedElement;
        }
        return Integer.parseInt(element);
    }

    public String getDamageType() {
        return damageType;
    }

    public String getSplash() {
        return splash;
    }

    public int getMaxLevel() {
        return maxLevel;
    }

    public String getHitNum() {
        return hitNum;
    }

    public boolean isCancelable() {
        if (this.isCancelable.equals("yes")) {
            return true;
        } else {
            return false;
        }
    }

    public int getCastDefense() {
        return castDefense;
    }

    public String getInfo2() {
        return info2;
    }

    /**
     * @return whether this Skill is obtained via NPC quests.
     */
    public boolean isQuestSkill() {
        int hexInfo = 0;
        try {
            if (info2.contains("x")) {
                hexInfo = Integer.parseInt(info2.split("x")[1], 16);
            }
        } catch (Exception e) {
            return false;
        }
        if (hexInfo == 0) {
            return false;
        }
        return (hexInfo & Integer.parseInt("1", 16)) != 0 ? true : false;
    }

    /**
     * @return whether this skill is a wedding skill
     */
    public boolean isWeddingSkill(){
        int hexInfo = 0;
        try {
            if (info2.contains("x")) {
                hexInfo = Integer.parseInt(info2.split("x")[1], 16);
            }
        } catch (Exception e) {
            return false;
        }
        if (hexInfo == 0) {
            return false;
        }
        return (hexInfo & Integer.parseInt("4", 16)) != 0 ? true : false;
    }

    public String getMaxInstances() {
        return maxInstances;
    }

    public String getSkillType() {
        return skillType;
    }

    public String getBlowCount() {
        return blowCount;
    }

    public String getInternalName() {
        return internalName;
    }

    public String getName() {
        return name;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public void setHit(int hit) {
        this.hit = hit;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public void setElement(String element) {
        this.element = element;
    }

    public void setDamageType(String damageType) {
        this.damageType = damageType;
    }

    public void setSplash(String splash) {
        this.splash = splash;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public void setHitNum(String hitNum) {
        this.hitNum = hitNum;
    }

    public void setCancelable(String isCancelable) {
        if (isCancelable.equals("yes")) {
            this.isCancelable = "yes";
        } else {
            this.isCancelable = "no";
        }
    }

    public void setCastDefense(int castDefense) {
        this.castDefense = castDefense;
    }

    public void setInfo2(String info2) {
        this.info2 = info2;
    }

    public void setMaxInstances(String maxInstances) {
        this.maxInstances = maxInstances;
    }

    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }

    public void setBlowCount(String blowCount) {
        this.blowCount = blowCount;
    }

    public void setInternalName(String internalName) {
        this.internalName = internalName;
    }

    public void setName(String name) {
        this.name = name;
    }
}
