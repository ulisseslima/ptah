package com.dvlcube.model.character;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity
@Table(name = "friends")
public class Friend implements Serializable {

    public Friend() {
    }

    public Friend(Char character, Char friend) {
        this.pk = new FriendPK(character, friend);
        this.friendAccountId = friend.getAccount().getAccountId();
    }
    @Id
    private FriendPK pk = new FriendPK();
    @Column(name = "friend_account")
    private long friendAccountId;

    /* Getters */
    public Char getChar() {
        return pk.getChar();
    }

    public long getFriendAccountId() {
        return friendAccountId;
    }

    public Char getFriend() {
        return pk.getFriend();
    }
}
