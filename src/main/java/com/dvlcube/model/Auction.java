package com.dvlcube.model;

import com.dvlcube.model.character.Char;
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
@Table(name = "auction")
public class Auction implements Serializable {
    public Auction(){}

    public Auction(Char seller, Char buyer, Item item){

    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "auction_id")
    private long id;
    @Column(name = "seller_id")
    private long sellerId;
    @Column(name = "seller_name")
    private String sellerName;
    @Column(name = "buyer_id")
    private long buyerId;
    @Column(name = "buyer_name")
    private String buyerName;
    @Column(name = "price")
    private long price;
    @Column(name = "buynow")
    private long buyNow;
    @Column(name = "hours")
    private Integer hours;
    @Column(name = "`timestamp`")
    private long timeStamp;
    @Column(name = "nameid")
    private long nameId;
    @Column(name = "item_name")
    private String itemName;
    @Column(name = "type")
    private int type;
    @Column(name = "refine")
    private int refine;
    @Column(name = "attribute")
    private int attribute;
    @Column(name = "card0")
    private int card0;
    @Column(name = "card1")
    private int card1;
    @Column(name = "card2")
    private int card2;
    @Column(name = "card3")
    private int card3;

    /** Getters **/
    public long getId() {
        return id;
    }
    public long getSellerId(){
        return sellerId;
    }
    public String getSellerName(){
        return sellerName;
    }
    public long getBuyerId(){
        return buyerId;
    }
    public String getBuyerName(){
        return buyerName;
    }
    public long getPrice(){
        return price;
    }
    public long getBuyNow(){
        return buyNow;
    }
    public Integer getHours(){
        return hours;
    }
    public long getTimeStamp(){
        return timeStamp;
    }
    public long getNameId(){
        return nameId;
    }
    public String getItemName(){
        return itemName;
    }
    public int getType(){
        return type;
    }
    public int getRefine(){
        return refine;
    }
    public int getAttribute(){
        return attribute;
    }
    public int getCard0(){
        return card0;
    }
    public int getCard1(){
        return card1;
    }
    public int getCard2(){
        return card2;
    }
    public int getCard3(){
        return card3;
    }

    /** Setters **/
    public void setId(long id) {
        this.id = id;
    }
    public void setSellerId(long sellerId){
        this.sellerId = sellerId;
    }
    public void setSellerName(String sellerName){
        this.sellerName = sellerName;
    }
    public void setBuyerId(long buyerId){
        this.buyerId = buyerId;
    }
    public void setBuyerName(String buyerName){
        this.buyerName = buyerName;
    }
    public void setPrice(long price){
        this.price = price;
    }
    public void setBuyNow(long buyNow){
        this.buyNow = buyNow;
    }
    public void setHours(Integer hours){
        this.hours = hours;
    }
    public void setTimeStamp(long timeStamp){
        this.timeStamp = timeStamp;
    }
    public void setNameId(long nameId){
        this.nameId = nameId;
    }
    public void setItemName(String itemName){
        this.itemName = itemName;
    }
    public void setType(int type){
        this.type = type;
    }
    public void setRefine(int refine){
        this.refine = refine;
    }
    public void setAttribute(int attribute){
        this.attribute = attribute;
    }
    public void setCard0(int card0){
        this.card0 = card0;
    }
    public void setCard1(int card1){
        this.card1 = card1;
    }
    public void setCard2(int card2){
        this.card2 = card2;
    }
    public void setCard3(int card3){
        this.card3 = card3;
    }
}
