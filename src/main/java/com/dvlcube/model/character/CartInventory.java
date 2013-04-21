package com.dvlcube.model.character;

import com.dvlcube.model.GenericItem;
import com.dvlcube.model.Item;
import com.dvlcube.model.ItemInfo;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * @author Wonka
 */
@Entity
@Table(name = "cart_inventory")
public class CartInventory implements Serializable, GenericItem {

    public CartInventory() {
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
    private int equip;
    @Column(name = "identify")
    private boolean isIdentified;
    @Column(name = "refine")
    private int refineRate;
    @Column(name = "attribute")
    private int attribute;
    @ManyToOne
    @JoinColumn(name = "card0", updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Item card0;
    @ManyToOne
    @JoinColumn(name = "card1", updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Item card1;
    @ManyToOne
    @JoinColumn(name = "card2", updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Item card2;
    @ManyToOne
    @JoinColumn(name = "card3", updatable = false)
    @NotFound(action = NotFoundAction.IGNORE)
    private Item card3;
    @Column(name = "expire_time")
    private long expireTime;

    /* Getters */
    public long getId() {
        return id;
    }

    public long getCharId() {
        return charId;
    }

    public Item getItem() {
        return item;
    }

    public long getItemId() {
        return item.getId();
    }

    public int getAmount() {
        return amount;
    }

    public int getEquip() {
        return equip;
    }

    public boolean isIdentified() {
        return isIdentified;
    }

    public int getRefineRate() {
        return refineRate;
    }

    public int getAttribute() {
        return attribute;
    }

    public Item getCard0() {
        return card0;
    }

    public Item getCard1() {
        return card1;
    }

    public Item getCard2() {
        return card2;
    }

    public Item getCard3() {
        return card3;
    }

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

    public void setAmount(int amount) {
        this.amount += amount;
        if (this.amount < 0) {
            this.amount = 0;
        }
    }

    public void setEquip(int equip) {
        this.equip = equip;
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

    public void setCard0(Item card0) {
        this.card0 = card0;
    }

    public void setCard1(Item card1) {
        this.card1 = card1;
    }

    public void setCard2(Item card2) {
        this.card2 = card2;
    }

    public void setCard3(Item card3) {
        this.card3 = card3;
    }

    public void setExpireTime(long time) {
        this.expireTime = time;
    }

    public String getName() {
        return this.item.getJapaneseName();
    }

    public String getDetailedName() {
        return ItemInfo.getDetailedName(this);
    }

    public String getTitle() {
        return ItemInfo.getTitle(this);
    }
}
