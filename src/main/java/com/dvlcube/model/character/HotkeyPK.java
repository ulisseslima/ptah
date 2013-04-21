package com.dvlcube.model.character;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *
 * @author Wonka
 */
@Embeddable
public class HotkeyPK implements Serializable {

    public HotkeyPK() {
    }

    HotkeyPK(int hotkey, Char character) {
        this.char_ = character;
        this.hotkey = hotkey;
    }
    @ManyToOne
    @JoinColumn(name = "char_id")
    private Char char_;
    @Column(name = "hotkey")
    private int hotkey;

    /* Getters */
    public Char getChar() {
        return char_;
    }

    public int getHotkey() {
        return hotkey;
    }
}
