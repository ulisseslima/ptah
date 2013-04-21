package com.dvlcube.model.guild;

import com.dvlcube.model.character.Char;
import com.dvlcube.persistence.DAO;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author Wonka
 */
@Entity
@Table(name = "guild")
public class Guild implements Serializable {

    /**
     * Note: a char can't own two guilds.
     */
    public Guild() {
    }

    public Guild(String name, Char character) {
        this.name = name;
        this.owner = character;
        this.ownerName = character.getName();
        this.level = 1;
        this.connectMember = 0;
        this.maxMembers = 16;
        this.averageLevel = character.getBaseLevel();
        this.exp = 0;
        this.nextExp = 2000000;
        this.skillPoints = 0;
        this.message1 = name;
        this.message2 = "!";
        this.emblemLength = 0;
        this.emblemId = 0;
        this.emblemData = null;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "guild_id")
    private long id;
    @Column(name = "name")
    private String name;
    @OneToOne
    @JoinColumn(name = "char_id")
    private Char owner;
    @Column(name = "master")
    private String ownerName;
    @Column(name = "guild_lv")
    private int level;
    @Column(name = "connect_member")
    private int connectMember;
    @Column(name = "max_member")
    private int maxMembers;
    @Column(name = "average_lv")
    private int averageLevel;
    @Column(name = "exp")
    private long exp;
    @Column(name = "next_exp")
    private long nextExp;
    @Column(name = "skill_point")
    private long skillPoints;
    @Column(name = "mes1")
    private String message1;
    @Column(name = "mes2")
    private String message2;
    @Column(name = "emblem_len")
    private long emblemLength;
    @Column(name = "emblem_id")
    private long emblemId;
    @Lob
    @Column(name = "emblem_data")
    private Byte[] emblemData;

    /* Getters */
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Char getOwner() {
        return owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public int getLevel() {
        return level;
    }

    public int getConnectMember() {
        return connectMember;
    }

    public int getMaxMembers() {
        return maxMembers;
    }

    public int getAverageLevel() {
        return averageLevel;
    }

    public long getExp() {
        return exp;
    }

    public long getNextExp() {
        return nextExp;
    }

    public long getSkillPoints() {
        return skillPoints;
    }

    public String getMessage1() {
        return message1;
    }

    public String getMessage2() {
        return message2;
    }

    public long getEmblemLength() {
        return emblemLength;
    }

    public long getEmblemId() {
        return emblemId;
    }

    public Byte[] getEmblemData() {
        return emblemData;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(Char owner) {
        this.owner = owner;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setConnectMember(int connectMember) {
        this.connectMember = connectMember;
    }

    public void setMaxMembers(int maxMembers) {
        this.maxMembers = maxMembers;
    }

    public void setAverageLevel(int averageLevel) {
        this.averageLevel = averageLevel;
    }

    public void setExp(long exp) {
        this.exp = exp;
    }

    public void setNextExp(long nextExp) {
        this.nextExp = nextExp;
    }

    public void setSkillPoints(long skillPoints) {
        this.skillPoints = skillPoints;
    }

    public void setMessage1(String message) {
        this.message1 = message;
    }

    public void setMessage2(String message) {
        this.message2 = message;
    }

    public void setEmblemLength(long emblemLength) {
        this.emblemLength = emblemLength;
    }

    public void setEmblemId(long emblemId) {
        this.emblemId = emblemId;
    }

    public void setEmblemData(Byte[] emblemData) {
        this.emblemData = emblemData;
    }

    /* Helpers */
    /**
     * @param dao The Data Access Object.
     * @return all the Chars that joined this Guild.
     */
    public List<Char> getMembers(DAO dao) {
        List<Char> guildMembers = dao.getList(
                Char.class,
                Order.asc("name"),
                Restrictions.eq("guildId", this.id));
        return guildMembers;
    }
}
