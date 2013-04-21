package com.dvlcube.model.character;

import com.dvlcube.controller.Server;
import com.dvlcube.i18n.I18n;
import com.dvlcube.model.Homunculus;
import com.dvlcube.model.Login;
import com.dvlcube.model.Party;
import com.dvlcube.model.Pet;
import com.dvlcube.model.Storage;
import com.dvlcube.model.User;
import com.dvlcube.model.guild.Guild;
import com.dvlcube.model.guild.GuildMember;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.CubeString;
import com.dvlcube.util.Util;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * Represents the in-game Character.
 * @author Wonka
 */
@Entity
@Table(name = "`char`")
public class Char implements Serializable {

    public Char() {
    }

    public Char(Login account, DAO dao) {
        this.account = account;
        slot = getFreeSlot(account.getAccountId(), dao);
        name = Util.getRandomNames(2);
        classId = getRandomClass(account.getSex());
        baseLevel = (int) (Math.random() * 99) + 1;
        setRandomJobLevel();
        baseExp = 0;
        jobExp = 0;
        zeny = (int) (Math.random() * 1000000);
        setRandomAttributes();
        maxHP = (40 * (VIT + 100)) / 100;
        hp = maxHP;
        maxSP = (11 * (INT + 100)) / 100;
        sp = maxSP;
        option = 0;
        karma = (int) (Math.random() * 100);
        manner = (int) (Math.random() * 100);
        hair = (int) (Math.random() * 6);
        hairColor = (int) (Math.random() * 6);
        clothesColor = (int) (Math.random() * 6);
        weaponId = 1;
        shieldId = 0;
        headTopId = 0;
        headMidId = 0;
        headBottomId = 0;
        lastMap = "prontera";
        lastX = 155;
        lastY = 180;
        saveMap = "prontera";
        saveX = 53;
        saveY = 111;
        partnerId = 0;
        isOnline = false;
        fame = (int) (Math.random() * 100);
        fatherId = 0;
        motherId = 0;
        childId = 0;
        partyId = 0;
        guildId = 0;
        petId = 0;
        homunculusId = 0;
    }

