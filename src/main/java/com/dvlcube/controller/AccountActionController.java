package com.dvlcube.controller;

import com.dvlcube.model.Login;
import com.dvlcube.model.User;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.Util;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wonka
 */
public class AccountActionController extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        User user = Server.getSession(request);
        String action = request.getParameter("action");

        if (User.isValid(user)) {
            if (action == null) {
                response.sendRedirect("./Accounts");
            } else {
                Server.invokeAction(this, action, request, response, user);
            }
        } else {
            Util.redirectToLogin(request, response);
        }
    }

    /**
     * Reorder the Char slots.
     * @param request The request object;
     * @param response The response object;
     * @param user The current session.
     * @throws IOException if an I/O exception occurs.
     */
    public void reorder(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        String accountOrder = request.getParameter("account-order");
        if (accountOrder != null) {
            String accountId = accountOrder.split(";")[0];
            String charPositions = accountOrder.replaceFirst(accountId + ";", "");
            List<String> newOrder = Util.newList(charPositions.split(";"));
            if (user.hasAccount(accountId)) {
                Login account = user.getAccount(Long.parseLong(accountId), new Query());
                if (!account.hasOnlineChars(new Query())) {
                    try {
                        account.reorder(newOrder, new Query());
                    } catch (Exception e) {
                        request.setAttribute("reorderException", true);
                    }
                    response.sendRedirect("./Account?id=" + accountId);
                } else {
                    response.sendRedirect("./Account?id=" + accountId);
                }
            }
        }
    }

    public void sell(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        request.setAttribute("notImplementedYet", true);
    }

    public void mail(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        request.setAttribute("notImplementedYet", true);
    }

    public void auction(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException {
        request.setAttribute("notImplementedYet", true);
    }
}
