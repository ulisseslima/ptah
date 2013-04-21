package com.dvlcube.controller;

import com.dvlcube.model.Login;
import com.dvlcube.model.User;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.Util;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Manages a single <code>Login</code> object (including each of its <code>Char</code>s).
 * @author Wonka
 */
public class AccountController extends HttpServlet {

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
        RequestDispatcher jsp = request.getRequestDispatcher("account/AdminAccount.jsp");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userSession");
        String id = request.getParameter("id");

        Login account = null;
        if (User.isValid(user)) {
            account = user.getLastUsedAccount(new Query());
            if (id != null) {
                if (user.hasAccount(id)) {
                    account = user.getAccount(Long.parseLong(id), new Query());
                }
            }

            if (account.hasOnlineChars(new Query())) {
                request.setAttribute("warnCharIsOnline", true);
            }
            request.setAttribute("account", account);
            request.setAttribute("storage", account.getStorage(new Query()));
            request.setAttribute("chars", account.getChars(new Query()));
            jsp.forward(request, response);
        } else {
            Util.redirectToLogin(request, response);
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
    }// </editor-fold>
}
