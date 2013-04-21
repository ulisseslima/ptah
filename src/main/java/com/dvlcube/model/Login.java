package com.dvlcube.model;

import com.dvlcube.model.character.Char;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;

/**
 * Represents the User in-game Account.
 * @author Wonka
 */
@Entity
@Table(name = "login")
public class Login implements Serializable {

    public Login() {
    }

    /**
     * This constructor is used to create, automatically, a new game account when a user creates a CP account.
     * @param user The User object;
     * @param password The password String;
     * @param lastIP The request IP String.
     */
    public Login(User user, String password, String lastIP) {
        this.userId = user.getName();
        this.userPass = password;
        this.sex = user.getGender();
        this.eMail = user.getMail();
        this.level = 0;
        this.state = 0;
        this.unbanTime = 0;
        this.expirationTime = 0;
        this.loginCount = 0;
        this.lastLogin = new Date();
        this.lastIPaddress = lastIP;
    }

    public Login(String mail, String userId, String password, String gender, String lastIP) {
        this.userId = userId;
        this.userPass = password;
        this.sex = gender;
        this.eMail = mail;
        this.level = 0;
        this.state = 0;
        this.unbanTime = 0;
        this.expirationTime = 0;
        this.loginCount = 0;
        this.lastLogin = new Date();
        this.lastIPaddress = lastIP;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private long id;
    @Column(name = "userid")
    private String userId;
    @Column(name = "user_pass")
    private String userPass;
    @Column(name = "sex")
    private String sex;
    @Column(name = "email")
    private String eMail;
    @Column(name = "`level`")
    private int level;
    @Column(name = "`state`")
    private int state;
    @Column(name = "unban_time")
    private int unbanTime;
    @Column(name = "expiration_time")
    private int expirationTime;
    @Column(name = "logincount")
    private int loginCount;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lastlogin")
    private Date lastLogin;
    @Column(name = "last_ip")
    private String lastIPaddress;
    @Transient
    private List<Char> chars;
    @Transient
    private List<Storage> storage;
    @Transient
    public static final int MIN_NAME_LENGTH = 3;

    /* Getters */
    public long getAccountId() {
        return id;
    }

    /**
     * @return The name used to login.
     */
    public String getUserId() {
        return userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public String getSex() {
        return sex;
    }

    public String getEMail() {
        return eMail;
    }

    public int getLevel() {
        return level;
    }

    public int getState() {
        return state;
    }

    public int getUnbanTime() {
        return unbanTime;
    }

    public int getExpirationTime() {
        return expirationTime;
    }

    public int getLoginCount() {
        return loginCount;
    }

    public String getLastLogin() {
        return Util.formatDate(lastLogin);
    }

    public String getLastIPAddress() {
        return lastIPaddress;
    }

    /* Setters */
    public void setAccountId(long id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public void setSex(String sex) {
        if (!sex.equals("M") || !sex.equals("F") || !sex.equals("S")) {
            this.sex = "M";
        } else {
            this.sex = sex;
        }
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setUnbanTime(int unbanTime) {
        this.unbanTime = unbanTime;
    }

    public void setExpirationTime(int expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setLoginCount(int loginCount) {
        this.loginCount = loginCount;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setLastIPAddress(String IPaddress) {
        this.lastIPaddress = IPaddress;
    }

    /* Helpers */
    /**
     * Gets all Chars in this account.
     * @param dao The Data Access Object.
     * @return A Char List.
     */
    public List<Char> getChars(DAO dao) {
        this.chars = dao.getList(
                Char.class,
                Order.asc("slot"),
                Restrictions.eq("account", this));
        return chars;
    }

    /**
     * Searches for the <code>User</code> that owns this account.
     * @return A <code>User</code> if it finds a match, <code>null</code> otherwise.
     */
    public User getUser() {
        DAO dao = new Query();
        String accountId = "%" + Long.toString(getAccountId()) + "%";
        User match = (User) dao.getUniqueResult(
                User.class,
                Restrictions.like("accounts", accountId));
        return match;
    }

    /**
     * Verifies if this account has any logged Chars in-game.
     * @return <code>true</code> if there are any Chars online, <code>false</code> otherwise.
     */
    public boolean hasOnlineChars(DAO dao) {
        Long onlinePlayers = (Long) dao.query("SELECT COUNT(id) FROM Char WHERE isOnline = 1 AND account.id = " + getAccountId());
        if (onlinePlayers > 0) {
            return true;
        }
        return false;
    }

    /**
     * Gets the number of Chars created in this Account.
     * Note: a Transient Char List must already be created.
     * @return The number of Chars, if the Char List is not null, 0 otherwise.
     */
    public int getNumChars() {
        if (chars != null) {
            return chars.size();
        } else {
            return 0;
        }
    }

    /**
     * Gets the number of Chars created in this Account.
     * @return The number of Chars created in this Account.
     */
    public int getNumChars(DAO dao) {
        Long numChars = (Long) dao.query(
                "SELECT COUNT(id) FROM Char WHERE account.id = " + getAccountId());
        if (numChars != null) {
            return numChars.intValue();
        } else {
            return 0;
        }
    }

    /**
     * Gets a Char Object at the desired slot position.
     * @param position The desired slot position;
     * @param dao The Data Access Object.
     * @return A Char Object, <code>null</code> if an exception occurs.
     */
    public Char getCharAtPosition(int position, DAO dao) {
        Criterion[] restrictions = {
            Restrictions.eq("account", this),
            Restrictions.eq("slot", position)};
        Char match = (Char) dao.getUniqueResult(Char.class, restrictions);
        return match;
    }

    /**
     * Gets all Storage Items in this Account.
     * @param dao The Data Access Object.
     * @return A Storage List, <code>null</code> otherwise.
     */
    public List<Storage> getStorage(DAO dao) {
        storage = dao.getList(
                Storage.class,
                Order.asc("item.id"),
                Restrictions.eq("accountId", this.getAccountId()));
        return storage;
    }

    public Char getRichestChar(DAO dao) {
        Char richestChar = (Char) dao.getFirstResult(
                Char.class,
                Order.desc("zeny"),
                Restrictions.eq("account", this));
        return richestChar;
    }

    /**
     * Reorder the slots of all Chars within this Account.
     * The List items must be constructed like that: charId:positionNumber
     * @param newOrder The new order array. Each element contains the Char id combined by its new position. This array must have the same length as the number of Chars in this Account.
     */
    public void reorder(List<String> newOrder, DAO dao) {
        if (newOrder.size() == getChars(dao).size()) {
            dao.open();
            try {
                for (String position : newOrder) {
                    Char character = (Char) dao.get(Char.class, position.split(":")[0]);
                    if (character.getAccount().getAccountId() == this.id) {
                        int slot = Integer.parseInt(position.split(":")[1]);
                        character.setSlot(slot);
                        dao.update(character);
                    } else {
                        throw new IllegalAccessException("!! Login.java: The passeed List contains a Character from another Account, transaction will be rolled back.");
                    }
                }
                dao.commit();
            } catch (Exception e) {
                dao.rollBack();
            } finally {
                dao.closeSession();
            }
        } else {
            throw new IllegalArgumentException("!! Login.java: The passed List contains a different value than " + getNumChars());
        }
    }

    /**
     * Checks if there is already a Login by that user name.
     * @param name The user name to check.
     * @return <code>true</code> if the Login already exists, <code>false</code> otherwise.
     */
    public static boolean exists(String name, DAO dao) {
        Login login = (Login) dao.getUniqueResult(
                Login.class,
                Restrictions.eq("userId", name));
        return login != null ? true : false;
    }
}
