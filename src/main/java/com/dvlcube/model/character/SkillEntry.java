package com.dvlcube.model.character;

import com.dvlcube.model.Skill;
import com.dvlcube.persistence.DAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity
@Table(name = "skill")
public class SkillEntry implements Serializable {

    public SkillEntry() {
    }

    private SkillEntry(Skill skill, int level, Char character) {
        this.pk = new SkillEntryPK(skill, character);
        this.level = level;
    }
    @Id
    private SkillEntryPK pk = new SkillEntryPK();
    @Column(name = "lv")
    private int level;

    /* Getters */
    public Char getChar() {
        return pk.getChar();
    }

    public Skill getSkill() {
        return pk.getSkill();
    }

    public int getLevel() {
        return level;
    }

    /* Helpers */
    /**
     * Spends all available skill points, filling pre-requisites as necessary.
     * TODO improve method so it can update a previously set skill entry (raising its level).
     * TODO not spend skill points on quest skills (it just skips 'em, as it is)
     * @param dao The Data Access Object;
     * @param character The Char.
     */
    static void assign(DAO dao, Char character) {
        int preRequisite = 0;
        int requiredLevel = 1;
        List<String[]> skillTree = CharInfo.getSkillTree(character.getClassId());
        List<Integer> requirementsMet = new ArrayList<Integer>();
        dao.open();
        for (String[] skill : skillTree) {
            if (character.getSkillPoints() < 1) {
                break;
            }
            for (int i = 3; i < skill.length; i += 2) {
                preRequisite = Integer.parseInt(skill[i]);
                if (!requirementsMet.contains(preRequisite)) {
                    if (preRequisite > 0 && character.getSkillPoints() > 0) {
                        requiredLevel = Integer.parseInt(skill[i + 1]);
                        Skill requiredSkill = (Skill) dao.get(Skill.class, preRequisite);
                        //System.out.println("!! SkillEntry.java: required skill ---> " + requiredSkill.getName());
                        SkillEntry skillEntry = SkillEntry.add(requiredSkill, requiredLevel, character);
                        if (skillEntry != null) {
                            dao.set(skillEntry);
                            requirementsMet.add(preRequisite);
                        }
                    } else {
                        break; //no more pre-requisites or skill points left
                    }
                }
            }
            //requirements done, can add the target skill:
            int targetSkill = Integer.parseInt(skill[1]);
            if (!requirementsMet.contains(targetSkill)) {
                if (character.getSkillPoints() > 0) {
                    Skill newSkill = (Skill) dao.get(Skill.class, targetSkill);
                    if (!newSkill.isQuestSkill()) {
                        //System.out.println("@@ SkillEntry.java: new skill is ---> " + newSkill.getName());
                        int maxLevel = Integer.parseInt(skill[2]);
                        SkillEntry skillEntry = SkillEntry.add(newSkill, maxLevel, character);
                        if (skillEntry != null) {
                            dao.set(skillEntry);
                            requirementsMet.add(targetSkill);
                        }
                    }
                }
            }
        }
        dao.update(character); //to update skill points count
        dao.close();
    }

    /**
     * Adds a SkillEntry to a Char.
     * @param skill The skill id;
     * @param points The desired skill level;
     * @param character The target Char.
     * @return A new SkillEntry, or null if an exception occurs.
     */
    public static SkillEntry add(Skill skill, int points, Char character) {
        int level = character.spendSkillPoints(points);
        //System.out.println("## SkillEntry.java: adding new skill entry spending " + level + " points");
        if (level > 0) {
            SkillEntry skillEntry = new SkillEntry(skill, level, character);
            if (skillEntry != null) {
                return skillEntry;
            }
        }
        //System.out.println("## SkillEntry.java: entry creation fail... (insufficient points or null SkillEntry)");
        return null;
    }
}
