package com.dvlcube.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Wonka
 * For details: eathena.ws/wiki/index.php/Custom_Mobs
 */
@Entity
@Table(name = "mob_db")
public class Mob implements Serializable {

    public Mob() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;
    @Column(name = "Sprite")
    private String sprite;
    @Column(name = "kName")
    private String kName;
    @Column(name = "iName")
    private String iName;
    @Column(name = "LV")
    private int level;
    @Column(name = "HP")
    private int hp;
    @Column(name = "SP")
    private int sp;
    @Column(name = "EXP")
    private int exp;
    @Column(name = "JEXP")
    private int jobExp;
    /**
     * Range of the mob attack. If set to 1 or 2, it will melee. 3 or more than 3 will set it to ranged.
     */
    @Column(name = "Range1")
    private int range1;
    /**
     * Maximum range for skills.
     */
    @Column(name = "Range2")
    private int range2;
    /**
     * Sight limit for mobs. If set to 1000 or beyond, mobs will follow you all over the map.
     */
    @Column(name = "Range3")
    private int range3;
    /**
     * Minimum attack of the mob.
     */
    @Column(name = "ATK1")
    private int ATK1;
    /**
     * Maximum attack of the mob. If no maximum attack defined here, the minimum attack will count as the absolute attack.
     */
    @Column(name = "ATK2")
    private int ATK2;
    @Column(name = "DEF")
    private int DEF;
    @Column(name = "MDEF")
    private int MDEF;
    @Column(name = "STR")
    private int STR;
    @Column(name = "AGI")
    private int AGI;
    @Column(name = "VIT")
    private int VIT;
    @Column(name = "`INT`")
    private int INT;
    @Column(name = "DEX")
    private int DEX;
    @Column(name = "LUK")
    private int LUK;
    @Column(name = "Scale")
    private int scale;
    @Column(name = "Race")
    private int race;
    @Column(name = "Element")
    private int element;
    /**
     * This defines how the mob behaves. Read eathena.ws/wiki/index.php/Custom_Mobs for details.
     */
    @Column(name = "Mode")
    private int mode;
    @Column(name = "Speed")
    private int speed;
    @Column(name = "aDelay")
    private int attackDelay;
    /**
     * Attack animation motion. Lower this value and the mob's attack will be displayed in higher fps (making it shorter, too).
     */
    @Column(name = "aMotion")
    private int aMotion;
    /**
     * Damage animation motion, same as aMotion but used to display the "I am hit" animation. Coincidentally, this same value is used to determine how long it is before the mob/player can move again. Endure is dMotion = 0, obviously.
     */
    @Column(name = "dMotion")
    private int dMotion;
    @Column(name = "MEXP")
    private int mvpExp;
    @Column(name = "ExpPer")
    private int expPercentage;
    @Column(name = "MVP1id")
    private int mvp1id;
    @Column(name = "MVP1per")
    private int mvp1percentage;
    @Column(name = "MVP2id")
    private int mvp2id;
    @Column(name = "MVP2per")
    private int mvp2percentage;
    @Column(name = "MVP3id")
    private int mvp3id;
    @Column(name = "MVP3per")
    private int mvp3percentage;
    @Column(name = "Drop1id")
    private int drop1id;
    @Column(name = "Drop1per")
    private int drop1percentage;
    @Column(name = "Drop2id")
    private int drop2id;
    @Column(name = "Drop2per")
    private int drop2percentage;
    @Column(name = "Drop3id")
    private int drop3id;
    @Column(name = "Drop3per")
    private int drop3percentage;
    @Column(name = "Drop4id")
    private int drop4id;
    @Column(name = "Drop4per")
    private int drop4percentage;
    @Column(name = "Drop5id")
    private int drop5id;
    @Column(name = "Drop5per")
    private int drop5percentage;
    @Column(name = "Drop6id")
    private int drop6id;
    @Column(name = "Drop6per")
    private int drop6percentage;
    @Column(name = "Drop7id")
    private int drop7id;
    @Column(name = "Drop7per")
    private int drop7percentage;
    @Column(name = "Drop8id")
    private int drop8id;
    @Column(name = "Drop8per")
    private int drop8percentage;
    @Column(name = "Drop9id")
    private int drop9id;
    @Column(name = "Drop9per")
    private int drop9percentage;
    @Column(name = "DropCardid")
    private int dropCardId;
    @Column(name = "DropCarper")
    private int dropCardPercentage;

    /** Getters **/
    public long getId() {
        return id;
    }

    public String getSprite() {
        return sprite;
    }

    public String getKName() {
        return kName;
    }

    public String getIName() {
        return iName;
    }

    public int getLevel() {
        return level;
    }

    public int getHP() {
        return hp;
    }

    public int getSP() {
        return sp;
    }

    public int getExp() {
        return exp;
    }

    public int getJobExp() {
        return jobExp;
    }

    public int getRage1() {
        return range1;
    }

    public int getRage2() {
        return range1;
    }

    public int getRage3() {
        return range1;
    }

    public int getAtk1() {
        return ATK1;
    }

    public int getAtk2() {
        return ATK2;
    }

    public int getDef() {
        return DEF;
    }

