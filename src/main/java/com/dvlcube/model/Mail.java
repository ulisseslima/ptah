package com.dvlcube.model;

import com.dvlcube.model.character.Char;
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
import javax.persistence.Table;

/**
 * @author Wonka
 */
@Entity
@Table(name = "mail")
public class Mail implements Serializable {

    public Mail() {
    }

    /**
     * Constructs a new Mail item without zeny and Items.
     * @param sender The Char who is sending the message;
     * @param recipient The Char who will be receiving the message;
     * @param subject The message subject;
     * @param message The message content.
     */
    public Mail(Char sender, Char recipient, String subject, String message) {
        senderName = sender.getName();
        senderId = sender.getId();
        recipientName = recipient.getName();
        recipientId = recipient.getId();
        this.subject = subject;
        this.message = message;
        time = new Date().getTime() / 1000;
        read = false;
        zeny = 0;
        itemId = 0;
        itemAmount = 0;
        itemRefineRate = 0;
        itemAttribute = 0;
        isIdentified = false;
        card0 = 0;
        card1 = 0;
        card2 = 0;
        card3 = 0;
    }

    /**
     * Constructs a new Mail item.
     * @param sender The Char who is sending the message;
     * @param recipient The Char who will be receiving the message;
     * @param subject The message subject;
     * @param message The message content.
     * @param zeny The zeny amount;
     */
    public Mail(Char sender, Char recipient, String subject, String message, long zeny) {
        senderName = sender.getName();
        senderId = sender.getId();
        recipientName = recipient.getName();
        recipientId = recipient.getId();
        this.subject = subject;
        this.message = message.length() > 255 ? message.substring(0, 254) : message;
        time = new Date().getTime() / 1000;
        read = false;
        this.zeny = zeny;
        itemId = 0;
        itemAmount = 0;
        itemRefineRate = 0;
        itemAttribute = 0;
        isIdentified = false;
        card0 = 0;
        card1 = 0;
        card2 = 0;
        card3 = 0;
    }

