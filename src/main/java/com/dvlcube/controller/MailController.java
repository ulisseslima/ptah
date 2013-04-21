package com.dvlcube.controller;

import com.dvlcube.model.GenericItem;
import com.dvlcube.model.Mail;
import com.dvlcube.model.User;
import com.dvlcube.model.character.Char;
import com.dvlcube.model.character.Friend;
import com.dvlcube.persistence.Query;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author wonka
 */
public class MailController extends HttpServlet {

    RequestDispatcher jsp;
    public static final String SUCCESS = "mail.load.success";
    public static final String FAIL = "mail.load.fail";

    /**
     * Initializes the mail system:
     * - Load available sender;
     * - Load inbox;
     * - Load contacts.
     * @param request servlet request;
     * @param response servlet response;
     * @param user user session;
     * @throws IOException
     * @throws ServletException
     */
    private void init(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException, ServletException {
        List<Mail> inbox = user.getInbox(new Query());

        List<Char> availableChars = user.getCharsInCity(new Query());
        request.setAttribute("availableChars", availableChars);
        List<Friend> friends = user.getGameFriends(new Query());
        request.setAttribute("friends", friends);

        //fazer uma lista com os chars que podem enviar email [tem que estar em uma das cidades]
        //pre selecionar o primeiro char do usuário

        //listar todos os amigos do rag na lista de possíveis destinatários
        //excluir chars da mesma conta do sender
        request.setAttribute("inbox", inbox);
        jsp.forward(request, response);
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        jsp = request.getRequestDispatcher("account/Mail.jsp");
        User user = Server.getSession(request);

        if (User.isValid(user)) {
            if (action == null) {
                init(request, response, user);
            } else {
                Server.invokeAction(this, action,
                        request, response, user);
            }
        } else {
            Server.redirectToLogin(request, response);
        }
    }

    /**
     * Loads a Mail message.
     * @param request servlet request
     * @param response servlet response
     * @param user The current session
     * @throws IOException if an I/O error occurs
     * @throws ServletException if a servlet-specific error occurs
     */
    public void getMail(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException, ServletException {
        long id = Server.getLong(request.getParameter("id"));
        System.out.println("%% Loading mail with id " + id);
        Mail mail = (Mail) Query.load(Mail.class, id);

        if (mail != null) {
            if (mail.belongsTo(user, new Query())) {
                Server.writeSuccess(response,
                        request.getLocale(),
                        SUCCESS,
                        id,
                        mail.getMessage());
                mail.setAsRead(true);
                Query dao = new Query();
                dao.open();
                dao.update(mail);
                dao.close();
            } else {
                Server.writeFail(response,
                        request.getLocale(),
                        FAIL,
                        id);
            }
        }
    }

    /**
     * Sends a Mail message to all the specified recipients.
     * @param request servlet request
     * @param response servlet response
     */
    public void sendMail(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        try {
            System.out.println("## sendMail active.");
            String senderName = request.getParameter("selectSender");
            String recipientsString = request.getParameter("txtRecipients");
            String subject = request.getParameter("txtSubject");
            String content = request.getParameter("txtContent");
            long zeny = Server.getLong(request.getParameter("txtZeny"));

            Char sender = Char.get(senderName, new Query());
            Object[] item = getItem(sender, request);

            if (user.hasAccount(sender.getAccount().getAccountId())) {
                List<Char> recipients = Char.get(
                        Char.split(recipientsString), new Query());
                boolean messageSent = Mail.send(
                        sender,
                        recipients,
                        subject, content, zeny,
                        item, new Query());

                if (messageSent) {
                    request.setAttribute("messageSent", true);
                } else {
                    request.setAttribute("notAllMessagesSent", true);
                }
            } else {
                request.setAttribute("messageNotSent", true);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            init(request, response, user);
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /** 
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /**
     * Gets an item based on an Item Info array.
     * @param itemInfo
     * @param request
     * @return an Inventory or Storage item, if an item can be loaded and the amount is greater than 0;
     * In other cases, the return is a <code>null</code> object and 0 amount. e.g.:
     * - If the request parameters are mal-formed;
     * - If the item amount is less than 1;
     * - If the Char that owns the item is online;
     * - If the item was pulled from the Storage and there are players from the same account online.
     */
    private Object[] getItem(Char sender, HttpServletRequest request) {
        final int SOURCE = 0;
        final int ID = 1;
        final int AMOUNT = 2;
        String itemString = request.getParameter("txtItem");

        if (itemString != null) {
            String[] itemInfo = itemString.split(","); //format: itemSource,itemId,itemAmount
            if (itemInfo.length == 3) {
                int itemAmount = 0;
                try {
                    itemAmount = Integer.parseInt(itemInfo[AMOUNT]);
                } catch (NumberFormatException e) {
                    itemAmount = 0;
                }

                if (itemAmount > 0) {
                    GenericItem item = null;
                    if (itemInfo[SOURCE].equals("storage")) {
                        try {
                            item = sender.getStorageItem(
                                    itemInfo[ID], new Query());
                        } catch (ConcurrentModificationException e) {
                            e.printStackTrace();
                            request.setAttribute("unlogChar", sender.getName());
                            return new Object[]{null, 0};
                        }
                    } else {
                        try {
                            item = sender.getInventoryItem(
                                    itemInfo[ID], new Query());
                        } catch (ConcurrentModificationException e) {
                            e.printStackTrace();
                            request.setAttribute("unlogAccount", sender.getAccount().getUserId());
                            return new Object[]{null, 0};
                        }
                    }
                    System.out.println("$$ Item attached: " + item.getName());
                    return new Object[]{item, itemAmount};
                }
            }
        }
        return new Object[]{null, 0};
    }
}
