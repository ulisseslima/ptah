package com.dvlcube.controller;

import com.dvlcube.filter.FacebookAuthFilter;
import com.dvlcube.model.FacebookContact;
import com.dvlcube.model.SocialAccount;
import com.dvlcube.model.User;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.Util;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wonka
 */
public class FacebookController extends HttpServlet {

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
        response.setContentType("text;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        String action = request.getParameter("action");
        Server.invokeAction(
                this, action,
                request, response);
    }

    /**
     * Loads Facebook Canvas
     * @param request The current request;
     * @param response The response.
     */
    public void start(HttpServletRequest request, HttpServletResponse response) {
        try {
            String askedPermission = ">" + request.getParameter("ask");
            if (askedPermission.equals(">email")) {
                request.getRequestDispatcher("facebook/Signup.jsp").forward(request, response);
            } else {
                FacebookContact client = FacebookAuthFilter.getClient(request.getSession());
                if (client != null) {
                    request.setAttribute("client", client);
                    if (client.isRegistered(new Query())) {
                        User userSession = client.getAccount();

                        request.getSession().setAttribute("userSession", userSession);
                        response.sendRedirect("./Accounts");
                    } else {
                        System.out.println("Facebook account not merged yet, redirecting to login form");
                        request.getRequestDispatcher("facebook/Facebook.jsp").forward(request, response);
                    }
                }
            }
        } catch (Exception e) {
            Server.error(e);
        }
    }

    /**
     * Binds a Facebook account to an ptah account.
     * @param request The current request;
     * @param response The response.
     */
    public void bindAccount(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Locale locale = request.getLocale();
        String userName = request.getParameter("name");
        String password = request.getParameter("pass");
        String facebookIdString = request.getParameter("fbId");
        long facebookId = 0;
        try {
            facebookId = Long.parseLong(facebookIdString);
        } catch (NumberFormatException nan) {
            Server.failed(response, locale);
        }
        String[] requiredFields = {userName, password, facebookIdString};
        System.out.println("Account info:"
                + "\n\tuserName->" + userName
                + "\n\tpassword->" + password
                + "\n\tfacebookId->" + facebookId);

        if (Util.areEmpty(requiredFields)) {
            Server.failed(response, locale);
        } else {
            User user = User.login(userName, password, new Query());
            if (User.isValid(user)) {
                DAO dao = new Query();
                dao.open();

                SocialAccount facebookAccount = (SocialAccount) dao.get(SocialAccount.class, user.getId());
                if (facebookAccount == null) {
                    facebookAccount = new SocialAccount(user.getId(), facebookId);
                    dao.set(facebookAccount);
                } else {
                    facebookAccount.setFacebookId(facebookId);
                    dao.update(facebookAccount);
                }

                FacebookContact facebookProfile = FacebookAuthFilter.getClient(request.getSession());
                if (facebookProfile != null) {
                    user.setDisplayImageSource(facebookProfile.getImage(), new Query());
                    dao.update(user);
                }

                dao.close();

                System.out.println("## New facebook account! id: " + facebookAccount.getFacebookId());
                request.getSession().setAttribute("userSession", user);
                Server.write(response, locale,
                        Server.SUCCESS, LoginController.SUCCESS);
            } else {
                Server.write(response, locale,
                        Server.FAIL, LoginController.FAIL);
            }
        }
    }

    /**
     * Creates a new ptah account and binds it to a Facebook account.
     * @param request
     * @param response
     */
    public void newAccount(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("txtName");
        String password = request.getParameter("txtPassword");
        String passwordConfirmation = request.getParameter("txtPassword2");
        /*if (newAccount.equals("true")) {

        if (formInfoIsOK(new Query(), userName, "mail", password, passwordConfirmation, request)) {
        User user = new User(userName, "mail", password, "gender");
        facebookAccount = new SocialAccount(user.getId(), facebookId);
        //create new account
        }
        }*/
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