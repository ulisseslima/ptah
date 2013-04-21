package com.dvlcube.model.character;

import com.dvlcube.model.Item;
import com.dvlcube.model.Skill;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.criterion.Restrictions;

/**
 * @author Wonka
 */
@Entity
@Table(name = "hotkey")
public class Hotkey implements Serializable {

    public Hotkey() {
    }

    public Hotkey(int hotkey, SkillEntry skill, Char character) {
        this.pk = new HotkeyPK(hotkey, character);
        this.itemOrSkillId = skill.getSkill().getId();
        this.type = 1;
        this.skillLevel = skill.getLevel();
    }

    public Hotkey(int hotkey, Inventory item, Char character) {
        this.pk = new HotkeyPK(hotkey, character);
        this.itemOrSkillId = item.getItemId();
        this.type = 0;
        this.skillLevel = 0;
    }
    @Id
    private HotkeyPK pk = new HotkeyPK();
    @Column(name = "itemskill_id")
    private long itemOrSkillId;
    @Column(name = "type")
    private int type;
    @Column(name = "skill_lvl")
    private int skillLevel;
    @Transient
    public static int MAX = 26;

    /* Getters */
    public Char getChar() {
        return pk.getChar();
    }

    public int getHotkey() {
        return pk.getHotkey();
    }

    /**
     * @return 1 if this Hotkey holds a Skill, 0 if it's an Item.
     */
    public int getType() {
        return type;
    }

    public long getItemOrSkillId() {
        return itemOrSkillId;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    /* Helpers */
    public Object getEntry() {
        DAO dao = new Query();
        if (this.type == 1) {
            Skill skill = (Skill) dao.getUniqueResult(Skill.class, Restrictions.eq("id", getItemOrSkillId()));
            return skill;
        } else {
            Item item = (Item) dao.getUniqueResult(Item.class, Restrictions.eq("id", getItemOrSkillId()));
            return item;
        }
    }

    /**
     * Assigns Hotkeys to the assigned SkillEntries.
     * @param dao The Data Access Object;
     * @param chars The target list of Chars.
     */
    public static void assign(List<Char> chars, DAO dao) {
        for (Char character : chars) {
            List<SkillEntry> skills = skills = character.getSkills(dao);
            List<Inventory> inventory = character.getInventory(dao);

            int i = 0;
            if (skills != null) {
                dao.open();
                for (SkillEntry entry : skills) {
                    if (i > Hotkey.MAX) {
                        break; //can't assign more Hotkeys
                    } else {
                        if (!entry.getSkill().isPassive()) {
                            entry.getSkill().getSkillType();
                            Hotkey hotkey = new Hotkey(i, entry, character);
                            dao.set(hotkey);
                            i++;
                        }
                    }
                }
                dao.close();
            }
            if (inventory != null) {
                dao.open();
                for (Inventory item : inventory) {
                    if (i > Hotkey.MAX) {
                        break; //can't assign more Hotkeys
                    } else {
                        if (item.getItem().isUsable()
                                || (item.isIdentified() && item.isEquip())) {
                            Hotkey hotkey = new Hotkey(i, item, character);
                            dao.set(hotkey);
                            i++;
                        }
                    }
                }
                dao.close();
            }
        }
    }
}
