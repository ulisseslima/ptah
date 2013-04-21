package com.dvlcube.model;

/**
 *
 * @author Wonka
 */
public interface GenericItem {

    public int getAmount();

    public int getAttribute();

    public int getRefineRate();

    public String getName();

    public boolean isIdentified();

    public Item getItem();

    public long getItemId();

    public Item getCard0();

    public Item getCard1();

    public Item getCard2();

    public Item getCard3();

    public long getExpireTime();

    /**
     * Generates the name the same way it is displayed in the game's storage, with the refine rate and card prefixes/suffixes.
     * @return The item's detailed name.
     */
    public String getDetailedName();

    /**
     * Gets additional info about the item (card information and expire time).
     * @return Additional information about this item.
     */
    public String getTitle();

    /* Setters */
    /**
     * Increase or decrease the amount of this item in the Inventory.
     * @param amount The amount to add or remove.
     */
    public void setAmount(int amount);
}
