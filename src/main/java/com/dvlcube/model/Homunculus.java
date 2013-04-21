package com.dvlcube.model;

import com.dvlcube.model.character.Char;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity
@Table(name = "homunculus")
public class Homunculus implements Serializable {

    public Homunculus() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "homun_id")
    private long id;
    @OneToOne
    @JoinColumn(name = "char_id")
    private Char owner;
    @Column(name = "`class`")
    private int classId;
    @Column(name = "name")
    private String name;
    @Column(name = "`level`")
    private int level;
    @Column(name = "exp")
    private int exp;
    @Column(name = "intimacy")
    private int intimacy;
    @Column(name = "hunger")
    private int hunger;
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
    @Column(name = "hp")
    private int hp;
    @Column(name = "max_hp")
    private int maxHP;
    @Column(name = "sp")
    private int sp;
    @Column(name = "max_sp")
    private int maxSP;
    @Column(name = "skill_point")
    private int skillPoints;
    @Column(name = "alive")
    private boolean isAlive;
    @Column(name = "rename_flag")
    private boolean renameFlag;
    @Column(name = "vaporize")
    private boolean isVaporized;

    /* Getters */
    public long getId() {
        return id;
    }

    public Char getOwner() {
        return owner;
    }

    public int getClassName() {
        return classId;
    }

    public String getName() {
        return name;
    }

    public String getNameURL() {
        return name.replace(" ", "+");
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    public int getIntimacy() {
        return intimacy;
    }

    public int getHunger() {
        return hunger;
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

    public int getHP() {
        return hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getSP() {
        return sp;
    }

    public int getMaxSP() {
        return maxSP;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public boolean getRenameFlag() {
        return renameFlag;
    }

    public boolean isVaporized() {
        return isVaporized;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setOwner(Char owner) {
        this.owner = owner;
    }

    public void setClassName(int id) {
        this.classId = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setIntimacy(int intimacy) {
        this.intimacy = intimacy;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
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

    public void setHP(int HP) {
        this.hp = HP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setSP(int sp) {
        this.sp = sp;
    }

    public void setMaxSP(int maxSP) {
        this.maxSP = maxSP;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public void setAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    public void setRenameFlag(boolean renameFlag) {
        this.renameFlag = renameFlag;
    }

    public void setVaporized(boolean isVaporized) {
        this.isVaporized = isVaporized;
    }
}
