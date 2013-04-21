package com.dvlcube.model.character;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity
@Table(name = "mercenary_owner")
public class MercenaryOwner implements Serializable {
    public MercenaryOwner(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "char_id")
    private long id;
    @Column(name = "merc_id")
    private long mercenaryId;
    @Column(name = "arch_calls")
    private long archerCalls;
    @Column(name = "arch_faith")
    private long archerFaith;
    @Column(name = "spear_calls")
    private long spearCalls;
    @Column(name = "spear_faith")
    private long spearFaith;
    @Column(name = "sword_calls")
    private long swordCalls;
    @Column(name = "sword_faith")
    private long swordFaith;

    /* Getters */
    public long getId() {
        return id;
    }
    public long getMercenaryId(){
        return mercenaryId;
    }
    public long getArcherCalls(){
        return archerCalls;
    }
    public long getArcherFaith(){
        return archerFaith;
    }
    public long getSpearCalls(){
        return spearCalls;
    }
    public long getSpearFaith(){
        return spearFaith;
    }
    public long getSwordCalls(){
        return swordCalls;
    }
    public long getSwordFaith(){
        return swordFaith;
    }

    /* Setters */
    public void setId(long id) {
        this.id = id;
    }
    public void setMercenaryId(long id){
        this.mercenaryId = id;
    }
    public void setArcherCalls(long archerCalls){
        this.archerCalls = archerCalls;
    }
    public void setArcherFaith(long archerFaith){
        this.archerFaith = archerFaith;
    }
    public void setSpearCalls(long spearCalls){
        this.spearCalls = spearCalls;
    }
    public void setSpearFaith(long spearFaith){
        this.spearFaith = spearFaith;
    }
    public void setSwordCalls(long swordCalls){
        this.swordCalls = swordCalls;
    }
    public void setSwordFaith(long swordFaith){
        this.swordFaith = swordFaith;
    }
}