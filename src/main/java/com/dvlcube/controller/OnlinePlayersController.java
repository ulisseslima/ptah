package com.dvlcube.controller;

import com.dvlcube.model.character.Char;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.CubeString;
import java.io.IOException;
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
public class OnlinePlayersController extends HttpServlet {

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

        List<Char> onlinePlayers = Char.getOnlinePlayers(new Query());
        if (action == null) {
            RequestDispatcher jsp = request.getRequestDispatcher("OnlinePlayers.jsp");
            request.setAttribute("onlinePlayers", onlinePlayers);
            jsp.forward(request, response);
        } else {
            Server.invokeAction(this, action, response, onlinePlayers);
        }
    }

    /**
     * Updates the list of online players.
     * @param response The response object;
     * @param onlinePlayers The List of online Chars.
     */
    public void check(HttpServletResponse response, List<Char> onlinePlayers) {
        if (onlinePlayers != null) {
            try {
                response.getWriter().write(CubeString.asCSV(onlinePlayers));
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
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
