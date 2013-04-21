package com.dvlcube.model.character;

import com.dvlcube.model.GenericItem;
import com.dvlcube.model.Item;
import com.dvlcube.model.ItemInfo;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.criterion.Restrictions;

/**
 * @author Wonka
 */
@Entity
@Table(name = "inventory")
public class Inventory extends ItemInfo implements Serializable, GenericItem {

    public Inventory() {
    }

    public Inventory(long charId, Item item) {
        this.charId = charId;
        this.item = item;
        this.amount = 1;
        this.equipLocation = 0;
        this.isIdentified = true;
        if (item.isRefinable()) {
            this.refineRate = (int) (Math.random() * 11);
        } else {
            this.refineRate = 0;
        }
        this.attribute = 0;
        this.expireTime = 0;
        this.card00 = 0;
        this.card01 = 0;
        this.card02 = 0;
        this.card03 = 0;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "char_id")
    private long charId;
    @ManyToOne
    @JoinColumn(name = "nameid")
    private Item item;
    @Column(name = "amount")
    private int amount;
    @Column(name = "equip")
    private int equipLocation;
    @Column(name = "identify")
    private boolean isIdentified;
    @Column(name = "refine")
    private int refineRate;
    @Column(name = "attribute")
    private int attribute;
    @Column(name = "card0")
    private int card00;
    @Column(name = "card1")
    private int card01;
    @Column(name = "card2")
    private int card02;
    @Column(name = "card3")
    private int card03;
    @Transient
    private Item card0, card1, card2, card3;
    @Column(name = "expire_time")
    private long expireTime;
    @Transient
    public static final int MAX_ITEMS = 50;

    enum Location {

        HEAD_BOTTOM(1, "char.equip.HeadBottom"),
        R_HAND(2, "char.equip.RHand"),
        GARMENT(4, "char.equip.Garment"),
        ACC_RIGHT(8, "char.equip.AccRight"),
        ARMOR(16, "char.equip.Armor"),
        L_HAND(32, "char.equip.LHand"),
        FOOTGEAR(64, "char.equip.Footgear"),
        ACC_LEFT(128, "char.equip.AccLeft"),
        HEAD_TOP(256, "char.equip.HeadTop"),
        HEAD_MID(512, "char.equip.HeadMid");
        public int code;
        public String i18nString;

        Location(int code, String i18nString) {
            this.code = code;
            this.i18nString = i18nString;
        }

        public static String getString(int code) {
            String string = null;
            for (Location location : Location.values()) {
                if (location.code == code) {
                    string = location.i18nString;
                }
            }
            return string;
        }
    }

    /* Getters */
    public long getId() {
        return id;
    }

    /**
     * @return the id of the Item that is represented by this Inventory Item. (calls item.getItem())
     */
    @Override
    public long getItemId() {
        return item.getId();
    }

    public long getCharId() {
        return charId;
    }

    @Override
    public Item getItem() {
        return item;
    }

    @Override
    public int getAmount() {
        return amount;
    }

    /**
     * Used to know whether an Inventory item is equipped, and where it is equipped.
     * @return The body part code. This code is used to load both the i18n body part name and image.
     */
    public String getEquipLocation() {
        return Location.getString(equipLocation);
    }

    @Override
    public boolean isIdentified() {
        return isIdentified;
    }

    @Override
    public int getRefineRate() {
        return refineRate;
    }

    @Override
    public int getAttribute() {
        return attribute;
    }

    @Override
    public Item getCard0() {
        return (Item) Query.getAssociation(card00, card0, Item.class);
    }

    @Override
    public Item getCard1() {
        return (Item) Query.getAssociation(card01, card1, Item.class);
    }

    @Override
    public Item getCard2() {
        return (Item) Query.getAssociation(card02, card2, Item.class);
    }

    @Override
    public Item getCard3() {
        return (Item) Query.getAssociation(card03, card3, Item.class);
    }

    @Override
    public long getExpireTime() {
        return expireTime;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setCharId(long id) {
        this.charId = id;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    public void setAmount(int amount) {
        this.amount += amount;
        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    public void setIdentified(boolean isIdentified) {
        this.isIdentified = isIdentified;
    }

    public void setRefineRate(int refineRate) {
        this.refineRate = refineRate;
    }

    public void setAttribute(int attribute) {
        this.attribute = attribute;
    }

    public void setExpireTime(long time) {
        this.expireTime = time;
    }

    @Override
    public String getName() {
        return this.item.getJapaneseName();
    }

    @Override
    public String getDetailedName() {
        return ItemInfo.getDetailedName(this);
    }

    @Override
    public String getTitle() {
        return ItemInfo.getTitle(this);
    }

    /* Helpers */
    /**
     * Creates randomized Items and stores in the Inventory of the specified List of Chars.
     * TODO consider Char weight limit and different items limit.
     * @param dao The Data Access Object;
     * @param chars The List of Chars;
     * @param n The number of random Items to create.
     */
    public static void randomize(List<Char> chars, int n, DAO dao) {
        dao.open();
        try {
            for (Char character : chars) {
                for (int i = 0; i < n; i++) {
                    Item item = Item.getRandom(dao);
                    insertOrUpdate(character.getId(), item, new Query());
                }
            }
            dao.commit();
        } catch (Exception e) {
            dao.rollBack();
        } finally {
            dao.closeSession();
        }
    }

    /**
     * Creates or updates an Item in the specified Char [Inventory].
     * Stackable Items need to be checked whether they already exist in the Inventory, so only its amount gets updated.
     * @param dao The Data Access Object;
     * @param charId The Char id;
     * @param item The Item to be stored.
     */
    public static void insertOrUpdate(long charId, Item item, DAO dao) {
        if (item.isStackable()) {
            Inventory inventoryItem = (Inventory) dao.getUniqueResult(
                    Inventory.class,
                    Restrictions.eq("charId", charId),
                    Restrictions.eq("item.id", item.getId()));
            dao.open();
            if (inventoryItem == null) {
                if (!Inventory.isFull(charId, new Query())) {
                    inventoryItem = new Inventory(charId, item);
                    dao.set(inventoryItem);
                }
            } else {
                inventoryItem.setAmount(+1);
                dao.update(item);
            }
            dao.close();
        } else {
            if (!Inventory.isFull(charId, new Query())) {
                dao.open();
                Inventory storageItem = new Inventory(charId, item);
                dao.set(storageItem);
                dao.close();
            }
        }
    }

    /**
     * Counts all the Inventory items carried by the specified Char.
     * @param dao The Data Access Object;
     * @param charId The Char id.
     * @return <code>true</code> if the Inventory has more items than the specified in Inventory.MAX_ITEMS, <code>false</code> otherwise.
     */
    public static boolean isFull(long charId, DAO dao) {
        Long itemCount = (Long) dao.query(
                "select count(id) from Inventory where charId = " + charId);
        if (itemCount != null) {
            if (itemCount >= Inventory.MAX_ITEMS) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return whether this Inventory Item can be equipped.
     */
    boolean isEquip() {
        return item.isRefinable() ? true : false;
    }
}
