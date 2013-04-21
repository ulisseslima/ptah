package com.dvlcube.model;

import com.dvlcube.model.character.Char;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

/**
 *
 * @author Wonka
 */
@Entity
@Table(name = "user_request")
public class Request implements Serializable {

    public Request() {
    }

    /**
     * Creates a Request Object.
     * @param type The Request type is defined by the i18n String. Pick one from the constants in this class;
     * @param from The id of the Object creating the Request;
     * @param to The id (as String) of the destination of this Request;
     * @param message The (optional) message to send along.
     */
    public Request(String type, long from, String to, String message) {
        this.type = type;
        this.from = from;
        this.to = Long.parseLong(to);
        if (!message.isEmpty()) {
            this.message = message;
        }
    }

    /**
     * Creates a Request Object.
     * @param type The Request type is defined by the i18n String. Pick one from the constants in this class;
     * @param from The id of the Object creating the Request;
     * @param to The id (as String) of the destination of this Request;
     * @param message The (optional) message to send along;
     * @param groupNames The Group names.
     */
    public Request(String type, long from, String to, String message, String groupNames) {
        if (groupNames.startsWith(";")) {
            groupNames = groupNames.replaceFirst(";", "");
        }
        this.type = type;
        this.from = from;
        this.to = Long.parseLong(to);
        if (message != null) {
            this.message = message;
        }
        this.groupNames = groupNames;
        this.date = new Date();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cd_request")
    private long id;
    @Column(name = "ic_request_type")
    private String type;
    @Column(name = "cd_request_from")
    private long from;
    @Column(name = "cd_request_to")
    private long to;
    @Column(name = "ds_request")
    private String message;
    @Column(name = "nm_request_groups")
    private String groupNames;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dt_request")
    private Date date;
    @Transient
    public static String FRIENDSHIP = "request.friendship",
            INGAME_FRIENDSHIP = "request.friendlist";


    /* -----------------------------Getters */
    public long getId() {
        return id;
    }

    public String getIdString() {
        return String.valueOf(id);
    }

    public String getType() {
        return type;
    }

    public String getGroupNames() {
        return groupNames;
    }

    public String[] getGroupNamesArray() {
        return groupNames.split(";");
    }

    public Date getDate() {
        return date;
    }

    /**
     * Gets the Object that solicited this Request, for proper display.
     * The return type is relative to the request type.
     * @return The Object that solicited this Request.
     */
    public Object getFrom() {
        if (type.equals("request.friendship")) {
            return getAssociation(User.class, from);
        } else {
            return getAssociation(Char.class, from);
        }
    }

    /**
     * Gets the target Object of this Request for proper display.
     * The return type is relative to the request type.
     * @return The target Object of this Request.
     */
    public Object getTo() {
        if (type.equals("request.friendship")) {
            return getAssociation(User.class, to);
        } else {
            return getAssociation(Char.class, to);
        }
    }

    public long getFromId() {
        return from;
    }

    public long getToId() {
        return to;
    }

    public String getMessage() {
        return message;
    }

    /* -----------------------------Helpers */
    /**
     * Since requests can be made from more than one type, this method is used to get the right type.
     * @param entity The desired Entity;
     * @param id The desired id;
     * @return This Request's association type.
     */
    private Object getAssociation(Class entity, long id) {
        DAO dao = new Query();
        return dao.getUniqueResult(entity, Restrictions.eq("id", id));
    }

    public boolean isDuplicate(DAO dao) {
        List match = dao.getList(
                Request.class,
                Order.asc("type"),
                Restrictions.eq("type", getType()),
                Restrictions.eq("from", getFromId()),
                Restrictions.eq("to", getToId()));
        if (match == null) {
            return false;
        } else {
            return true;
        }
    }
}
