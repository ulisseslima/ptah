package com.dvlcube.model;

import com.dvlcube.model.character.Char;
import com.dvlcube.model.character.Friend;
import com.dvlcube.persistence.DAO;
import com.dvlcube.security.PasswordService;
import com.dvlcube.util.CubeString;
import com.dvlcube.util.Util;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.Transient;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Criterion;

/**
 * <p>The <code>User</code> Entity represents the highest level in the Account System hierarchy,
 * a <code>User</code> can have many <code>Account</code>s, and an <code>Account</code> can have
 * many </code>Char</code>s.</p>
 * <p>The <code>User</code> Entity is completely detached from the game DB Design, so any changes
 * won't affect the game directly.</p>
 * @author Wonka
 */
@Entity
@Table(name = "user_account")
public class User implements Serializable {

    public User() {
    }

    public User(String name, String mail, String password, String gender) {
        try {
            this.about = Integer.toString(new Date().hashCode());
            this.name = name;
            this.mail = mail;
            this.password = password;
            setGender(gender);
            if (this.gender.equals("F")) {
                this.image = "usr/etc/f-user.gif";
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public String toJSON() {
        String json = "{ 'parts' : {" //"parts" could be a collection of users
                + "'part' : ["
                + "{ 'name' : 'Mainboard', 'price' : '100', 'manufacturer' : {'name' : 'MAN1', 'location' : 'USA'} },"
                + "{ 'name' : 'Keyboard' , 'price' : '10', 'manufacturer' : {'name' : 'MAN2', 'location' : 'USA'}},"
                + "{ 'name' : 'CPU', 'price' : '90', 'manufacturer' : {'name' : 'MAN3', 'location' : 'USA'}},"
                + "{ 'name' : 'FAN', 'price' : '3','manufacturer' : {'name' : 'MAN1', 'location' : 'USA'} }"
                + "] }"
                + "}";
        return json;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cd_user")
    private long id;
    @Column(name = "nm_user")
    private String name;
    @Column(name = "im_user")
    private String image = "usr/etc/m-user.gif";
    @Column(name = "cd_user_accounts")
    private String accounts;
    @Column(name = "ic_user_gender")
    private String gender;
    @Column(name = "cd_user_mail")
    private String mail;
    @Column(name = "cd_user_password")
    private String password;
    @Column(name = "nm_user_signature")
    private String signature;
    @Column(name = "nm_user_about")
    private String about;
    @Column(name = "qt_user_post")
    private int totalPosts;
    @Column(name = "dt_user_joined")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date joinDate;
    @Column(name = "nm_user_website")
    private String website;
    @Transient
    private String imageElement, editableImageElement;
    @Transient
    private static final int MAX_ACCOUNTS = 16;
    @Transient
    private Locale locale;

    /* Getters */
    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getNameURL() {
        return name.replace(" ", "+");
    }

    public String getDisplayImageSource() {
        return image;
    }

    public String getImageElement() {
        setImageElement(this.image);
        return imageElement;
    }

    public String getEditableImageElement() {
        setEditableImageElement(this.image);
        return editableImageElement;
    }

    /**
     * Gets the last used Login by this User.
     * @param dao The Data Access Object.
     * @return A Login Object, <code>null</code> otherwise.
     */
    public Login getLastUsedAccount(DAO dao) {
        List<Login> allAccounts = getAccounts(dao);
        if (allAccounts != null) {
            return allAccounts.get(0);
        }
        return null;
    }

    /**
     * Gets a specific Login Object.
     * @param dao The Data Access Object;
     * @param accountId The desired Account Id.
     * @return An Account Object if a match is found.
     */
    public Login getAccount(Long accountId, DAO dao) {
        Login account = (Login) dao.get(Login.class, accountId);
        if (account != null) {
            if (getAccountsAsLong() != null) {
                if (getAccountsAsLong().contains(account.getAccountId())) {
                    return account;
                }
            }
        }
        return null;
    }

    /**
     * Gets a specific Login Object. The Login id must be in this User's account list.
     * @param dao The Data Access Object;
     * @param accountUserId The desired Account userId.
     * @return An Account Object if a match is found.
     */
    public Login getAccount(String accountUserId, DAO dao) {
        dao.open();
        try {
            Criteria criteria = dao.getSession().createCriteria(Login.class);
            criteria.add(Restrictions.eq("userId", accountUserId));
            criteria.add(Restrictions.in("id", Util.toLongList(getAccounts())));
            Login account = (Login) criteria.uniqueResult();
            dao.commit();
            if (account != null) {
                return account;
            }
        } catch (Exception e) {
            e.fillInStackTrace();
            dao.rollBack();
        } finally {
            dao.closeSession();
        }
        return null;
    }

    /**
     * @return CSV list of accounts owned by this User.
     */
    public String getAccountsAsString() {
        return accounts;
    }

    /**
     * @return a list containing all this User's account ids as long.
     */
    public List<Long> getAccountsAsLong() {
        return Util.toLongList(getAccounts());
    }

    /**
     * @return Each Login account id owned by this User, as a String array.
     */
    public String[] getAccounts() {
        return accounts.split(";");
    }

    /**
     * Creates a list of Login Objects based on the value of this.accounts.
     * @param dao A Data Access Object.
     * @return All Login Objects that match the criteria.
     */
    public List<Login> getAccounts(DAO dao) {
        if (this.accounts == null) {
            return null;
        } else {
            dao.open();
            List<Long> accountIds = Util.toLongList(this.accounts.split(";"));
            try {
                Criteria criteria = dao.getSession().createCriteria(Login.class);
                criteria.addOrder(Order.desc("lastLogin"));
                criteria.add(Restrictions.in("id", accountIds));
                List<Login> results = criteria.list();
                dao.commit();
                if (results != null) {
                    return results;
                }
            } catch (Exception e) {
                e.fillInStackTrace();
                dao.rollBack();
            } finally {
                dao.closeSession();
            }
            return null;
        }
    }

    public String getGender() {
        return gender;
    }

    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }

    public String getSignature() {
        return signature;
    }

    public String getAbout() {
        return about;
    }

    public int getTotalPosts() {
        return totalPosts;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public String getJoinDateHash() {
        return about;
    }

    public String getWebsite() {
        return website;
    }

    public Locale getLocale() {
        if (locale == null) {
            return Locale.US;
        } else {
            return locale;
        }
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        if (name.length() > 23) {
            throw new IllegalArgumentException("The max length of the 'name' field is 23");
        }
        this.name = name;
    }

    /**
     * Before updating the User's Image URL, a query is made to find out if a match already exists.
     * If a URL already exists, its address is passed to this User;
     * Otherwise, a new URL Object is created, and then its address is passed to this User.
     * @param dao The Data Access Object;
     * @param url The address of the image.
     */
    public void setDisplayImageSource(String url, DAO dao) {
        if (url.length() < 200) {
            URL matchingURL = URL.get(url, URL.IMAGE, this, dao);
            dao.open();
            try {
                if (matchingURL == null) {
                    URL imgSource = new URL(url, URL.IMAGE, this);
                    dao.set(imgSource);
                    this.image = imgSource.getAddress();
                } else {
                    this.image = matchingURL.getAddress();
                }
                dao.update(this);
                dao.commit();
            } catch (Exception e) {
                dao.rollBack();
            } finally {
                dao.closeSession();
            }
        }
    }

    public void setImageElement(String source) {
        this.imageElement = "<img src='" + source + "' alt='user-image'/>";
    }

    public void setEditableImageElement(String source) {
        this.editableImageElement = "<img id='user-image' src='" + source + "' alt='user-image' onclick='show_imageWindow()'/>";
    }

    /**
     * This method handles the String representation of all the game accounts the User has created.
     * Since all Account Id's are stored in one single String (separated by commas) with a fixed length of
     * 512 characters, a User object can only hold up to 64 different Account Id's.
     * @param id The game account id.
     */
    public void setAccounts(Long id) {
        if (this.accounts == null) {
            this.accounts = id.toString();
        } else {
            this.accounts += ";" + id.toString();
        }
    }

    /**
     * Max Length: 1;
     * M: Male; F: Female; U: Undefined.
     * @param gender The gender's initial letter, case sensitive.
     */
    public void setGender(String gender) {
        if (!gender.equals("M") && !gender.equals("F")) {
            this.gender = "U";
        } else {
            this.gender = gender;
        }
    }

    /**
     * Max length: 64;
     * @param mail The certified mail String.
     */
    public void setMail(String mail) {
        if (mail.length() > 39) {
            throw new IllegalArgumentException("The max length of the 'mail' field is 39");
        }
        this.mail = mail;
    }

    /**
     * Max length: 256;
     * @param password The User password.
     */
    public void setPassword(String password) {
        if (password.length() > 32) {
            throw new IllegalArgumentException("The max length of the 'password' field is 32");
        }
        try {
            String encryptedPassword = PasswordService.getInstance().encrypt(password);
            this.password = encryptedPassword;
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /**
     * Max length: 256;
     * @param signature The User signature that will appear on the end of posts.
     */
    public void setSignature(String signature) {
        this.signature = signature;
    }

    /**
     * Unlimited length;
     * Displays additional information about the User, providade by the User himself.
     * @param about The additional text.
     */
    public void setAbout(String about) {
        this.about = about;
    }

    public void setTotalPosts() {
        if (this.totalPosts < 0) {
            this.totalPosts = 1;
        } else {
            this.totalPosts++;
        }
    }

    public void setJoinDate(Date date) {
        this.joinDate = date;
    }

    /**
     * Max Length: 32;
     * @param website A User URL.
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /* Helpers */
    /**
     * Determines if the login information provided succeeded.
     * @param dao The Data Access Object;
     * @param userName The user name;
     * @param password The user password (not encrypted).
     * @return The matching User Object, <code>null</code> otherwise.
     */
    public static User login(String userName, String password, DAO dao) {
        try {
            if (userName != null && password != null) {
                String encryptedPassword = PasswordService.getInstance().encrypt(password);
                User user = (User) dao.getUniqueResult(
                        User.class,
                        Restrictions.eq("name", userName),
                        Restrictions.eq("password", encryptedPassword));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Checks whether the given combination of a field and a value already exists in the DB.
     * @param dao The Data Access Object;
     * @param field The target field;
     * @param value The target value.
     * @return <code>true</code> if a match can be found, <code>false</code> otherwise.
     */
    public boolean isAvailable(String field, String value, DAO dao) {
        List matches = dao.getList(
                User.class,
                Order.asc("id"),
                Restrictions.eq(field, value));
        if (matches.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the passed User object is valid by testing the name property.
     * (if it can be accessed, then it's a valid User)
     * @param user The User Object to be tested.
     * @return <code>true</code> if the Object is a valid User, <code>false</code> otherwise.
     */
    public static boolean isValid(User user) {
        if (user == null) {
            return false;
        } else {
            if (user.getName() == null) {
                return false;
            } else {
                return true;
            }
        }
    }

    public void setNewPassword(String password) {
        this.password = password;
    }

    /**
     * Looks for an entry that matches the field and value passed.
     * @param dao A Data Access Object instance;
     * @param field The target column name;
     * @param value The target entry value.
     * @return The <code>User</code> object if a result was found, <code>null</code> otherwise;
     */
    public static User find(String field, String value, DAO dao) {
        return (User) dao.getUniqueResult(
                User.class,
                Restrictions.eq(field, value));
    }

    /**
     * @return Whether this is a valid User.
     */
    public boolean isValid() {
        return isValid(this);
    }

    /**
     * Sets the HTML image elements.
     * @param source The image src attribute value.
     */
    public void setImageElements(String source) {
        setImageElement(source);
        setEditableImageElement(source);
    }

    /**
     * Gets all image URL's from this User.
     * @param dao The Data Access Object;
     * @return A <code>URL List</code>.
     */
    public List<URL> getImages(DAO dao) {
        return dao.getList(
                URL.class,
                Order.asc("address"),
                Restrictions.eq("user", this),
                Restrictions.eq("type", URL.IMAGE));
    }

    /**
     * Checks if an Account Id exists in the Account List of this User.
     * @param accountId The Account Id to search.
     * @return <code>true</code> if the Account Id exists in the Account List of this User,
     * <code>false</code> otherwise.
     */
    public boolean hasAccount(String accountId) {
        for (String account : getAccounts()) {
            if (account.equals(accountId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if an Account Id exists in the Account List of this User.
     * @param accountId The Account Id to search.
     * @return <code>true</code> if the Account Id exists in the Account List of this User,
     * <code>false</code> otherwise.
     */
    public boolean hasAccount(Long accId) {
        String accountId = accId.toString();
        for (String account : getAccounts()) {
            if (account.equals(accountId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Verifies if this User has an Account with this userId.
     * @param dao The Data Access Object;
     * @param accountUserId The Account userId.
     * @return <code>true</code> if a match is found, <code>null</code> otherwise.
     */
    public boolean hasAccountName(String accountUserId, DAO dao) {
        List userAccounts = Util.toLongList(this.accounts.split(";"));
        Criterion[] restrictions = {
            Restrictions.eq("userId", accountUserId),
            Restrictions.in("id", userAccounts)};
        Login match = (Login) dao.getUniqueResult(Login.class, restrictions);
        if (match != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Gets a Char Object that matches the passed char name.
     * The Object is only returned if the User owns the Account of that Char Object.
     * @param dao The Data Access Object;
     * @param charName The Char name.
     * @return A Char Object if a match is found and it belongs to an Account that is owned by this User, <code>null</code> otherwise.
     */
    public Char getChar(String charName, DAO dao) {
        Char character = Char.get(charName, dao);
        if (character != null) {
            Long accountId = character.getAccount().getAccountId();
            if (this.accounts != null) {
                if (this.accounts.contains(accountId.toString())) {
                    return character;
                }
            }
        }
        return null;
    }

    /**
     * Gets the Char Object in the most recently used Account at the first slot position with a Char created (i.e. not necessarily 0).
     * @param dao The Data Access Object.
     * @return A Char Object if this User has at least one Account with one Char, <code>null</code> otherwise.
     */
    public Char getFirstChar(DAO dao) {
        if (this.accounts != null) {
            Login account = getLastUsedAccount(dao);
            List<Char> chars = account.getChars(dao);
            if (chars != null) {
                Char firstChar = chars.get(0);
                return firstChar;
            }
        }
        return null;
    }

    /**
     * Gets the generic "Friends" Group, if it exists. Creates it, otherwise.
     * @param dao The Data Access Object.
     * @return The "Friends" Group.
     */
    public Group getFriendsGroup(DAO dao) {
        Group friends = (Group) dao.getFirstResult(
                Group.class,
                Order.asc("name"),
                Restrictions.eq("owner", this),
                Restrictions.eq("name", "Friends"));
        if (friends == null) {
            friends = new Group(this, "Friends");
            dao.open();
            try {
                dao.set(friends);
                dao.commit();
            } catch (Exception e) {
                dao.rollBack();
            } finally {
                dao.closeSession();
            }
        }
        return friends;
    }

    /**
     * Gets all Requests made to this User.
     * @param dao The Data Access Object.
     * @return All Requests made to this User.
     */
    public List<Request> getRequests(DAO dao) {
        return dao.getList(
                Request.class,
                Order.desc("date"),
                Restrictions.eq("type", Request.FRIENDSHIP),
                Restrictions.eq("to", id));
    }

    /**
     * Checks if this User has a Friendship Request from the given User id.
     * @param dao The Data Access Object;
     * @param userId The subject user id.
     * @return <code>true</code> if a Request exists, <code>false</code> otherwise.
     */
    public boolean hasRequestFrom(long userId, DAO dao) {
        List<Request> requests = dao.getList(
                Request.class,
                Order.desc("date"),
                Restrictions.eq("type", Request.FRIENDSHIP),
                Restrictions.eq("from", userId),
                Restrictions.eq("to", this.id));
        return requests == null ? false : true;
    }

    /**
     * Gets all Requests made to any account owned by this User. (if fed correctly).
     * @param dao The Data Access Object;
     * @param charIds All the Character ids owned by this User.
     * @return All Requests made to any Char of this account.
     */
    public List<Request> getCharRequests(List<Long> charIds, DAO dao) {
        return dao.getList(
                Request.class,
                Order.desc("date"),
                Restrictions.eq("type", Request.INGAME_FRIENDSHIP),
                Restrictions.in("to", charIds));
    }

    public List<Group> getGroups(DAO dao) {
        return dao.getList(
                Group.class,
                Order.asc("name"),
                Restrictions.eq("owner", this));
    }

    /**
     * Gets the User object with the given name.
     * @param dao The Data Access Object;
     * @param name The name to search for.
     * @return The matching User Object.
     */
    public static User get(String name, DAO dao) {
        return (User) dao.getUniqueResult(
                User.class,
                Restrictions.eq("name", name));
    }

    /**
     * Gets all this User's friends. Friends are represented as GroupMembership Objects.
     * @param dao The Data Access Object.
     * @return All this User's friends.
     */
    public List<UserContact> getFriends(DAO dao)
            throws IllegalAccessException, InvocationTargetException {
        CubeString query = new CubeString(
                "select distinct(u.nm_user), u.im_user ",
                "from user_group_membership m, user_account u, user_group g ",
                "where g.cd_group = m.cd_group and m.cd_member = u.cd_user and g.cd_group_owner = ", this.id,
                " group by cd_member order by cd_member");
        return dao.sqlList(query.toString(), UserContact.class);
    }

    /**
     * Gets a UserContact representation of this User.
     * @return a UserContact representation of this User.
     */
    public UserContact asContact() {
        return new UserContact(name, image);
    }

    /**
     * Removes all GroupMemberships between this User and the given User.
     * @param dao The Data Access Object;
     * @param friendId The friend User id.
     */
    public boolean unfriend(String friendName, DAO dao) {
        List<Group> groups = this.getGroups(dao);
        User friend = User.get(friendName, dao);
        List<Group> friendGroups = friend.getGroups(dao);
        if (groups.addAll(friendGroups)) {
            User[] members = {this, friend};
            List<GroupMembership> memberships = dao.getList(
                    GroupMembership.class,
                    Order.asc("member"),
                    Restrictions.in("group", groups),
                    Restrictions.in("member", members));
            if (memberships != null) {
                dao.open();
                try {
                    for (GroupMembership membership : memberships) {
                        dao.delete(membership);
                    }
                    dao.commit();
                    return true;
                } catch (Exception e) {
                    e.fillInStackTrace();
                } finally {
                    dao.closeSession();
                }
            }
        }
        return false;
    }

    /**
     * @param dao The Data Access Object.
     * @return all the Chars in all accounts owned by this User.
     */
    public List<Char> getChars(DAO dao) {
        return dao.getList(
                Char.class,
                Order.asc("name"),
                Restrictions.in("account.id", getAccountsAsLong()));
    }

    /**
     * @param dao The Data Access Object.
     * @return All the Char ids belonging to this User.
     */
    public List<Long> getCharIds(DAO dao) {
        return dao.queryList(
                "select id from Char where account.id in ("
                + getAccountsAsString().replace(";", ",")
                + ")");
    }

    /**
     * @param dao The Data Access Object.
     * @return All Mail objects sent to any Char belonging to this User.
     */
    public List<Mail> getInbox(DAO dao) {
        List<Long> charIds = getCharIds(dao);
        return dao.getList(
                Mail.class,
                Order.desc("time"),
                Restrictions.in("recipientId", charIds));
    }

    /**
     * @param dao The Data Access Object.
     * @return All Friends from all Chars owned by this User.
     */
    public List<Friend> getGameFriends(DAO dao) {
        List<Long> charIds = getCharIds(dao);
        return dao.getList(
                Friend.class,
                Order.asc("friendAccountId"),
                Restrictions.in("pk.char_.id", charIds));
    }

    /**
     * @param dao The Data Access Object.
     * @return The first Char, owned by this User, whose current map is one of the allowed capitals.
     */
    public Char getFirstCharInCity(DAO dao) {
        return (Char) dao.getFirstResult(
                Char.class,
                Order.desc("zeny"),
                Restrictions.in("account.id", getAccountsAsLong()),
                Restrictions.in("lastMap", Memo.CAPITALS));
    }

    /**
     * @param dao The Data Access Object.
     * @return All the Chars owned by this User and staying in one of the allowed capitals.
     */
    public List<Char> getCharsInCity(DAO dao) {
        return dao.getList(
                Char.class,
                Order.desc("zeny"),
                Restrictions.in("account.id", getAccountsAsLong()),
                Restrictions.in("lastMap", Memo.CAPITALS));
    }

    /**
     * @param dao The Data Access Object;
     * @param ids The char ids that are going to be checked.
     * @return Whether this User owns any one of the specified Char ids.
     */
    public boolean ownsAny(DAO dao, long... ids) {
        List<Long> charIds = getCharIds(dao);
        for (long charId : ids) {
            if (charIds.contains(charId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return whether this User has an undefined gender.
     */
    public boolean hasUndefinedGender() {
        if (getGender().equals("M") || getGender().equals("F")) {
            return false;
        }
        return true;
    }

    /**
     * @return whether this User's name is too short for a game account.
     */
    public boolean hasNameTooShort() {
        return getName().length() > Login.MIN_NAME_LENGTH ? false : true;
    }

    /**
     * @return Whether this User has reached the limit of Login accounts per User.
     */
    public boolean canCreateNewAccount() {
        return getAccounts().length < MAX_ACCOUNTS;
    }
}