    public Char(String name, Login account, int[] attributes, DAO dao) {
        this.account = account;
        this.slot = getFreeSlot(account.getAccountId(), dao);
        this.name = name;
        this.classId = 0;
        this.baseLevel = 1;
        this.jobLevel = 1;
        this.baseExp = 0;
        this.jobExp = 0;
        this.zeny = 0;
        if (attributes != null) {
            this.STR = attributes[0];
            this.INT = attributes[1];
            this.VIT = attributes[2];
            this.DEX = attributes[3];
            this.AGI = attributes[4];
            this.LUK = attributes[5];
        }
        this.maxHP = (40 * (this.VIT + 100)) / 100;
        this.hp = this.maxHP;
        this.maxSP = (11 * (this.INT + 100)) / 100;
        this.sp = this.maxSP;
        this.statusPoints = 0;
        this.skillPoints = 0;
        this.option = 0;
        this.karma = 0;
        this.manner = 0;
        this.hair = 0;
        this.hairColor = 0;
        this.clothesColor = 0;
        this.weaponId = 1;
        this.shieldId = 0;
        this.headTopId = 0;
        this.headMidId = 0;
        this.headBottomId = 0;
        this.lastMap = "new_1-1";
        this.lastX = 53;
        this.lastY = 111;
        this.saveMap = "new_1-1";
        this.saveX = 53;
        this.saveY = 111;
        this.partnerId = 0;
        this.isOnline = false;
        this.fame = 0;
        this.fatherId = 0;
        this.motherId = 0;
        this.childId = 0;
        this.partyId = 0;
        this.guildId = 0;
        this.petId = 0;
        this.homunculusId = 0;
    }
    @Transient
    public static final int NAME_MAX_LENGTH = 30;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "char_id")
    private long id;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Login account;
    @Column(name = "char_num")
    private long slot = 0;
    @Column(name = "name")
    private String name;
    @Column(name = "`class`")
    private int classId;
    @Column(name = "base_level")
    private int baseLevel;
    @Column(name = "job_level")
    private int jobLevel;
    @Column(name = "base_exp")
    private long baseExp;
    @Column(name = "job_exp")
    private long jobExp;
    @Column(name = "zeny")
    private long zeny;
    @Column(name = "str")
    private int STR = 5;
    @Transient
    private int strBonus = 0;
    @Column(name = "agi")
    private int AGI = 5;
    @Transient
    private int agiBonus = 0;
    @Column(name = "vit")
    private int VIT = 5;
    @Transient
    private int vitBonus = 0;
    @Column(name = "`int`")
    private int INT = 5;
    @Transient
    private int intBonus = 0;
    @Column(name = "dex")
    private int DEX = 5;
    @Transient
    private int dexBonus = 0;
    @Column(name = "luk")
    private int LUK = 5;
    @Transient
    private int lukBonus = 0;
    @Column(name = "hp")
    private int hp;
    @Transient
    private int hpBonus = 0;
    @Column(name = "max_hp")
    private int maxHP;
    @Column(name = "sp")
    private int sp;
    @Column(name = "max_sp")
    private int maxSP;
    @Column(name = "status_point")
    private int statusPoints;
    @Column(name = "skill_point")
    private int skillPoints;
    @Column(name = "`option`")
    private int option;
    @Column(name = "karma")
    private int karma;
    @Column(name = "manner")
    private int manner;
    @Column(name = "party_id")
    private long partyId;
    @Column(name = "guild_id")
    private long guildId;
    @Column(name = "pet_id")
    private long petId;
    @Column(name = "homun_id")
    private long homunculusId;
    @Column(name = "hair")
    private int hair;
    @Column(name = "hair_color")
    private int hairColor;
    @Column(name = "clothes_color")
    private int clothesColor;
    @Column(name = "weapon")
    private int weaponId;
    @Transient
    @OneToOne
    @JoinColumn(name = "weapon")
    private Inventory weapon;
    @Column(name = "shield")
    private int shieldId;
    @Column(name = "head_top")
    private int headTopId;
    @Column(name = "head_mid")
    private int headMidId;
    @Column(name = "head_bottom")
    private int headBottomId;
    @Column(name = "last_map")
    private String lastMap;
    @Column(name = "last_x")
    private int lastX;
    @Column(name = "last_y")
    private int lastY;
    @Column(name = "save_map")
    private String saveMap;
    @Column(name = "save_x")
    private int saveX;
    @Column(name = "save_y")
    private int saveY;
    @Column(name = "online")
    private boolean isOnline;
    @Column(name = "fame")
    private long fame;
    @Column(name = "partner_id")
    private long partnerId;
    @Column(name = "father")
    private long fatherId;
    @Column(name = "mother")
    private long motherId;
    @Column(name = "child")
    private long childId;
    @Transient
    private List<SkillEntry> skills = new ArrayList<SkillEntry>();
    @Transient
    private List<Hotkey> hotkeys = new ArrayList<Hotkey>();
    @Transient
    private Char partner, father, mother, child;
    @Transient
    private Party party;
    @Transient
    private Guild guild;
    @Transient
    private Pet pet;
    @Transient
    private Homunculus homunculus;

    /* Getters */
    public long getId() {
        return id;
    }

    public Login getAccount() {
        return account;
    }

    public long getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }

    public String getNameURL() {
        return name.replace(" ", "+");
    }

    /**
     * Class Names are saved as Integers;
     * There's a classes.properties file stored in /web/const/. This file maps the Integers to the i18n keys;
     * This method searches the i18n file for the specific key and returns the value.
     * @return The Class name of the Char.
     * @throws IOException
     * @deprecated
     */
    @Deprecated
    public String getClassName2() throws IOException {
        //System.out.println("\n## App Path:" + getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
        try {
            String className = "Undefined";
            String line;
            FileReader classes = new FileReader(this.getClass().getResource("classes.properties").getPath());
            BufferedReader reader = new BufferedReader(classes);
            while ((line = reader.readLine()) != null) {
                String classKey = line.split("=")[0];
                Integer classValue = Integer.parseInt(line.split("=")[1]);
                if (classValue == this.classId) {
                    className = I18n.getString(Locale.US, classKey);
                    break;
                }
            }
            return className;
        } catch (Exception e) {
            return "Undefined";
        }
    }

    /**
     * Calculates job status bonuses.
     */
    public void getJobBonuses() {
        try {
            if (classId != 0) {
                String line;
                FileReader bonuses = new FileReader(this.getClass().getResource("job_bonus").getPath());
                BufferedReader reader = new BufferedReader(bonuses);
                while ((line = reader.readLine()) != null) {
                    int classCode = Integer.parseInt(line.substring(0, line.indexOf(",")));
                    if (classCode == this.classId) {
                        int[] jobBonuses = Util.toIntArray(line);
                        for (int i = 1; i <= this.jobLevel; i++) {
                            switch (jobBonuses[i]) {
                                case 1:
                                    strBonus++;
                                    break;
                                case 2:
                                    agiBonus++;
                                    break;
                                case 3:
                                    vitBonus++;
                                    break;
                                case 4:
                                    intBonus++;
                                    break;
                                case 5:
                                    dexBonus++;
                                    break;
                                case 6:
                                    lukBonus++;
                                    break;
                            }
                        }
                    }
                }
                reader.close();
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /**
     * Ported from eAthena's status_base_pc_maxhp()
     * @param baseLevel The Char base level.
     * @return Gets the number of stat points based on the Char's base level.
     */
    public int getStatPoints(int baseLevel) {
        int statPoints = 30;
        if (baseLevel > 1) {
            try {
                FileReader statTable = new FileReader(this.getClass().getResource("statpoint").getPath());
                BufferedReader reader = new BufferedReader(statTable);
                for (int i = 1; i < baseLevel; i++) {
                    statPoints += Integer.parseInt(reader.readLine());
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }
        }
        return statPoints;
    }

    /**
     * Class Names are saved as Integers;
     * There's a classes.properties file stored in this package. This file maps the Integers to the i18n keys;
     * This method searches the i18n file for the specific key and returns the value.
     * @return The Class name of the Char.
     * @throws IOException
     */
    public String getClassName() throws IOException {
        Properties properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("classes.properties"));
        try {
            String className = properties.getProperty(Long.toString(this.classId));
            Locale locale = new Locale(properties.getProperty("Locale"));
            String i18nClassName = I18n.getString(locale, className);
            return i18nClassName;
        } catch (Exception e) {
            return "Undefined";
        }
    }

    public int getClassId() {
        return classId;
    }

    public int getClassHex() {
        return CharInfo.toHex(classId);
    }

    public int getBaseLevel() {
        return baseLevel;
    }

    public int getJobLevel() {
        return jobLevel;
    }

    public long getBaseExp() {
        return baseExp;
    }

    public long getJobExp() {
        return jobExp;
    }

    public long getZeny() {
        return zeny;
    }

    public int getStr() {
        return STR;
    }

    public int getStrBonus() {
        return strBonus;
    }

    public int getAgi() {
        return AGI;
    }

    public int getAgiBonus() {
        return agiBonus;
    }

    public int getVit() {
        return VIT;
    }

    public int getVitBonus() {
        return vitBonus;
    }

    public int getInt() {
        return INT;
    }

    public int getIntBonus() {
        return intBonus;
    }

    public int getDex() {
        return DEX;
    }

    public int getDexBonus() {
        return dexBonus;
    }

    public int getLuk() {
        return LUK;
    }

    public int getLukBonus() {
        return lukBonus;
    }

    public int getHP() {
        return hp;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getSP() {
        return sp;
    }

    public int getMaxSP() {
        return maxSP;
    }

    /**
     * @return The number of stat points available to spend.
     */
    public int getStatusPoints() {
        return statusPoints;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getOption() {
        return option;
    }

    public int getKarma() {
        return karma;
    }

    public int getManner() {
        return manner;
    }

    public int getHair() {
        return hair;
    }

    public int getHairColor() {
        return hairColor;
    }

    public int getClothesColor() {
        return clothesColor;
    }

    public Inventory getWeapon() {
        return weapon;
    }

    public int getWeaponId() {
        return weaponId;
    }

    public int getHeadBottomId() {
        return headBottomId;
    }

    public String getLastMap() {
        return lastMap;
    }

    public int getLastX() {
        return lastX;
    }

    public int getLastY() {
        return lastY;
    }

    public String getSaveMap() {
        return saveMap;
    }

    public int getSaveX() {
        return saveX;
    }

    public int getSaveY() {
        return saveY;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public long getFame() {
        return fame;
    }

    public Party getParty() {
        return (Party) Query.getAssociation(partyId, party, Party.class);
    }

    public Guild getGuild() {
        return (Guild) Query.getAssociation(guildId, guild, Guild.class);
    }

    public Pet getPet() {
        return (Pet) Query.getAssociation(petId, pet, Pet.class);
    }

    public Homunculus getHomunculus() {
        return (Homunculus) Query.getAssociation(homunculusId, homunculus, Homunculus.class);
    }

    public Char getFather() {
        return (Char) Query.getAssociation(fatherId, father, Char.class);
    }

    public Char getMother() {
        return (Char) Query.getAssociation(motherId, mother, Char.class);
    }

    public Char getChild() {
        return (Char) Query.getAssociation(childId, child, Char.class);
    }

    public Char getPartner() {
        return (Char) Query.getAssociation(partnerId, partner, Char.class);
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setAccountId(Login account) {
        this.account = account;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClass(int classId) {
        this.classId = classId;
    }

    public void setBaseLevel(int baseLevel) {
        this.baseLevel = baseLevel;
    }

    public void setJobLevel(int jobLevel) {
        this.jobLevel = jobLevel;
    }

    public void setBaseExp(int baseExp) {
        this.baseExp = baseExp;
    }

    public void setJobExp(int jobExp) {
        this.jobExp = jobExp;
    }

    public void setZeny(long zeny) {
        this.zeny += zeny;
        if (this.zeny < 0) {
            this.zeny = 0;
        }
    }

    public void setStr(int STR) {
        this.STR = STR;
    }

    public void setAgi(int AGI) {
        this.AGI = AGI;
    }

    public void setVit(int VIT) {
        this.VIT = VIT;
    }

    public void setInt(int INT) {
        this.INT = INT;
    }

    public void setDex(int DEX) {
        this.DEX = DEX;
    }

    public void setLuk(int LUK) {
        this.LUK = LUK;
    }

    public void setHP(int HP) {
        this.hp = HP;
    }

    public void setMaxHP(int maxHP) {
        this.maxHP = maxHP;
    }

    public void setSP(int sp) {
        this.sp = sp;
    }

    public void setMaxSP(int maxSP) {
        this.maxSP = maxSP;
    }

    public void setStatusPoints(int statusPoints) {
        this.statusPoints = statusPoints;
    }

    public void setSkillPoints(int skillPoints) {
        this.skillPoints = skillPoints;
    }

    public void setOption(int option) {
        this.option = option;
    }

    public void setKarma(int karma) {
        this.karma = karma;
    }

    public void setManner(int manner) {
        this.manner = manner;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public void setPartyId(long partyId) {
        this.partyId = partyId;
    }

    public void setGuild(Guild guild) {
        this.guild = guild;
    }

    public void setGuildId(long id) {
        try {
            this.guildId = id;
        } catch (Exception e) {
            this.guildId = 0;
        }
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public void setHomunculus(Homunculus homunculus) {
        this.homunculus = homunculus;
    }

    public void setHair(int hair) {
        this.hair = hair;
    }

    public void setHairColor(int hairColor) {
        this.hairColor = hairColor;
    }

    public void setClothesColor(int color) {
        this.clothesColor = color;
    }

    public void setLastMap(String mapName) {
        this.lastMap = mapName;
    }

    public void setLastX(int x) {
        this.lastX = x;
    }

    public void setLastY(int y) {
        this.lastY = y;
    }

    public void setSaveMap(String mapName) {
        this.saveMap = mapName;
    }

    public void setSaveX(int x) {
        this.saveX = x;
    }

    public void setSaveY(int y) {
        this.saveY = y;
    }

    public void setPartner(Char partner) {
        this.partner = partner;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public void setFather(Char father) {
        this.father = father;
    }

    public void setMother(Char mother) {
        this.mother = mother;
    }

    public void setChild(Char child) {
        this.child = child;
    }

    public void setFame(long fame) {
        this.fame = fame;
    }

    public void setWeapon(Inventory weapon) {
        this.weapon = weapon;
    }

    /* Helpers */
    /**
     * Searches for the <code>User</code> that owns this Char.
     * @return A <code>User</code> if it finds a match, <code>null</code> otherwise.
     */
    public User getUser() {
        DAO dao = new Query();
        String accountId = "%" + Long.toString(getAccount().getAccountId()) + "%";
        return (User) dao.getUniqueResult(
                User.class,
                Restrictions.like("accounts", accountId));
    }

    /**
     * Tries to get the <code>Locale</code> that is being used by the <code>User</code> owner of this <code>Char</code>.
     * @return The current <code>Locale</code>, or <code>Locale.US</code> if it can't get the <code>User</code>'s locale.
     */
    public Locale getCurrentLocale() {
        try {
            Locale locale = getAccount().getUser().getLocale();
            return locale;
        } catch (Exception e) {
            return Locale.US;
        }
    }

    /**
     * Loads a specific Char Object from its name.
     * @param dao The Data Access Object;
     * @param charName The Char name.
     * @return A Char Object if a match is found, <code>null</code> otherwise.
     */
    public static Char get(String charName, DAO dao) {
        return (Char) dao.getUniqueResult(
                Char.class,
                Restrictions.eq("name", charName));
    }

    /**
     * @param dao The Data Access Object;
     * @param charNames The desired Char names.
     * @return All Char objects whose name match an array element.
     */
    public static List<Char> get(List charNames, DAO dao) {
        return dao.getList(
                Char.class,
                Order.asc("name"),
                Restrictions.in("name", charNames));
    }

    /**
     * Gets all currently online players.
     * @param dao The Data Access Object.
     * @return A <code>Char List</code>.
     */
    public static List<Char> getOnlinePlayers(DAO dao) {
        return dao.getList(
                Char.class,
                Order.asc("name"),
                Restrictions.eq("isOnline", true));
    }

    @Override
    public String toString() {
        try {
            return getClassName() + "," + getName() + "," + getBaseLevel() + "/" + getJobLevel() + "," + getLastMap();
        } catch (Exception e) {
            return null;
        }
    }

    public int getCurrentHPPercentage() {
        if (getMaxHP() < 1 || getHP() < 1) {
            return 25;
        }
        int currentHPPercentage = (getHP() * 100) / getMaxHP();
        if (currentHPPercentage > 100) {
            currentHPPercentage = 100;
        }
        return currentHPPercentage;
    }

    public int getCurrentSPPercentage() {
        if (getMaxSP() < 1 || getSP() < 1) {
            return 5;
        }
        int currentSPPercentage = (getSP() * 100) / getMaxSP();
        if (currentSPPercentage > 100) {
            currentSPPercentage = 100;
        }
        return currentSPPercentage;
    }

    /**
     * Creates a combination of the name, class and guild of this Char.
     * @return a combination of the name, class and guild of this Char.
     * @throws IOException If an IO exception occurs.
     */
    public String getNameClassGuild() throws IOException {
        CubeString builder = new CubeString();
        builder.append("<b>", getClassName(), "</b> ", getName());
        if (this.guild != null) {
            builder.append(" @ ", getGuild().getName());
        }
        return builder.toString();
    }

    /**
     * Gets the first free slot in the specified Account.
     * Note: a DB session must already be open.
     * @param dao The current DAO session;
     * @param accountId The account id.
     * @return The free slot number.
     */
    public final long getFreeSlot(long accountId, DAO dao) {
        try {
            org.hibernate.Query query = dao.getSession().createQuery(
                    "SELECT slot FROM Char "
                    + "WHERE account.id = " + accountId);
            List<Long> occupiedSlots = query.list();
            if (occupiedSlots.size() > 0) {
                long freeSlot = 0;
                while (occupiedSlots.contains(freeSlot)) {
                    freeSlot++;
                }
                return freeSlot;
            }
        } catch (Exception e) {
            dao.rollBack();
        }
        return 0;
    }

    /**
     * Checks if the provided initial stat points are valid.
     * To be valid, all stats must:
     * - Have a total sum of 30;
     * - Have a sum of 10 on each pair.
     * @param stats The stats Array.
     * @return <code>true</code> if the stats are valid, <code>false<otherwise>.
     */
    public static boolean hasValid(int[] stats) {
        try {
            assert Util.sum(stats) == 30 : "The sum of all stats must be equal to 30";
            assert pairsAreOK(stats) : "The attribute pairs do not meet the assertions";
            return true;
        } catch (AssertionError e) {
            return false;
        }
    }

    /**
     * Checks all the stats pairs:
     * - str + int;
     * - vit + dex;
     * - agi + luk.
     * Each stat, when raised, subtracts 1 from its counterpart. The sum of each pair must be equal to 10, because the initial stat points can't go below 1 nor above 9.
     * @param stats The attribute Array.
     * @return <code>true</code> if all the pairs meet these assertions, <code>false<otherwise>.
     */
    public static boolean pairsAreOK(int[] stats) {
        int i = 0;
        while (i < stats.length - 1) {
            int sum = stats[i] + stats[i + 1];
            if (sum != 10) {
                return false;
            }
            i += 2;
        }
        return true;
    }

    /**
     * Seeks all party members of the Party this Char is in.
     * @param dao The Data Access Object.
     * @return a Char List if this Char is in a Party, <code>null</code> otherwise.
     */
    public List<Char> getPartyMembers(DAO dao) {
        if (this.partyId > 0) {
            List<Char> partyMembers = dao.getList(
                    Char.class,
                    Order.asc("name"),
                    Restrictions.eq("partyId", partyId));
            return partyMembers;
        } else {
            return null;
        }
    }

    /**
     * Seeks all guild members of the Guild this Char is in.
     * @param dao The Data Access Object.
     * @return a Char List if this Char is in a Guild, <code>null</code> otherwise.
     */
    public List<Char> getGuildMembers(DAO dao) {
        if (this.guildId > 0) {
            List<Char> guildMembers = dao.getList(
                    Char.class,
                    Order.asc("name"),
                    Restrictions.eq("guildId", guildId));
            return guildMembers;
        } else {
            return null;
        }
    }

    /**
     * Gets all Items in this Char's Inventory.
     * @param dao The Data Access Object.
     * @return This Char's Inventory List.
     */
    public List<Inventory> getInventory(DAO dao) {
        return dao.getList(
                Inventory.class,
                Order.asc("item.id"),
                Restrictions.eq("charId", getId()));
    }

    /**
     * Checks if the player has a cart, then loads all items it contains.
     * @param dao The Data Access Object.
     * @return This Char's CartInventory List.
     */
    public List<CartInventory> getCart(DAO dao) {
        List<CartInventory> cart = dao.getList(
                CartInventory.class,
                Order.asc("item.id"),
                Restrictions.eq("charId", getId()));
        return cart;
    }

    /**
     * Gets all the Skills assigned for this Char.
     * @param dao The Data Access Object.
     * @return This Char's Skill list.
     * @see Skill
     * @see SkillEntry
     */
    public List<SkillEntry> getSkills(DAO dao) {
        if (skills.isEmpty()) {
            skills = dao.getList(
                    SkillEntry.class,
                    Order.asc("pk.skill.id"),
                    Restrictions.eq("pk.char_.id", getId()));
        }
        return skills;
    }

    /**
     * Gets all the Skill Hotkeys assigned to this Char.
     * @param dao The Data Access Object.
     * @return This Char's Hotkey list.
     * @see Skill
     * @see SkillEntry
     * @see Hotkey
     */
    public List<Hotkey> getHotkeys(DAO dao) {
        if (hotkeys.isEmpty()) {
            hotkeys = dao.getList(
                    Hotkey.class,
                    Order.asc("pk.hotkey"),
                    Restrictions.eq("pk.char_.id", getId()));
        }
        return hotkeys;
    }

    /**
     * <code>getHotkeys()</code> returns all the assigned Hotkeys.
     * Alas, when it's necessary to show all the 27 hotkey slots [i.e. including the empty ones], this method should be used.
     * @param dao The Data Access Object.
     * @return All the 27 Hotkey slots, including <code>null<code> values on the empty ones.
     */
    public List<Hotkey> getFixedNumberOfHotkeys(DAO dao) {
        List<Hotkey> originalHotkeys = getHotkeys(dao);
        if (originalHotkeys != null) {
            List<Hotkey> fixedHotkeys = new ArrayList<Hotkey>();
            int j = 0;
            for (int i = 0; i < 27; i++) {
                if (j >= originalHotkeys.size()) {
                    fixedHotkeys.add(null);
                } else {
                    Hotkey hotkey = originalHotkeys.get(j);
                    if (hotkey.getHotkey() == i) {
                        fixedHotkeys.add(hotkey);
                        j++;
                    } else {
                        fixedHotkeys.add(null);
                    }
                }
            }
            return fixedHotkeys;
        }
        return null;
    }

    /**
     * Create the specified number of random Chars.
     * @param dao The Data Access Object;
     * @param accountId The account which this Char will be saved to;
     * @param n The number of Chars to create.
     * @return
     */
    public static List<Char> randomize(String accountId, int n, DAO dao) {
        dao.open();
        Login account = (Login) dao.get(Login.class, accountId);
        dao.close();
        List<Char> chars = new ArrayList<Char>();
        try {
            for (int i = 0; i < n; i++) {
                int numChars = account.getNumChars(new Query());
                if (numChars < Server.getMaxChars()) {
                    dao.open();
                    Char character = new Char(account, dao);
                    character.calculateStats();
                    dao.set(character);

                    Party party = joinOrOrganizeParty(account, character, new Query());
                    character.setPartyId(party.getId());
                    dao.update(character);
                    dao.close();

                    joinOrCreateGuild(character, new Query());
                    SkillEntry.assign(new Query(), character);
                    chars.add(character);
                } else {
                    //System.out.println("## Char.java: char limit reached");
                    break;
                }
            }
        } catch (Exception e) {
            //Server.error(e, "error while randomizing characters");
            e.printStackTrace();
        } finally {
            return chars;
        }
    }

    /**
     * Gets the first Party it finds in the specified account. If no party is created, it creates one.
     * @param dao The Data Access Object;
     * @param account The Login account;
     * @param leader The leader of the new Party (in case none is found).
     * @return A Party Object.
     */
    private static Party joinOrOrganizeParty(Login account, Char leader, DAO dao) {
        Party party = (Party) dao.getFirstResult(
                Party.class,
                Order.asc("name"),
                Restrictions.eq("leaderAccount", account));
        if (party == null) {
            party = new Party(
                    Util.getRandomName(),
                    account,
                    leader);
            dao.open();
            dao.set(party);
            dao.close();
        }
        return party;
    }

    /**
     * Try to find a Guild owned by one of the specified Char's User Chars.
     * @param dao The Data Access Object;
     * @param character The desired Char.
     * @return An existing Guild, if a match is found, or a new Guild otherwise.
     */
    private static Guild joinOrCreateGuild(Char character, DAO dao) {
        List<Char> chars = character.getUser().getChars(dao);
        Guild guild = (Guild) dao.getFirstResult(
                Guild.class,
                Order.asc("name"),
                Restrictions.in("owner", chars));
        dao.open();
        if (guild == null) {
            guild = new Guild(Util.getRandomName(), character);
            dao.set(guild);
            GuildMember guildMember = new GuildMember(character);
            dao.set(guildMember);
        }
        character.setGuildId(guild.getId());
        dao.update(character);
        dao.close();
        return guild;
    }

    /**
     * @return a random job class id.
     */
    private int getRandomClass(String sex) {
        int randomId = 1;
        randomId = (int) (Math.random() * 49) + 1;
        if (randomId > 26) {
            randomId += 4000;
        } else {
            if ((int) (Math.random() * 10) > 6) {
                randomId += 4000;
            }
        }
        return fixedJob(randomId, sex);
    }

    /**
     * Fixes the class according to the gender of the account. (Dancer-Bard case)
     * 4043 - Baby Dancer; 20 - Dancer;
     * 4042 - Baby Bard; 19 Bard;
     * @param jobId The job id;
     * @param sex The account gender.
     * @return the fixed (if necessary) job id [according to the gender specified]
     */
    private int fixedJob(int jobId, String sex) {
        if (sex.equals("M") && (jobId == 4043) || (jobId == 20)) {
            jobId--;
        } else if (sex.equals("F") && (jobId == 4042) || (jobId == 19)) {
            jobId++;
        }
        return jobId;
    }

    /**
     * Sets a random job level.
     */
    private void setRandomJobLevel() {
        jobLevel = 10;
        skillPoints = 9;
        if ((classId > 0 && classId < 7) || (classId > 4001 && classId < 4008)) {
            jobLevel = (int) (Math.random() * 50) + 1;
            skillPoints += jobLevel - 1;
        } else if ((classId > 6 && classId < 27) || (classId > 4029)) {
            jobLevel = (int) (Math.random() * 50) + 1;
            skillPoints += jobLevel + 48;
        } else if ((classId == 4001) || (classId == 4023)) {
            skillPoints = jobLevel = 10;
        } else if (classId > 4007 && classId < 4023) {
            jobLevel = (int) (Math.random() * 70) + 1;
            skillPoints += jobLevel + 48;
        }
    }

    /**
     * Sets random stat points.
     * Warnings:
     * - Doesn't check whether the Char is of an advanced class;
     * - Doesn't check the cost of raising a stat (based on base level);
     */
    private void setRandomAttributes() {
        statusPoints = getStatPoints(baseLevel) / baseLevel;
        STR = AGI = LUK = DEX = VIT = INT = 1;
        int boost = 0;
        while (statusPoints > 0) {
            boost = spend(statusPoints, STR);
            STR += boost;
            statusPoints -= boost;
            boost = spend(statusPoints, AGI);
            AGI += boost;
            statusPoints -= boost;
            boost = spend(statusPoints, DEX);
            DEX += boost;
            statusPoints -= boost;
            boost = spend(statusPoints, VIT);
            VIT += boost;
            statusPoints -= boost;
            boost = spend(statusPoints, INT);
            INT += boost;
            statusPoints -= boost;
            boost = spend(statusPoints, LUK);
            LUK += boost;
            statusPoints -= boost;
        }
    }

    /**
     * Helper method for setRandomAttributes()
     * @param points The Char's current number of stat points left.
     * @return a random number of stat points that will be spent.
     */
    private int spend(int points, int stat) {
        if (stat < 99) {
            int boost = (int) (Math.random() * 9);
            if (points > 9) {
                boost = (int) (Math.random() * (points / 3));
            }
            if ((int) (Math.random() * 25) > 10) {
                boost *= 2;
            }
            if (boost > points) {
                boost = points;
            }
            if (stat + boost > 99) {
                boost = 99 - stat;
            }
            return boost;
        }
        return 0;
    }

    /**
     * Calculates the following stats:
     * - Max HP;
     * - Max SP;
     */
    public void calculateStats() {
        CharInfo status = new CharInfo(this);
        this.maxHP = status.getMaxHP();
        this.hp = (int) (Math.random() * maxHP);
        this.maxSP = status.getMaxSP();
        this.sp = (int) (Math.random() * maxSP);
    }

    /**
     * Safe method to spend skill points.
     * @param points Desired points to spend.
     * @return The points you can spend.
     */
    public int spendSkillPoints(int points) {
        int spendablePoints = 0;
        if (skillPoints > 0) {
            if (skillPoints - points > 0) {
                spendablePoints = points;
                skillPoints -= points;
            } else {
                spendablePoints = skillPoints;
                skillPoints = 0;
            }
        }
        return spendablePoints;
    }

    /**
     * @param dao The Data Access Object;
     * @param idString The Inventory item id, as String.
     * @return An Inventory item, if this Char owns it. <code>null</code> otherwise.
     * @throws ConcurrentModificationException if the Char is logged in.
     */
    public Inventory getInventoryItem(String idString, DAO dao)
            throws ConcurrentModificationException {
        if (this.isOnline()) {
            throw new ConcurrentModificationException("Char must be offline before making any further modifications");
        }
        long itemId = 0;
        try {
            itemId = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            return null;
        }
        Inventory item = (Inventory) dao.getUniqueResult(
                Inventory.class,
                Restrictions.eq("id", itemId),
                Restrictions.eq("charId", this.getId()));
        return item;
    }

    /**
     * @param dao The Data Access Object;
     * @param idString The Storage item id, as String.
     * @return A Storage item, if this Login account contains it. <code>null</code> otherwise.
     * @throws ConcurrentModificationException if any Char from the same account is online.
     */
    public Storage getStorageItem(String idString, DAO dao)
            throws ConcurrentModificationException {
        if (this.getAccount().hasOnlineChars(dao)) {
            throw new ConcurrentModificationException("All Chars must be offline before making any further modifications");
        }
        long itemId = 0;
        try {
            itemId = Long.parseLong(idString);
        } catch (NumberFormatException e) {
            return null;
        }
        Storage item = (Storage) dao.getUniqueResult(
                Storage.class,
                Restrictions.eq("id", itemId),
                Restrictions.eq("accountId", this.getAccount().getAccountId()));
        return item;
    }

    /**
     * Safely splits a CSV string of Char names.
     * @param recipientsString The CSV string of Char names.
     * @return an array of Char names.
     */
    public static List split(String recipientsString) {
        return Util.split(recipientsString);
    }
}
