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
@Table(name = "guild_position")
public class GuildPosition implements Serializable {
    public GuildPosition(){}

    @Id    
    @Column(name = "guild_id")
    private long guildId;
    @Id
    @Column(name = "position")
    private int position;
    @Column(name = "name")
    private String name;
    @Column(name = "mode")
    private int mode;
    @Column(name = "exp_mode")
    private int expDonation;

    /* Getters */
    public long getGuildId() {
        return guildId;
    }
    public int getPosition(){
        return position;
    }
    public String getName(){
        return name;
    }
    public int getMode(){
        return mode;
    }
    public int getExpDonation(){
        return expDonation;
    }

    /* Setters */
    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }
    public void setPosition(int position){
        this.position = position;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setMode(int mode){
        this.mode = mode;
    }
    public void setExpDonation(int donationPercentage){
        this.expDonation = donationPercentage;
    }
}