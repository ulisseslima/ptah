package com.dvlcube.model.guild;

import com.dvlcube.model.character.Char;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity
@Table(name = "guild_member")
public class GuildMember implements Serializable {

    public GuildMember() {
    }

    public GuildMember(Char member) {
        this.pk = new GuildMemberPK(member);
        this.accountId = member.getAccount().getAccountId();
        this.hair = member.getHair();
        this.hairColor = member.getHairColor();
        this.gender = member.getAccount().getSex().equals("M") ? 1 : 0;
        this.classId = member.getClassId();
        this.level = member.getBaseLevel();
        this.exp = 0;
        this.percentExpPaid = 0;
        this.isOnline = false;
        try {
            this.position = member.getGuild().getOwnerName().equals(member.getName()) ? 0 : 16;
        } catch (Exception e) {
            this.position = 0;
        }
        this.name = member.getName();
    }
    @Id
    private GuildMemberPK pk = new GuildMemberPK();
    @Column(name = "account_id")
    private long accountId;
    @Column(name = "hair")
    private int hair;
    @Column(name = "hair_color")
    private int hairColor;
    @Column(name = "gender")
    private int gender;
    @Column(name = "`class`")
    private int classId;
    @Column(name = "lv")
    private int level;
    @Column(name = "exp")
    private long exp;
    @Column(name = "exp_payper")
    private long percentExpPaid;
    @Column(name = "online")
    private boolean isOnline;
    @Column(name = "position")
    private int position;
    @Column(name = "name")
    private String name;

    /* Getters */
    public Guild getGuild() {
        return pk.getGuild();
    }

    public Char getMember() {
        return pk.getMember();
    }

    public long getAccountId() {
        return accountId;
    }

    public int getHair() {
        return hair;
    }

    public int getHairColor() {
        return hairColor;
    }

    public int getGender() {
        return gender;
    }

    public int getClassId() {
        return classId;
    }

    public int getLevel() {
        return level;
    }

    public long getExp() {
        return exp;
    }

    public long getPercentExpPaid() {
        return percentExpPaid;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    /* Setters */
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setHair(int hairId) {
        this.hair = hairId;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setClassId(int classId) {
        this.classId = classId;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public void setPercentExpPaid(long percentExpPaid) {
        this.percentExpPaid = percentExpPaid;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setName(String name) {
        this.name = name;
    }
}
