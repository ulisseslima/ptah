package com.dvlcube.model;

import com.dvlcube.persistence.DAO;
import com.dvlcube.util.Util;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.Transient;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 * @author Ulisses
 * A <code>Group</code> can contain a collection of <code>User</code>s.
 */
@Entity
@Table(name = "user_group")
public class Group implements Serializable {

    /**
     * It is advised to use one of the constructors.
     * A <code>Group</code> can contain a collection of <code>User</code>s, for organization.
     */
    public Group() {
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cd_group")
    private long id;
    @ManyToOne
    @JoinColumn(name = "cd_group_owner")
    private User owner;
    @Column(name = "nm_group")
    private String name;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_group_created")
    private Date dateCreated;
    @Column(name = "ds_group")
    private String description;
    @Transient
    private List<GroupMembership> members;

    /**
     * Creates a <code>Group</code> without members.
     * A <code>Group</code> can contain a collection of <code>User</code>s, for organization.
     * @param owner The <code>Group</code> creator;
     * @param name The <code>Group</code> name;
     * @param description The <code>Group</code> description.
     */
    public Group(User owner, String name, String description) {
        try {
            this.owner = owner;
            this.name = name;
            this.description = description;
            dateCreated = new Date();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /**
     * Creates a <code>Group</code> without members.
     * A <code>Group</code> can contain a collection of <code>User</code>s, for organization.
     * @param owner The <code>Group</code> creator;
     * @param name The <code>Group</code> name;
     */
    public Group(User owner, String name) {
        try {
            this.owner = owner;
            this.name = name;
            dateCreated = new Date();
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    /****************************** Getters *******************************/
    public long getId() {
        return id;
    }

    public User getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getDateCreated() {
        return Util.formatDate(dateCreated);
    }

    public String getDescription() {
        return description;
    }

    /****************************** Setters *******************************/
    public void setId(long id) {
        this.id = id;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated() {
        this.dateCreated = new Date();
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            //this.description = "No description";
        } else {
            this.description = description;
        }
    }

    /****************************** Helpers *******************************/
    /**
     * Gets all Users in this group.
     * @return <code>User List</code>.
     */
    public List<GroupMembership> getMemberships(DAO dao) {
        List<GroupMembership> memberships = dao.getList(
                GroupMembership.class,
                Order.asc("member.name"),
                Restrictions.eq("group", this));
        return memberships;
    }

    /**
     * Persists all items in the array as <code>GroupMemberships</code>.
     * @param members The items must contain valid <code>User</code> names or e-mails.
     */
    public void setMembers(String[] members, DAO dao) {
        List<User> validMembers = dao.getList(
                User.class,
                Order.asc("name"),
                Restrictions.in("name", members));
        if (validMembers != null) {
            for (User member : validMembers) {
                if (getMembership(member, dao) == null) {
                    dao.openSession();
                    try {
                        GroupMembership membership = new GroupMembership(member, this);
                        dao.beginTransaction();
                        dao.set(membership);
                        dao.commit();
                    } catch (Exception e) {
                        dao.rollBack();
                    } finally {
                        dao.closeSession();
                    }
                }
            }
        }
    }

    public void deleteMemberships(DAO dao) {
        members = getMemberships(dao);
        if (members != null) {
            dao.openSession();
            try {
                for (GroupMembership membership : members) {
                    dao.beginTransaction();
                    dao.delete(membership);
                    dao.commit();
                }
            } catch (Exception e) {
                dao.rollBack();
            } finally {
                dao.closeSession();
            }
        }
    }

    /**
     * Gets a GroupMembership from a User that has a membership with this Group.
     * If a membership doesn't exist, <code>null</code> is returned.
     * @param dao The Data Access Object;
     * @param member The User Object;
     * @return The respective GroupMembership between this Group and the given User, <code>null</code> otherwise.
     */
    public GroupMembership getMembership(User member, DAO dao) {
        GroupMembership membership = (GroupMembership) dao.getUniqueResult(
                GroupMembership.class,
                Restrictions.eq("group", this),
                Restrictions.eq("member", member));
        return membership;
    }

    /**
     * Gets a Group Object. If it doesn't exist, it creates one.
     * @param dao The Data Access Object;
     * @param owner The User owner of the Group;
     * @param groupName The Group name.
     * @return The requested Group.
     */
    public static Group get(User owner, String groupName, DAO dao) {
        if (groupName.length() < 2) {
            throw new IllegalArgumentException("!! Group.java: invalid group name");
        }
        Group group = (Group) dao.getUniqueResult(
                Group.class,
                Restrictions.eq("owner", owner),
                Restrictions.eq("name", groupName));
        if (group == null) {
            group = new Group(owner, groupName);
            dao.open();
            try {
                dao.set(group);
                dao.commit();
            } catch (Exception e) {
                dao.rollBack();
            } finally {
                dao.closeSession();
            }
        }
        return group;
    }

    /**
     * Used to process new Groups. A list of Group names is passed, and only non-existant Groups are created.
     * @param dao The Data Access Object;
     * @param owner The User owner of the Group;
     * @param groupNames The Group names.
     */
    public static void process(User owner, String groupNames, DAO dao) {
        if (groupNames.startsWith(";")) {
            groupNames = groupNames.replaceFirst(";", "");
        }
        for (String groupName : groupNames.split(";")) {
            if (!groupName.isEmpty()) {
                if (groupName.length() > 1) {
                    Group group = (Group) dao.getUniqueResult(
                            Group.class,
                            Restrictions.eq("owner", owner),
                            Restrictions.eq("name", groupName));
                    if (group == null) {
                        group = new Group(owner, groupName);
                        dao.open();
                        try {
                            dao.set(group);
                            System.out.println("## Group.java: new Group -> '" + group.getName() + "'");
                            dao.commit();
                        } catch (Exception e) {
                            dao.rollBack();
                        } finally {
                            dao.closeSession();
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return this.name;
    }
}