    /**
     * Constructs a new Mail item.
     * @param sender The Char who is sending the message;
     * @param recipient The Char who will be receiving the message;
     * @param subject The message subject;
     * @param message The message content.
     * @param zeny The zeny amount;
     * @param item The Inventory Item to be attached to this message (can't be null).
     */
    public Mail(Char sender, Char recipient, String subject, String message,
            long zeny, GenericItem item, int itemAmount) {
        senderName = sender.getName();
        senderId = sender.getId();
        recipientName = recipient.getName();
        recipientId = recipient.getId();
        this.subject = subject;
        this.message = message.length() > 255 ? message.substring(0, 254) : message;
        time = new Date().getTime() / 1000;
        read = false;
        this.zeny = zeny;
        itemId = item == null ? 0 : item.getItemId();
        this.itemAmount = itemAmount;
        itemRefineRate = item == null ? 0 : item.getRefineRate();
        itemAttribute = item == null ? 0 : item.getAttribute();
        isIdentified = item == null ? false : item.isIdentified();
        card0 = item == null ? 0 : item.getCard0().getId();
        card1 = item == null ? 0 : item.getCard1().getId();
        card2 = item == null ? 0 : item.getCard2().getId();
        card3 = item == null ? 0 : item.getCard3().getId();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;
    @Column(name = "send_name")
    private String senderName;
    @Column(name = "send_id")
    private long senderId;
    @Column(name = "dest_name")
    private String recipientName;
    @Column(name = "dest_id")
    private long recipientId;
    @Column(name = "title")
    private String subject;
    @Column(name = "message")
    private String message;
    @Column(name = "`time`")
    private long time;
    @Column(name = "status")
    private boolean read;
    @Column(name = "zeny")
    private long zeny;
    @Column(name = "nameid")
    private long itemId;
    @Column(name = "amount")
    private int itemAmount;
    @Column(name = "refine")
    private int itemRefineRate;
    @Column(name = "attribute")
    private int itemAttribute;
    @Column(name = "identify")
    private boolean isIdentified;
    @Column(name = "card0")
    private long card0;
    @Column(name = "card1")
    private long card1;
    @Column(name = "card2")
    private long card2;
    @Column(name = "card3")
    private long card3;

    /* Getters */
    public long getId() {
        return id;
    }

    public String getSenderName() {
        return senderName;
    }

    /**
     * @return the sender name in a url friendly format.
     */
    public String getSenderUrl() {
        return senderName.replace(" ", "+");
    }

    public long getSenderId() {
        return senderId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    /**
     * @return the recipient's name in a url friendly format.
     */
    public String getRecipientUrl(){
        return recipientName.replace(" ", "+");
    }

    public long getRecipientId() {
        return recipientId;
    }

    public String getSubject() {
        return subject;
    }

    /**
     * @return A shorter version of the subject (limited by 50 characters).
     */
    public String getShortSubject() {
        if (subject == null) {
            return "~";
        }
        if (subject.length() > 50) {
            return subject.substring(0, 50) + "...";
        }
        return subject;
    }

    /**
     * @return The message contents. Tag delimiters are escaped, and if the message is empty, "~" is returned.
     */
    public String getMessage() {
        return message == null ? "~"
                : message.replace("<", "&lt;").replace(">", "&gt;").replace("\n", "<br>");
    }

    /**
     * Note: because the db design stores times as integers, we have to multiply time by 1000.
     * @return The date this Mail was sent.
     */
    public String getDateSent() {
        return Util.formatDate(new Date(time * 1000));
    }

    public boolean isRead() {
        return read;
    }

    public long getZeny() {
        return zeny;
    }

    public long getItemId() {
        return itemId;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public int getItemRefineRate() {
        return itemRefineRate;
    }

    public boolean isIdentified() {
        return isIdentified;
    }

    /* Setters */
    public void setAsRead(boolean read) {
        this.read = read;
    }

    public void setZeny(int zeny) {
        this.zeny = zeny;
    }

    /* Helpers */
    /**
     * Sends a message to all the Char recipients.
     * Note that if there isn't enough items for all recipients, some recipients might get only part of the items or no message at all. The same applies to zeny.
     * @param dao The Data Access Object;
     * @param sender The Char sending the message;
     * @param recipients The list of Chars receiving the message;
     * @param subject The message subject;
     * @param content The message contents;
     * @param zeny The message zeny amount;
     * @param item The item to be attached and the amount to take;
     * @param itemAmount The item amount.
     * @return Whether the message was successfully sent or could not be sent to all recipients.
     */
    public static boolean send(Char sender, List<Char> recipients, String subject, String content,
            long zeny, Object[] itemInfo, DAO dao) {
        GenericItem item = itemInfo[0] != null ? (GenericItem) itemInfo[0] : null;
        int itemAmount = (Integer) itemInfo[1];
        System.out.println("## sending mail:\n\titemAmount: " + itemAmount
                + " \n\tzeny: " + zeny);

        if ((item == null || itemAmount < 1) && zeny < 1) {
            System.out.println("$$ Mail.java: sending message with simple constructor.");
            for (Char recipient : recipients) {
                System.out.println("\t-> recipient: " + recipient.getName());
                if (recipient.getAccount().getAccountId() != sender.getAccount().getAccountId()) {
                    Mail message = new Mail(sender, recipient, subject, content);
                    dao.open();
                    dao.set(message);
                    dao.close();
                }
            }
        } else if ((item == null || itemAmount < 1) && zeny > 0) {
            System.out.println("## Mail.java: sending message with zeny.");
            for (Char recipient : recipients) {
                System.out.println("\t-> recipient: " + recipient.getName());
                System.out.println("\t-> account zeny: " + sender.getZeny());

                if (sender.getZeny() >= zeny) {
                    if (recipient.getAccount().getAccountId() != sender.getAccount().getAccountId()) {
                        Mail message = new Mail(sender, recipient, subject, content, zeny);
                        dao.open();
                        sender.setZeny(-zeny);
                        dao.update(sender);
                        dao.set(message);
                        dao.close();
                    }
                } else {
                    return false;
                }
            }
        } else {
            System.out.println("$$ Mail.java: sending message with full constructor.");
            for (Char recipient : recipients) {
                System.out.println("\t-> recipient: " + recipient.getName());
                System.out.println("\t-> account zeny: " + sender.getZeny());
                System.out.println("\t-> attached item: " + item.toString());

                if (recipient.getAccount().getAccountId() != sender.getAccount().getAccountId()) {
                    if (item.getAmount() < 1 && zeny < 1) {
                        return false;
                    }
                    Mail message = new Mail(sender, recipient, subject, content,
                            sender.getZeny() < zeny
                            ? sender.getZeny() : zeny,
                            item,
                            item == null
                            ? 0 : item.getAmount() < itemAmount
                            ? item.getAmount() : itemAmount);
                    dao.open();
                    if (zeny > 0) {
                        sender.setZeny(-zeny);
                        dao.update(sender);
                    }
                    if (itemAmount > 0) {
                        item.setAmount(-itemAmount);
                        dao.update(item);
                    }
                    dao.set(message);
                    dao.close();
                }
            }
        }
        return true;
    }

    /**
     * @param dao The Data Access Object;
     * @param user The User object.
     * @return Whether the specified user is allowed to view this message.
     */
    public boolean belongsTo(User user, DAO dao) {
        if (user.ownsAny(dao,
                this.senderId,
                this.recipientId)) {
            return true;
        }
        return false;
    }
}
