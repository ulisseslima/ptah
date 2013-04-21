package com.dvlcube.model.guild;

import com.dvlcube.model.character.Char;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Wonka
 */
@Embeddable
public class GuildMemberPK implements Serializable {

    public GuildMemberPK() {
    }

    public GuildMemberPK(Char member) {
        this.guild = member.getGuild();
        this.player = member;
    }
    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;
    @ManyToOne
    @JoinColumn(name = "char_id")
    private Char player;

    /* Getters */
    public Guild getGuild() {
        return guild;
    }

    public Char getMember() {
        return player;
    }
}
