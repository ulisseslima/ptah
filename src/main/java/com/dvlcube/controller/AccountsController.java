package com.dvlcube.controller;

import com.dvlcube.model.Login;
import com.dvlcube.model.Request;
import com.dvlcube.model.User;
import com.dvlcube.model.character.Char;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wonka
 */
public class AccountsController extends HttpServlet {

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
        RequestDispatcher jsp = request.getRequestDispatcher("account/Accounts.jsp");
        User user = Server.getSession(request);

        if (User.isValid(user)) {
            List<Long> charIds = new ArrayList();
            List<Login> accounts = user.getAccounts(new Query());
            if (accounts != null) {
                request.setAttribute("accounts", accounts);
                for (Login login : accounts) {
                    List<Char> chars = login.getChars(new Query());
                    if (chars != null) {
                        addCharIdsToQueryList(chars, charIds);
                        request.setAttribute(Long.toString(login.getAccountId()), chars);
                    }
                }
            }
            List<Request> charRequests = null;
            if (charIds.size() > 0) {
                charRequests = user.getCharRequests(charIds, new Query());
                if (charRequests != null) {
                    request.setAttribute("charRequests", charRequests);
                    request.setAttribute("hasRequests", true);
                }
            }

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
    }

    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void addCharIdsToQueryList(List<Char> chars, List list) {
        for (Char character : chars) {
            list.add(character.getId());
        }
    }
}
