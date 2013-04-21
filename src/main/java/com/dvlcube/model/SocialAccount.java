package com.dvlcube.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Holds information about a <code>User</code>'s accounts in Social Networks.
 * @author Wonka
 */
@Entity
@Table(name = "user_social_account")
public class SocialAccount implements Serializable {

    public SocialAccount() {
    }

    public SocialAccount(long userId, long facebookId) {
        this.id = userId;
        this.facebookId = facebookId;
    }
    @Id
    @Column(name = "cd_user")
    private long id;
    @Column(name = "cd_facebook")
    private long facebookId;
    @Column(name = "cd_orkut")
    private long orkutId;

    public long getId() {
        return id;
    }

    public long getFacebookId() {
        return facebookId;
    }

    public long getOrkutId() {
        return orkutId;
    }

    /* Setters */
    public void setFacebookId(long facebookId) {
        this.facebookId = facebookId;
    }

    public void setOrkutId(long orkutId) {
        this.orkutId = orkutId;
    }
}
