package com.dvlcube.controller;

import com.dvlcube.i18n.I18n;
import com.dvlcube.model.Request;
import com.dvlcube.model.Storage;
import com.dvlcube.model.User;
import com.dvlcube.model.character.Char;
import com.dvlcube.model.character.Friend;
import com.dvlcube.model.character.Hotkey;
import com.dvlcube.model.character.Inventory;
import com.dvlcube.model.guild.Guild;
import com.dvlcube.model.guild.GuildMember;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.Util;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wonka
 */
public class CharActionController extends HttpServlet {

    /**
     * If the process went well, the response will return a success mesage.
     * If anything go wrong during the tests, e.g:
     * <ul>
     * <li>The Request is non-existant</li>
     * <li>The Request is of a different type</li>
     * <li>The User accepting this Request is not the target of the Request</li>
     * </ul>
     * Then a fail message will be returned.
     * @param requestFinished This is a boolean indicating whether everything was OK;
     * @param response The Response Object;
     * @param locale The request Locale.
     * @throws IOException If an I/O Exception occurs.
     */
    private void checkIf(boolean requestFinished, HttpServletResponse response, Locale locale)
            throws IOException {
        if (requestFinished) {
            String message = I18n.getString(locale, "request.success");
            response.getWriter().write("success;".concat(message));
        } else {
            String message = I18n.getString(locale, "request.fail");
            response.getWriter().write("fail;".concat(message));
        }
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
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text;charset=UTF-8");
        Locale locale = request.getLocale();
        String action = request.getParameter("action");
        User user = Server.getSession(request);

        if (User.isValid(user)) {
            if (action == null) {
                response.sendRedirect("./Accounts");
            } else {
                Server.invokeAction(this, action, request, response, locale, user);
            }
        } else {
            Util.redirectToLogin(request, response);
        }
    }

    @Deprecated
    public void $fixGuilds(HttpServletRequest request, HttpServletResponse response, Locale locale, User user)
            throws IOException {
        DAO dao = new Query();
        dao.open();
        for (int i = 9; i < 14; i++) {
            Guild guild = (Guild) dao.get(Guild.class, i);
            if (guild != null) {
                List<Char> members = guild.getMembers(new Query());
                if (members != null) {
                    for (Char member : members) {
                        GuildMember guildMember = new GuildMember(member);
                        dao.set(guildMember);
                        //System.out.println("\t## New membership: " + guildMember.getName() + "#" + guildMember.getGuild().getName());
                    }
                }
            }
        }
        dao.close();
        response.sendRedirect("./Index");
    }

    /**
     * Invite another Char to your party.
     * @param request The request object;
     * @param response The response object;
     * @param locale The request locale;
     * @param user The current session.
     * @throws IOException if an I/O exception occurs.
     */
    public void party(HttpServletRequest request, HttpServletResponse response, Locale locale, User user)
            throws IOException {
        String partyId = request.getParameter("partyId");
        //chamar para party
    }

    /**
     * Ask another Char to become part of your in-game friends list.
     * @param request The request object;
     * @param response The response object;
     * @param locale The request locale;
     * @param user The current session.
     * @throws IOException if an I/O exception occurs.
     */
    public void friend(HttpServletRequest request, HttpServletResponse response, Locale locale, User user)
            throws IOException {
        String charName = request.getParameter("charName");
        String requestedId = request.getParameter("toId");
        String requestMessage = request.getParameter("message");
        String[] requiredFields = {charName, requestedId};

        if (!Util.areEmpty(requiredFields)) {
            Char character = user.getChar(charName, new Query());
            if (character != null) {
                Request friendRequest = new Request(
                        Request.INGAME_FRIENDSHIP,
                        character.getId(),
                        requestedId, requestMessage);
                if (friendRequest.isDuplicate(new Query())) {
                    String message = I18n.getString(locale, "request.isDuplicate");
                    response.getWriter().write("fail;".concat(message));
                } else {
                    DAO dao = new Query();
                    dao.open();
                    try {
                        dao.set(friendRequest);
                        dao.commit();
                        String message = I18n.getString(locale, "request.success");
                        response.getWriter().write("success;".concat(message));
                    } catch (Exception e) {
                        dao.rollBack();
                    } finally {
                        dao.closeSession();
                    }
                }
            } else {
                String message = I18n.getString(locale, "request.fail");
                response.getWriter().write("illegal;".concat(message));
            }
            return;
        }
        response.getWriter().write(HttpServletResponse.SC_NO_CONTENT);
    }

    /**
     * Accept a request to become part of another Char's friends list.
     * @param request The request object;
     * @param response The response object;
     * @param locale The request locale;
     * @param user The current session.
     * @throws IOException if an I/O exception occurs.
     */
    public void acceptFriend(HttpServletRequest request, HttpServletResponse response, Locale locale, User user)
            throws IOException {
        boolean requestFinished = false;
        DAO dao = new Query();
        dao.openSession();
        try {
            Request friendRequest = (Request) dao.get(
                    Request.class,
                    request.getParameter("requestId"));
            if (friendRequest != null) {
                if (friendRequest.getType().equals(Request.INGAME_FRIENDSHIP)) {
                    Char userChar = (Char) friendRequest.getTo();
                    if (user.hasAccount(userChar.getAccount().getAccountId())) {
                        Friend friend = new Friend(
                                userChar,
                                (Char) friendRequest.getFrom());
                        Friend requestedFriend = new Friend(
                                (Char) friendRequest.getFrom(),
                                userChar);
                        dao.beginTransaction();
                        dao.set(friend);
                        dao.set(requestedFriend);
                        dao.delete(friendRequest);
                        dao.commit();
                        requestFinished = true;
                    }
                }
            }
        } catch (Exception e) {
            dao.rollBack();
        } finally {
            dao.closeSession();
        }
        checkIf(requestFinished, response, locale);
    }

    /**
     * Sends an invitation for another Char to join your Guild.
     * @param request The request object;
     * @param response The response object;
     * @param locale The request locale;
     * @param user The current session.
     * @throws IOException if an I/O exception occurs.
     */
    public void guild(HttpServletRequest request, HttpServletResponse response, Locale locale, User user)
            throws IOException {
        String guildId = request.getParameter("guildId");
        //add to guild
    }

    /**
     * Creates 3 random Chars and 300 random Items in the specified Login account.
     * @param request The request object;
     * @param response The response object;
     * @param locale The request locale;
     * @param user The current session.
     * @throws IOException if an I/O exception occurs.
     */
    public void populateAccount(HttpServletRequest request, HttpServletResponse response, Locale locale, User user)
            throws IOException {
        String accountId = request.getParameter("randomAccount");
        List<Char> chars = Char.randomize(accountId, 3, new Query());
        Storage.randomize(accountId, 50, new Query());
        Inventory.randomize(chars, 20, new Query());
        Hotkey.assign(chars, new Query());

        response.sendRedirect("./Accounts");
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
}
