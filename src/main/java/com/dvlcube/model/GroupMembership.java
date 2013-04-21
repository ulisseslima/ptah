package com.dvlcube.model;

import com.dvlcube.util.Util;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Ulisses
 */
@Entity
@Table(name = "user_group_membership")
public class GroupMembership implements Serializable{
    public GroupMembership(){}

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "cd_membership")
    private long id;

    @ManyToOne
    @JoinColumn(name = "cd_member")
    private User member;

    @ManyToOne
    @JoinColumn(name = "cd_group")
    private Group group;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_membership")
    private Date dateJoined;

    public GroupMembership(User member, Group group) {
        this.member = member;
        this.group = group;
        this.dateJoined = new Date();
    }
    
    /****************************** Getters *******************************/
    public long getId() {
        return id;
    }

    public User getMember(){
        return member;
    }

    public Group getGroup(){
        return group;
    }

    public String getDateJoined(){
        return Util.formatDate(dateJoined);
    }

    /****************************** Setters *******************************/
    public void setId(long id) {
        this.id = id;
    }

    public void setMember(User user){
        this.member = user;
    }

    public void setGroup(Group group){
        this.group = group;
    }

    public void setDateJoined(){
        this.dateJoined = new Date();
    }
}
