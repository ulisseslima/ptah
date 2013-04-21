package com.dvlcube.model.character;

import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author wonka
 */
@Embeddable
public class FriendPK implements Serializable {

    public FriendPK() {
    }

    public FriendPK(Char char_, Char friend) {
        this.char_ = char_;
        this.friend = friend;
    }
    @ManyToOne
    @JoinColumn(name = "char_id")
    private Char char_;
    @ManyToOne
    @JoinColumn(name = "friend_id")
    private Char friend;

    public Char getChar() {
        return char_;
    }

    public Char getFriend() {
        return friend;
    }
}
