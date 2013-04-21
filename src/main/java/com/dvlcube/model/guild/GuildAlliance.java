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
@Table(name = "guild_alliance")
public class GuildAlliance implements Serializable {

    public GuildAlliance() {
    }
    @Id
    @Column(name = "alliance_id")
    private long allianceId;
    @Id
    @Column(name = "guild_id")
    private long guildId;
    @Column(name = "opposition")
    private boolean isOpposition;
    @Column(name = "name")
    private String name;


    /* Getters */
    public long getAllianceId() {
        return allianceId;
    }

    public long getGuildId() {
        return guildId;
    }

    public boolean isOpposition() {
        return isOpposition;
    }

    public String getName() {
        return name;
    }

    /* Setters */
    public void setAllianceId(long allianceId) {
        this.allianceId = allianceId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public void setOpposition(boolean isOpposition) {
        this.isOpposition = isOpposition;
    }

    public void setName(String name) {
        this.name = name;
    }
}
