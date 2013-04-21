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
@Table(name = "guild_skill")
public class GuildSkill implements Serializable {

    public GuildSkill() {
    }
    @Id
    @Column(name = "id")
    private long id;
    @Id
    @Column(name = "guildId")
    private long guildId;
    @Column(name = "lv")
    private int level;

    /* Getters */
    public long getId() {
        return id;
    }

    public long getGuildId() {
        return guildId;
    }

    public int getLevel() {
        return level;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
