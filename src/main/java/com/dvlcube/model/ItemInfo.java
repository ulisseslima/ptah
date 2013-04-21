package com.dvlcube.model;

import com.dvlcube.util.CubeString;

/**
 *
 * @author Wonka
 */
public class ItemInfo {

    /**
     * Generates the name the same way it is displayed in the game's storage, with the refine rate and card prefixes/suffixes.
     * @return The item's detailed name.
     */
    public static String getDetailedName(GenericItem item) {
        CubeString builder = new CubeString();
        if (item.getRefineRate() > 0) {
            builder.append("+", item.getRefineRate(), " ");
        }
        builder.append(item.getName());
        Integer slots = item.getItem().getSlots();
        if (slots != null) {
            if (slots > 0) {
                builder.append("[", slots, "]");
            }
        }
        return builder.toString();
    }

    /**
     * Gets additional info about the item (card information and expire time).
     * @return Additional information about this item.
     */
    public static String getTitle(GenericItem item) {
        CubeString builder = new CubeString();
        int cardCount = 0;
        if (item.getCard0() != null) {
            cardCount++;
            builder.append("[", item.getCard0().getName(), "]");
        }
        if (item.getCard1() != null) {
            cardCount++;
            builder.append("[", item.getCard1().getName(), "]");
        }
        if (item.getCard2() != null) {
            cardCount++;
            builder.append("[", item.getCard2().getName(), "]");
        }
        if (item.getCard3() != null) {
            cardCount++;
            builder.append("[", item.getCard3().getName(), "]");
        }
        if (cardCount > 0) {
            builder.insert(0, "Cards: ");
        }
        if (item.getExpireTime() > 0) {
            builder.append(" - Expire time: ", item.getExpireTime());
        }
        return builder.toString();
    }
}
