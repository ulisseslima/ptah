package com.dvlcube.model;

import java.io.Serializable;

/**
 *
 * @author Wonka
 */
public class UserContact implements Serializable, Contact {

    public UserContact() {
    }

    public UserContact(String name, String image) {
        this.name = name;
        this.image = image;
    }
    private String name;
    private String image;

    /* Getters */
    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    /* Setters */
    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /* Helpers */
    public String getNameURL() {
        return name.replace(" ", "+");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof UserContact)) {
            return false;
        }
        final UserContact other = (UserContact) obj;
        if ((this.name == null) ? (other.getName() != null) : !this.name.equals(other.getName())) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }
}
