package com.dvlcube.model.admin;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity(name = "MapReg")
@Table(name = "mapreg")
public class MapReg implements Serializable {

    public MapReg() {
    }
    @Id
    @Column(name = "varname")
    private String varName;
    @Column(name = "index")
    private long index;
    @Column(name = "`value`")
    private String value;

    /* Getters */
    public String getVarName() {
        return varName;
    }

    public long getIndex() {
        return index;
    }

    public String getValue() {
        return value;
    }

    /* Setters */
    public void setId(String varName) {
        this.varName = varName;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
