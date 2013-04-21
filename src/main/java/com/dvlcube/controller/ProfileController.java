package com.dvlcube.controller;

import com.dvlcube.model.User;
import com.dvlcube.persistence.Query;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wonka
 */
public class ProfileController extends HttpServlet {

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
        String id = request.getParameter("id");
        RequestDispatcher jsp = request.getRequestDispatcher("ProfileView.jsp");
        User userSession = Server.getSession(request);

        if (id == null) {
            if (User.isValid(userSession)) {
                try {
                    Server.getLastLogin(userSession, request);
                    Server.getFriends(userSession, request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                request.setAttribute("user", userSession);
                jsp.forward(request, response);
            } else {
                response.sendRedirect("./OnlinePlayers");
            }
        } else {
            User user = User.get(id, new Query());
            if (User.isValid(user)) {
                try {
                    Server.getLastLogin(user, request);
                    Server.checkPendingRequest(request, user);
                    request.setAttribute("user", user);
                    jsp.forward(request, response);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                response.sendRedirect("./OnlinePlayers");
            }
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
}
