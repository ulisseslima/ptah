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
@Table(name = "pet")
public class Pet implements Serializable {

    public Pet() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pet_id")
    private long id;
    @Column(name = "`class`")
    private int classType;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Login ownerAccount;
    @OneToOne
    @JoinColumn(name = "char_id")
    private Char owner;
    @Column(name = "`level`")
    private int level;
    @Column(name = "egg_id")
    private int eggId;
    @Column(name = "equip")
    private int equip;
    @Column(name = "intimate")
    private int intimacy;
    @Column(name = "hungry")
    private int hungerLevel;
    @Column(name = "rename_flag")
    private int renameFlag;
    @Column(name = "incuvate")
    private int incubate;

    /* Getters */
    public long getId() {
        return id;
    }

    public long getClassType() {
        return classType;
    }

    public String getName() {
        return name;
    }

    public Login getOwnerAccount() {
        return ownerAccount;
    }

    public Char getOwner() {
        return owner;
    }

    public long getLevel() {
        return level;
    }

    public int getEggId() {
        return eggId;
    }

    public int getEquip() {
        return equip;
    }

    public int getIntimacy() {
        return intimacy;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public int getRenameFlag() {
        return renameFlag;
    }

    public int getIncubate() {
        return incubate;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setClassType(int type) {
        this.classType = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwnerAccount(Login login) {
        this.ownerAccount = login;
    }

    public void setOwner(Char owner) {
        this.owner = owner;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setEggId(int id) {
        this.eggId = id;
    }

    public void setEquip(int equip) {
        this.equip = equip;
    }

    public void setIntimacy(int intimacy) {
        this.intimacy = intimacy;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public void setRenameFlag(int flag) {
        this.renameFlag = flag;
    }

    public void setIncubate(int incubate) {
        this.incubate = incubate;
    }
}