    public int getMDef() {
        return MDEF;
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

    public int getScale() {
        return scale;
    }

    public int getRace() {
        return race;
    }

    public int getElement() {
        return element;
    }

    public int getMode() {
        return mode;
    }

    public int getSpeed() {
        return speed;
    }

    public int getAttackDelay() {
        return attackDelay;
    }

    public int getAMotion() {
        return aMotion;
    }

    public int getDMotion() {
        return dMotion;
    }

    public int getMVPExp() {
        return mvpExp;
    }

    public int getExpPercentage() {
        return expPercentage;
    }

    public int getMvpDrop1id() {
        return mvp1id;
    }

    public int getMvpDrop1Percentage() {
        return mvp1percentage;
    }

    public int getMvpDrop2id() {
        return mvp2id;
    }

    public int getMvpDrop2Percentage() {
        return mvp2percentage;
    }

    public int getMvpDrop3id() {
        return mvp3id;
    }

    public int getMvpDrop3Percentage() {
        return mvp3percentage;
    }

    public int getDrop1id() {
        return drop1id;
    }

    public int getDrop1Percentage() {
        return drop1percentage;
    }

    public int getDrop2id() {
        return drop2id;
    }

    public int getDrop2Percentage() {
        return drop2percentage;
    }

    public int getDrop3id() {
        return drop3id;
    }

    public int getDrop3Percentage() {
        return drop3percentage;
    }

    public int getDrop4id() {
        return drop4id;
    }

    public int getDrop4Percentage() {
        return drop4percentage;
    }

    public int getDrop5id() {
        return drop5id;
    }

    public int getDrop5Percentage() {
        return drop5percentage;
    }

    public int getDrop6id() {
        return drop6id;
    }

    public int getDrop6Percentage() {
        return drop6percentage;
    }

    public int getDrop7id() {
        return drop7id;
    }

    public int getDrop7Percentage() {
        return drop7percentage;
    }

    public int getDrop8id() {
        return drop8id;
    }

    public int getDrop8Percentage() {
        return drop8percentage;
    }

    public int getDrop9id() {
        return drop9id;
    }

    public int getDrop9Percentage() {
        return drop9percentage;
    }

    public int getDropCardId() {
        return dropCardId;
    }

    public int getDropCardPercentage() {
        return dropCardPercentage;
    }

    /** Setters **/
    public void setId(long id) {
        this.id = id;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public void setKName(String kName) {
        this.kName = kName;
    }

    public void setIName(String iName) {
        this.iName = iName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public void setSP(int sp) {
        this.sp = sp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public void setJobExp(int jobExp) {
        this.jobExp = jobExp;
    }

    public void setRange1(int range) {
        this.range1 = range;
    }

    public void setRange2(int range) {
        this.range1 = range;
    }

    public void setRange3(int range) {
        this.range1 = range;
    }

    public void setAtk1(int atk) {
        this.ATK1 = atk;
    }

    public void setAtk2(int atk) {
        this.ATK2 = atk;
    }

    public void setDef(int def) {
        this.DEF = def;
    }

    public void setMDef(int mdef) {
        this.MDEF = mdef;
    }

    public void setStr(int str) {
        this.STR = str;
    }

    public void setAgi(int agi) {
        this.AGI = agi;
    }

    public void setVit(int vit) {
        this.VIT = vit;
    }

    public void setInt(int INT) {
        this.INT = INT;
    }

    public void setDex(int dex) {
        this.DEX = dex;
    }

    public void setLuk(int luk) {
        this.LUK = luk;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

    public void setRace(int race) {
        this.race = race;
    }

    public void setElement(int element) {
        this.element = element;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setAttackDelay(int delay) {
        this.attackDelay = delay;
    }

    public void setAMotion(int aMotion) {
        this.aMotion = aMotion;
    }

    public void setDMotion(int dMotion) {
        this.dMotion = dMotion;
    }

    public void setMVPExp(int exp) {
        this.mvpExp = exp;
    }

    public void setExpPercentage(int percentage) {
        this.expPercentage = percentage;
    }

    public void setMvpDrop1id(int id) {
        this.mvp1id = id;
    }

    public void setMvpDrop1Percentage(int percentage) {
        this.mvp1percentage = percentage;
    }

    public void setMvpDrop2id(int id) {
        this.mvp2id = id;
    }

    public void setMvpDrop2Percentage(int percentage) {
        this.mvp2percentage = percentage;
    }

    public void setMvpDrop3id(int id) {
        this.mvp3id = id;
    }

    public void setMvpDrop3Percentage(int percentage) {
        this.mvp3percentage = percentage;
    }

    public void setDrop1id(int id) {
        this.drop1id = id;
    }

    public void setDrop1Percentage(int percentage) {
        this.drop1percentage = percentage;
    }

    public void setDrop2id(int id) {
        this.drop2id = id;
    }

    public void setDrop2Percentage(int percentage) {
        this.drop2percentage = percentage;
    }

    public void setDrop3id(int id) {
        this.drop3id = id;
    }

    public void setDrop3Percentage(int percentage) {
        this.drop3percentage = percentage;
    }

    public void setDrop4id(int id) {
        this.drop4id = id;
    }

    public void setDrop4Percentage(int percentage) {
        this.drop4percentage = percentage;
    }

    public void setDrop5id(int id) {
        this.drop5id = id;
    }

    public void setDrop5Percentage(int percentage) {
        this.drop5percentage = percentage;
    }

    public void setDrop6id(int id) {
        this.drop6id = id;
    }

    public void setDrop6Percentage(int percentage) {
        this.drop6percentage = percentage;
    }

    public void setDrop7id(int id) {
        this.drop7id = id;
    }

    public void setDrop7Percentage(int percentage) {
        this.drop7percentage = percentage;
    }

    public void setDrop8id(int id) {
        this.drop8id = id;
    }

    public void setDrop8Percentage(int percentage) {
        this.drop8percentage = percentage;
    }

    public void setDrop9id(int id) {
        this.drop9id = id;
    }

    public void setDrop9Percentage(int percentage) {
        this.drop9percentage = percentage;
    }

    public void setDropCardId(int id) {
        this.dropCardId = id;
    }

    public void setDropCardPercentage(int percentage) {
        this.dropCardPercentage = percentage;
    }
}
