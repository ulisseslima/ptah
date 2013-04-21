package com.dvlcube.model;

import com.dvlcube.persistence.DAO;
import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.Permission;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONException;

/**
 *
 * @author Wonka
 */
public class FacebookContact implements Serializable, Contact {

    public FacebookContact() {
    }

    public FacebookContact(long id, FacebookJsonRestClient facebook) {
        this.id = id;
        try {
            String query = "SELECT name,sex,pic_big_with_logo,email FROM user WHERE uid=" + id;
            JSONArray contact = (JSONArray) facebook.fql_query(query);
            name = contact.getJSONObject(0).getString("name");
            sex = contact.getJSONObject(0).getString("sex");
            image = contact.getJSONObject(0).getString("pic_big_with_logo");
            mail = contact.getJSONObject(0).getString("email");
            hasEmailPermission = facebook.users_hasAppPermission(Permission.EMAIL);
        } catch (JSONException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        } catch (FacebookException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
    }
    private long id;
    private String name;
    private String sex;
    private String image;
    private String phone;
    private String mail;
    private User user;
    private boolean hasEmailPermission;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex.substring(0, 1).toUpperCase();
    }

    public String getImage() {
        return image;
    }

    public String getPhone() {
        return phone;
    }

    public String getMail() {
        return mail;
    }

    /**
     * @return Whether this facebook user has given this app permission to get his email address.
     */
    public boolean hasEmailPermission() {
        return hasEmailPermission;
    }

    /**
     * @return the User account owned by this Facebook user.
     */
    public User getAccount() {
        return user;
    }

    /*---------------------------------- Helpers */
    /**
     * @param facebook
     * @return all friends from this FacebookContact.
     */
    public ArrayList<FacebookContact> getFriends(FacebookJsonRestClient facebook) {
        JSONArray friendIds = null;
        ArrayList<FacebookContact> contacts = new ArrayList<FacebookContact>();
        try {
            friendIds = facebook.friends_get();
            /*StringBuilder lol = new StringBuilder();
            for (int i = 0; i < friendIds.length(); i++) {
            try {
            lol.append(",").append(friendIds.getLong(i));
            } catch (JSONException jsonE) {
            Logger.getLogger(OnlinePlayersController.class.getName()).log(Level.SEVERE, null, jsonE);
            }
            }
            String lol2 = lol.toString().replaceFirst(",", "");
            String query = "SELECT name,pic,email FROM user WHERE uid in (" + lol2 + ")";
            contacts.addAll((List) facebook.fql_query(query));*/

            for (int i = 0; i < friendIds.length(); i++) {
                try {
                    Long fid = friendIds.getLong(i);
                    FacebookContact contact = new FacebookContact(fid, facebook);
                    contacts.add(contact);
                } catch (JSONException ex) {
                    Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        } catch (FacebookException ex) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
        }
        return contacts;
    }

    /**
     * @param dao The Data Access Object.
     * @return whether this Facebook user is registered in ptah.
     */
    public boolean isRegistered(DAO dao) {
        SocialAccount account = (SocialAccount) dao.getUniqueResult(
                SocialAccount.class,
                Restrictions.eq("facebookId", id));
        if (account == null) {
            return false;
        } else {
            dao.open();
            user = (User) dao.get(User.class, account.getId());
            dao.close();
        }
        return account == null ? false : true;
    }

    /**
     * Registers this Facebook user id to its ptah account.
     * @param dao The Data Access Object;
     * @param user The User that owns this Facebook account.
     */
    public void register(User user, DAO dao) {
        SocialAccount account = new SocialAccount(user.getId(), id);
        dao.open();
        dao.set(account);
        dao.close();
    }
}
