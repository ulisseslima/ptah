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
@Table(name = "guild_expulsion")
public class GuildExpulsion implements Serializable {

    public GuildExpulsion() {
    }
    @Id
    @Column(name = "guild_id")
    private long guildId;
    @Id
    @Column(name = "name")
    private String name;
    @Column(name = "account_id")
    private long accountId;
    @Column(name = "mes")
    private String message;

    /* Getters */
    public long getGuildId() {
        return guildId;
    }

    public String getName() {
        return name;
    }

    public long getAccountId() {
        return accountId;
    }

    public String getMessage() {
        return message;
    }

    /* Setters */
    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
