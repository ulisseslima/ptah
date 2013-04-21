package com.dvlcube.model;

/**
 *
 * @author Wonka
 */
public class ItemGroup {

    public ItemGroup(int start, int end) {
        this.start = start;
        this.end = end;
    }
    private int start;
    private int end;
    public static final ItemGroup[] groups = {
        new ItemGroup(501, 599),
        new ItemGroup(601, 700),
        new ItemGroup(701, 1099),
        new ItemGroup(1101, 1149),
        new ItemGroup(1151, 1187),
        new ItemGroup(1201, 1249),
        new ItemGroup(1250, 1282),
        new ItemGroup(1301, 1310),
        new ItemGroup(1351, 1382),
        new ItemGroup(1401, 1426),
        new ItemGroup(1451, 1486),
        new ItemGroup(1501, 1546),
        new ItemGroup(1550, 1577),
        new ItemGroup(1601, 1641),
        new ItemGroup(1701, 1743),
        new ItemGroup(1750, 1772),
        new ItemGroup(1801, 1827),
        new ItemGroup(1901, 1927),
        new ItemGroup(1950, 1981),
        new ItemGroup(2101, 2135),
        new ItemGroup(2201, 2299),
        new ItemGroup(2301, 2396),
        new ItemGroup(2401, 2446),
        new ItemGroup(2501, 2550),
        new ItemGroup(2601, 2775),
        new ItemGroup(4001, 4453),
        new ItemGroup(5001, 5811),
        new ItemGroup(6000, 7953),
        new ItemGroup(9001, 9056),
        new ItemGroup(10001, 10038),
        new ItemGroup(11000, 11019),
        new ItemGroup(11500, 12999),
        new ItemGroup(13000, 13042),
        new ItemGroup(13100, 13179),
        new ItemGroup(13200, 13207),
        new ItemGroup(13250, 13307),
        new ItemGroup(13500, 16248)
    };

    public static enum WeaponType {

        W_FIST, //Bare hands
        W_DAGGER, //1
        W_1HSWORD, //2
        W_2HSWORD, //3
        W_1HSPEAR, //4
        W_2HSPEAR, //5
        W_1HAXE, //6
        W_2HAXE, //7
        W_MACE, //8
        W_2HMACE, //9 (unused)
        W_STAFF, //10
        W_BOW, //11
        W_KNUCKLE, //12
        W_MUSICAL, //13
        W_WHIP, //14
        W_BOOK, //15
        W_KATAR, //16
        W_REVOLVER, //17
        W_RIFLE, //18
        W_GATLING, //19
        W_SHOTGUN, //20
        W_GRENADE, //21
        W_HUUMA, //22
        W_2HSTAFF, //23
        MAX_WEAPON_TYPE,
        // dual-wield constants
        W_DOUBLE_DD, // 2 daggers
        W_DOUBLE_SS, // 2 swords
        W_DOUBLE_AA, // 2 axes
        W_DOUBLE_DS, // dagger + sword
        W_DOUBLE_DA, // dagger + axe
        W_DOUBLE_SA // sword + axe
    };
    public static final int HEALING = 0;
    public static final int USABLES = 1;
    public static final int ETC = 2;
    public static final int WEAPONS_1hSWORDS = 3;
    public static final int WEAPONS_2hSWORDS = 4;
    public static final int WEAPONS_DAGGERS = 5;
    public static final int WEAPONS_KATARS = 6;
    public static final int WEAPONS_1hAXES = 7;
    public static final int WEAPONS_2hAXES = 8;
    public static final int WEAPONS_1hSPEARS = 9;
    public static final int WEAPONS_2hSPEARS = 10;//2h staff included
    public static final int WEAPONS_MACES = 11;
    public static final int BOOKS = 12;
    public static final int WEAPONS_STAFFS = 13;
    public static final int WEAPONS_BOWS = 14;
    public static final int AMMO_ARROWS = 15;
    public static final int WEAPONS_KNUCKLES = 16;
    public static final int INSTRUMENTS = 17;
    public static final int WHIPS = 18;
    //ADDITIONAL 2H STAFF EXCLUDED: RANGE -> 2000..2002
    public static final int SHIELDS = 19;
    public static final int HEADGEARS = 20;
    public static final int ARMORS = 21;
    public static final int FOOTGEARS = 22;
    public static final int GARMENTS = 23;
    public static final int ACCESSORIES = 24;
    public static final int CARDS = 25;
    public static final int HEADGEARS2 = 26;
    public static final int ETC2 = 27;
    public static final int PET_EGGS = 28;
    public static final int PET_ACC = 29;
    public static final int ETC_BOOKS = 30;
    public static final int USABLES_MORE = 31;
    public static final int WEAPONS_NINJA = 32;
    public static final int WEAPONS_GUNS = 33;
    public static final int AMMO_BULLETS = 34;
    public static final int AMMO_SHURIKENS = 35;
    public static final int CASH_SHOP = 36;


    /* Getters */
    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    /* Helpers */
    /**
     * @param id The ItemGroup id.
     * @return a random id belonging to the specified group.
     */
    public long getRandomId(int id) {
        return groups[id].getRandomId();
    }

    /**
     * @return a random item id in this group.
     */
    public long getRandomId() {
        int items = (getEnd() - getStart()) + 1;
        int randomItem = (int) (Math.random() * items) + getStart();
        return randomItem;
    }

    /**
     * Checks whether the specified item can be refined.
     * @param itemId The Item id.
     * @return <code>true</code> if the specified item is refinable, <code>false</code> otherwise.
     */
    public static boolean isRefinable(long itemId) {
        if (itemId >= groups[WEAPONS_1hSWORDS].getStart()
                && itemId <= groups[GARMENTS].getEnd()) {
            return true;
        } else if (itemId >= groups[WEAPONS_NINJA].getStart()
                && itemId <= groups[WEAPONS_GUNS].getEnd()) {
            return true;
        }
        return false;
    }

    public static boolean isUsable(long itemId) {
        if (itemId >= groups[USABLES].getStart() && itemId <= groups[USABLES].getEnd()) {
            return true;
        } else if (itemId >= groups[USABLES_MORE].getStart() && itemId <= groups[USABLES_MORE].getEnd()) {
            return true;
        }
        return false;
    }
}
