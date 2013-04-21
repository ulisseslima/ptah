package com.dvlcube.model;

import com.dvlcube.persistence.DAO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @author Wonka
 */
@Entity
@Table(name = "item_db")
public class Item implements Serializable {

    /**
     * TODO Warning: loops until it finds an existing Item id. Can be improved.
     * @param dao The Data Access Object.
     * @return a random Item.
     */
    public static Item getRandom(DAO dao) {
        Item item = null;
        while (item == null) {
            long randomId = (int) (Math.random() * Item.MAX) + Item.MIN;
            item = (Item) dao.get(Item.class, randomId);
        }
        return item;
    }

    public Item() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "name_english")
    private String englishName;
    @Column(name = "name_japanese")
    private String japaneseName;
    @Column(name = "type")
    private int type;
    @Column(name = "price_buy")
    private Long buyPrice;
    @Column(name = "price_sell")
    private Long sellPrice;
    @Column(name = "weight")
    private int weight;
    @Column(name = "attack")
    private Integer attack;
    @Column(name = "defence")
    private Integer defense;
    @Column(name = "range")
    private Integer range;
    @Column(name = "slots")
    private Integer slots;
    @Column(name = "equip_jobs")
    private Long equipJobs;
    @Column(name = "equip_upper")
    private Integer equipUpper;
    @Column(name = "equip_genders")
    private Integer equipGenders;
    @Column(name = "equip_locations")
    private Integer equipLocations;
    @Column(name = "weapon_level")
    private Integer weaponLevel;
    @Column(name = "equip_level")
    private Integer equipLevel;
    @Column(name = "refineable")
    private Boolean isRefineable;
    @Column(name = "`view`")
    private Integer view;
    @Column(name = "script")
    private String script;
    @Column(name = "equip_script")
    private String equipScript;
    @Column(name = "unequip_script")
    private String unequipScript;
    @Transient
    public static final int MIN = 501, MAX = 14594;

    /* Getters */
    public long getId() {
        return id;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getJapaneseName() {
        return japaneseName;
    }

    public String getName() {
        return japaneseName;
    }

    public int getType() {
        return type;
    }

    public Long getBuyPrice() {
        return buyPrice;
    }

    public Long getSellPrice() {
        return sellPrice;
    }

    public int getWeight() {
        return weight;
    }

    public Integer getAttack() {
        return attack;
    }

    public Integer getDefense() {
        return defense;
    }

    public Integer getRange() {
        return range;
    }

    public Integer getSlots() {
        return slots;
    }

    public Long getEquipJobs() {
        return equipJobs;
    }

    public Integer getEquipUpper() {
        return equipUpper;
    }

    public Integer getEquipGenders() {
        return equipGenders;
    }

    public Integer getEquipLocations() {
        return equipLocations;
    }

    public Integer getWeaponLevel() {
        return weaponLevel;
    }

    public Integer getEquipLevel() {
        return equipLevel;
    }

    public Boolean isRefinable() {
        return isRefineable == null ? false : isRefineable;
    }

    public Integer getView() {
        return view;
    }

    public String getScript() {
        return script;
    }

    public String getEquipScript() {
        return equipScript;
    }

    public String getUnequipScript() {
        return unequipScript;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setEnglishName(String name) {
        this.englishName = name;
    }

    public void setJapaneseName(String name) {
        this.japaneseName = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setBuyPrice(Long buyPrice) {
        this.buyPrice = buyPrice;
    }

    public void setSellPrice(Long sellPrice) {
        this.sellPrice = sellPrice;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public void setSlots(Integer slots) {
        this.slots = slots;
    }

    public void setEquipJobs(Long equipJobs) {
        this.equipJobs = equipJobs;
    }

    public void setEquipUpeer(Integer equipUpper) {
        this.equipUpper = equipUpper;
    }

    public void setEquipGenders(Integer equipGenders) {
        this.equipGenders = equipGenders;
    }

    public void setEquipLocations(Integer equipLocations) {
        this.equipLocations = equipLocations;
    }

    public void setWeaponLevel(Integer weaponLevel) {
        this.weaponLevel = weaponLevel;
    }

    public void setEquipLevel(Integer equipLevel) {
        this.equipLevel = equipLevel;
    }

    public void setRefineable(Boolean isRefineable) {
        this.isRefineable = isRefineable;
    }

    public void setView(Integer view) {
        this.view = view;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public void setEquipScript(String equipScript) {
        this.equipScript = equipScript;
    }

    public void setUnequipScript(String unequipScript) {
        this.unequipScript = unequipScript;
    }

    /* Helpers */
    /*
     * Whether this item can be stacked in an Inventory or Storage.
     */
    public boolean isStackable() {
        if (type < 4
                || type == 6
                || type > 9) {
            return true;
        }
        return false;
    }

    /**
     * @return whether this Item is usable.
     */
    public boolean isUsable() {
        return ItemGroup.isUsable(this.id);
    }

    /**
     * @return Whether this Item is refinable.
     */
    @Deprecated
    public boolean isRefinable2() {
        return ItemGroup.isRefinable(id);
    }
}
