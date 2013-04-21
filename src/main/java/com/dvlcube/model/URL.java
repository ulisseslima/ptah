package com.dvlcube.model;

import com.dvlcube.persistence.DAO;
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
import org.hibernate.criterion.Criterion;

/**
 *
 * @author Wonka
 */
@Entity
@Table(name = "url")
public class URL implements Serializable {

    public URL() {
    }

    public URL(String address, String type, User user) {
        this.address = address;
        this.type = type;
        this.user = user;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cd_url")
    private long id;
    @Column(name = "nm_url")
    private String address;
    @Column(name = "ds_url")
    private String type;
    @ManyToOne
    @JoinColumn(name = "cd_url_user")
    private User user;
    @Transient
    public static final String IMAGE = "image", VIDEO = "video";

    /* Getters */
    public long getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /* Helpers */
    /**
     * Converts a <code>URL List</code> into a String list.
     * @param urls The <code>URL List</code>.
     */
    public static String toString(List<URL> urls) {
        if (urls != null) {
            StringBuilder builder = new StringBuilder();
            for (URL url : urls) {
                builder.append(":obj_").append(url.getAddress());
            }
            return builder.toString().replaceFirst(":obj_", "");
        }
        return null;
    }

    /**
     * Gets a specific URL item.
     * @param dao The Data Access Object;
     * @param address The address to be searched;
     * @param type The type of URL to be searched;
     * @param user The User that owns the URL;
     * @return A URL Object, or null if a match can't be found.
     */
    public static URL get(String address, String type, User user, DAO dao) {
        Criterion[] restrictions = {
            Restrictions.eq("user", user),
            Restrictions.eq("type", type),
            Restrictions.eq("address", address)};
        URL match = (URL) dao.getUniqueResult(URL.class, restrictions);
        return match;
    }
}
