package com.dvlcube.controller;

import com.dvlcube.log.AccessController;
import com.dvlcube.model.User;
import com.dvlcube.persistence.Query;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wonka
 */
public class LoginController extends HttpServlet {

    public static final String SUCCESS = "login.success";
    public static final String FAIL = "login.fail";

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
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("com.dvlcube.i18n.dvlcube", locale);

        String userName = request.getParameter("user");
        String password = request.getParameter("id");
        HttpSession session = request.getSession();

        response.setContentType("text;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        try {
            User user = User.login(userName, password, new Query());
            if (User.isValid(user)) {
                user.setLocale(request.getLocale());
                session.setAttribute("userSession", user);
                Server.write(response, locale,
                        Server.SUCCESS, LoginController.SUCCESS);
            } else {
                Server.write(response, locale,
                        Server.FAIL, LoginController.FAIL);
            }
            AccessController.log(user, request, "LoginController.java -> user: " + userName + " pass: " + password);
        } catch (Exception e) {
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
