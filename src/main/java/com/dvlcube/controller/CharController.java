package com.dvlcube.controller;

import com.dvlcube.model.User;
import com.dvlcube.model.character.CartInventory;
import com.dvlcube.model.character.Char;
import com.dvlcube.model.character.Hotkey;
import com.dvlcube.model.character.Inventory;
import com.dvlcube.model.character.SkillEntry;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.Util;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Manages a single Char Object.
 * @author Wonka
 */
public class CharController extends HttpServlet {

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
        RequestDispatcher jsp = request.getRequestDispatcher("account/AdminChar.jsp");
        String id = request.getParameter("id");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userSession");
        DAO dao = new Query();

        Char character = null;
        if (User.isValid(user)) {
            if (id == null) {
                character = user.getFirstChar(dao);
            } else {
                character = user.getChar(id, dao);
                if (character != null) {
                    if (character.getAccount().hasOnlineChars(dao)) {
                        request.setAttribute("warnCharIsOnline", true);
                    }
                    List<Char> partyMembers = character.getPartyMembers(dao);
                    if (character.getParty() != null) {
                        long partyLeaderId = character.getParty().getLeader().getId();
                        request.setAttribute("partyLeaderId", partyLeaderId);
                    }
                    List<Char> guildMembers = character.getGuildMembers(dao);
                    if (character.getGuild() != null) {
                        long guildLeaderId = character.getGuild().getOwner().getId();
                        request.setAttribute("guildLeaderId", guildLeaderId);
                    }
                    List<Inventory> inventory = character.getInventory(dao);
                    List<CartInventory> cart = character.getCart(dao);
                    List<SkillEntry> skills = character.getSkills(dao);
                    List<Hotkey> hotkeys = character.getFixedNumberOfHotkeys(dao);
                    character.getJobBonuses();

                    request.setAttribute("hotkeys", hotkeys);
                    request.setAttribute("skills", skills);
                    request.setAttribute("cart", cart);
                    request.setAttribute("inventory", inventory);
                    request.setAttribute("partyMembers", partyMembers);
                    request.setAttribute("guildMembers", guildMembers);
                    request.setAttribute("char", character);

                    jsp.forward(request, response);
                } else {
                    response.sendRedirect("./Accounts");
                }
            }
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
