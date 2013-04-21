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
@Table(name = "party")
public class Party implements Serializable {

    public Party() {
    }

    public Party(String name, Login account, Char leader){
        this.name = name;
        this.isSharingExp = true;
        this.itemShareConfig = 0;
        this.leaderAccount = account;
        this.leader = leader;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "party_id")
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "exp")
    private boolean isSharingExp;
    @Column(name = "item")
    private int itemShareConfig;
    @OneToOne
    @JoinColumn(name = "leader_id")
    private Login leaderAccount;
    @OneToOne
    @JoinColumn(name = "leader_char")
    private Char leader;

    /* Getters */
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isSharingExp() {
        return isSharingExp;
    }

    public int getItemShareConfig() {
        return itemShareConfig;
    }

    public Login getLeaderAccount() {
        return leaderAccount;
    }

    public Char getLeader() {
        return leader;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSharingExp(boolean isSharingExp) {
        this.isSharingExp = isSharingExp;
    }

    public void setItemShareConfig(int itemShareConfig) {
        this.itemShareConfig = itemShareConfig;
    }

    public void setLeaderAccount(Login account) {
        this.leaderAccount = account;
    }

    public void setLeader(Char character) {
        this.leader = character;
    }
}
