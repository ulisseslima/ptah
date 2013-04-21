package com.dvlcube.model;

import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import java.io.Serializable;
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
 * Handles all Storage Items from all Accounts.
 * @author Wonka
 */
@Entity
@Table(name = "storage")
public class Storage implements Serializable, GenericItem {

    public Storage() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "account_id")
    private long accountId;
    @ManyToOne
    @JoinColumn(name = "nameid")
    private Item item;
    @Column(name = "amount")
    private int amount;
    @Column(name = "equip")
    private int equip;
    @Column(name = "identify")
    private int isIdentified;
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
    public static final int MAX_ITEMS = 600;

    /**
     * Creates a random Storage Item in the specified account.
     * @param accountId The account id;
     * @param item The Item to be stored.
     */
    public Storage(long accountId, Item item) {
        this.accountId = accountId;
        this.item = item;
        this.amount = 1;
        this.equip = 0;
        if (item.isRefinable()) {
            this.refineRate = (int) (Math.random() * 11);
            this.isIdentified = (int) (Math.random() * 10) > 4 ? 1 : 0;
        } else {
            this.refineRate = 0;
            this.isIdentified = 1;
        }
        this.attribute = 0;
        this.expireTime = 0;
        this.card00 = 0;
        this.card01 = 0;
        this.card02 = 0;
        this.card03 = 0;
    }

    /* Getters */
    public long getId() {
        return id;
    }

    public long getAccountId() {
        return accountId;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public int getEquip() {
        return equip;
    }

    public boolean isIdentified() {
        if (this.isIdentified > 0) {
            return true;
        }
        return false;
    }

    public int getRefineRate() {
        return refineRate;
    }

    public int getAttribute() {
        return attribute;
    }

    public Item getCard0() {
        return (Item) Query.getAssociation(card00, card0, Item.class);
    }

    public Item getCard1() {
        return (Item) Query.getAssociation(card01, card1, Item.class);
    }

    public Item getCard2() {
        return (Item) Query.getAssociation(card02, card2, Item.class);
    }

    public Item getCard3() {
        return (Item) Query.getAssociation(card03, card3, Item.class);
    }

    public long getExpireTime() {
        return expireTime;
    }

    public String getName() {
        return this.item.getJapaneseName();
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setAccountId(long id) {
        this.accountId = id;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public void setAmount(int amount) {
        this.amount += amount;
        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    public void setEquip(int equip) {
        this.equip = equip;
    }

    public void setIdentified(int isIdentified) {
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

    /* Helpers */
    public long getItemId() {
        return this.item.getId();
    }

    /**
     * Gets an <code>Item Object</code> from the <code>Storage</code>.
     * @param dao The Data Access Object;
     * @param itemId The Item id;
     * @return An <code>Item Object</code>.
     */
    public static Storage get(String itemId, DAO dao) {
        dao.open();
        try {
            Storage item = (Storage) dao.get(Storage.class, itemId);
            dao.commit();
            return item;
        } catch (Exception e) {
            dao.rollBack();
        } finally {
            dao.closeSession();
        }
        return null;
    }

    public String getDetailedName() {
        return ItemInfo.getDetailedName(this);
    }

    public String getTitle() {
        return ItemInfo.getTitle(this);
    }

    /**
     * Creates the specified number of random Items in the specified Login account Storage.
     * @param dao The Data Access Object;
     * @param id The target Login account;
     * @param i The number of random Items do create.
     */
    public static void randomize(String id, int n, DAO dao) {
        dao.open();
        try {
            long accountId = Long.parseLong(id);
            for (int i = 0; i < n; i++) {
                Item item = Item.getRandom(dao);
                insertOrUpdate(accountId, item, new Query());
            }
            dao.commit();
        } catch (Exception e) {
            dao.rollBack();
        } finally {
            dao.closeSession();
        }
    }

    /**
     * Creates or updates an Item in the specified account.
     * Stackable Items need to be checked whether they already exist in the Storage, so only its amount gets updated.
     * @param dao The Data Access Object;
     * @param accountId The account id;
     * @param item The Item to be stored.
     */
    public static void insertOrUpdate(long accountId, Item item, DAO dao) {
        if (item.isStackable()) {
            Storage storageItem = (Storage) dao.getUniqueResult(
                    Storage.class,
                    Restrictions.eq("accountId", accountId),
                    Restrictions.eq("item.id", item.getId()));
            dao.open();
            if (storageItem == null) {
                if (!Storage.isFull(accountId, new Query())) {
                    storageItem = new Storage(accountId, item);
                    dao.set(storageItem);
                }
            } else {
                storageItem.setAmount(+1);
                dao.update(item);
            }
            dao.close();
        } else {
            if (!Storage.isFull(accountId, new Query())) {
                dao.open();
                Storage storageItem = new Storage(accountId, item);
                dao.set(storageItem);
                dao.close();
            }
        }
    }

    /**
     * Counts all the Storage item in the specified account.
     * @param dao The Data Access Object;
     * @param accountId The account id.
     * @return <code>true</code> if the Storage has more items than the specified in Storage.MAX_ITEMS, <code>false</code> otherwise.
     */
    public static boolean isFull(long accountId, DAO dao) {
        Long itemCount = (Long) dao.query(
                "select count(id) from Storage where accountId = " + accountId);
        if (itemCount != null) {
            if (itemCount >= Storage.MAX_ITEMS) {
                return true;
            }
        }
        return false;
    }
}
