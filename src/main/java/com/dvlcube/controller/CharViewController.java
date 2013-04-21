package com.dvlcube.controller;

import com.dvlcube.model.character.Char;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
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
public class CharViewController extends HttpServlet {

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
        RequestDispatcher jsp = request.getRequestDispatcher("CharView.jsp");
        String id = request.getParameter("id");
        DAO dao = new Query();

        if (id == null) {
            //redirecionar pra conta?
        } else {
            //carregar informações públicas sobre o char
            Char character = Char.get(id, dao);
            if (character != null) {
                getPartyMembers(character, request);
                getGuildMembers(character, request);
                Server.checkPendingRequest(request, character.getUser());
                request.setAttribute("char", character);
                jsp.forward(request, response);
            } else {
                response.sendRedirect("./Index");
            }
        }
    }

    /**
     * Gets all the party members in this Char's Party.
     * @param character The requested Char;
     * @param request The current request.
     */
    private void getPartyMembers(Char character, HttpServletRequest request) {
        List<Char> partyMembers = character.getPartyMembers(new Query());
        request.setAttribute("partyMembers", partyMembers);
        if (character.getParty() != null) {
            long partyLeaderId = character.getParty().getLeader().getId();
            request.setAttribute("partyLeaderId", partyLeaderId);
        }
    }

    /**
     * Gets all the Guild members of the Guild this Char is in (if any).
     * @param character The requested Char;
     * @param request The current request.
     */
    private void getGuildMembers(Char character, HttpServletRequest request) {
        List<Char> guildMembers = character.getGuildMembers(new Query());
        request.setAttribute("guildMembers", guildMembers);
        if (character.getGuild() != null) {
            long guildLeaderId = character.getGuild().getOwner().getId();
            request.setAttribute("guildLeaderId", guildLeaderId);
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
