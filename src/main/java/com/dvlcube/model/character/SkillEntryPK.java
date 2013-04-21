package com.dvlcube.model.character;

import com.dvlcube.model.Skill;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Wonka
 */
@Embeddable
public class SkillEntryPK implements Serializable {

    public SkillEntryPK() {
    }
    @ManyToOne
    @JoinColumn(name = "id")
    private Skill skill;
    @ManyToOne
    @JoinColumn(name = "char_id")
    private Char char_;

    SkillEntryPK(Skill skill, Char character) {
        this.skill = skill;
        this.char_ = character;
    }

    /* Getters */
    public Skill getSkill() {
        return skill;
    }

    public Char getChar() {
        return char_;
    }
}
