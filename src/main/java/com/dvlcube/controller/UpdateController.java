package com.dvlcube.controller;

import com.dvlcube.i18n.I18n;
import com.dvlcube.log.AccessController;
import com.dvlcube.model.Group;
import com.dvlcube.model.Login;
import com.dvlcube.model.Storage;
import com.dvlcube.model.URL;
import com.dvlcube.model.User;
import com.dvlcube.model.character.Char;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.CubeString;
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
public class UpdateController extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        User user = Server.getSession(request);
        String get = request.getParameter("get");

        if (User.isValid(user)) {
            if (get == null) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                response.setHeader("Cache-Control", "no-cache");
                response.setContentType("text;charset=UTF-8");
                Locale locale = request.getLocale();
                Server.invokeAction(this, get, request, response, locale, user);
            }
        } else {
            response.sendRedirect("./Authenticate");
        }
    }
    
    /**
     * Gets all the Groups created by the User in the current session. If there is none, the generic Group is created.
     * @param request The request object;
     * @param response The response object;
     * @param locale The request Locale;
     * @param user The current session.
     * @throws IOException if an I/O error occurs.
     */
    public void groups(HttpServletRequest request, HttpServletResponse response, Locale locale, User user)
            throws IOException {
        List<Group> groups = user.getGroups(new Query());
        if (groups == null) {
            String groupName = I18n.getString(locale, "groups.GenericGroup");
            Group.get(user, groupName, new Query());
            response.getWriter().write(groupName);
        } else {
            response.getWriter().write(CubeString.asCSV(groups));
        }
    }

    /**
     * Identifies an unidentified item using zeny from the richest Char in the account of the specified User.
     * @param request The request object;
     * @param response The response object;
     * @param locale The request Locale;
     * @param user The current session.
     * @throws IOException if an I/O error occurs.
     */
    public void appraisal(HttpServletRequest request, HttpServletResponse response, Locale locale, User user)
            throws IOException {
        Storage item = Storage.get(request.getParameter("item"), new Query());
        if (item != null) {
            if (!item.isIdentified()) {
                Long itemAccount = item.getAccountId();
                try {
                    if (user.hasAccount(itemAccount.toString())) {
                        Login account = user.getAccount(itemAccount, new Query());
                        if (account.hasOnlineChars(new Query())) {
                            String message = I18n.getString(locale, "account.hasOnlineChars");
                            response.getWriter().write("unlog;" + message);
                        } else {
                            Char richestChar = account.getRichestChar(new Query());
                            if (richestChar.getZeny() >= 200) {
                                item.setIdentified(1);
                                richestChar.setZeny(-200);
                                DAO dao = new Query();
                                dao.open();
                                dao.update(item);
                                dao.update(richestChar);
                                dao.close();
                                response.getWriter().write("success");
                            } else {
                                String message = I18n.getString(locale, "char.InsufficientZeny");
                                response.getWriter().write("nozeny;" + message);
                            }
                        }
                    } else {
                        String message = I18n.getString(locale, "exception.IllegalAccess");
                        response.getWriter().write("illegal;" + message);
                        AccessController.log(user, request, "an illegal operation: accessing account -> " + itemAccount);
                    }
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                }
            } else {
                String message = I18n.getString(locale, "account.storage.item.AlreadyIdentified");
                response.getWriter().write("identified;" + message);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    /**
     * Saves a new profile image for the specified User. The image is saved as a URL object.
     * @param request The request object;
     * @param response The response object;
     * @param locale The request Locale;
     * @param user The current session.
     * @throws IOException if an I/O error occurs.
     * @see URL
     */
    public void newImage(HttpServletRequest request, HttpServletResponse response, Locale locale, User user)
            throws IOException {
        String imageUrl = request.getParameter("imageUrl");
        try {
            response.getWriter().write(imageUrl);
            user.setDisplayImageSource(imageUrl, new Query());
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }

    /**
     * Creates a List composed by the specified User's URLs.
     * @param request The request object;
     * @param response The response object;
     * @param locale The request Locale;
     * @param user The current session.
     * @throws IOException if an I/O error occurs.
     * @see URL
     */
    public void imageList(HttpServletRequest request, HttpServletResponse response, Locale locale, User user)
            throws IOException {
        List<URL> imageList = user.getImages(new Query());
        if (imageList != null) {
            response.getWriter().write(URL.toString(imageList));
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}
