package com.dvlcube.controller;

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
 * Request info samples (a request to Index):
 * ## getPathInfo(): null
 * ## getRequestURI(): /ptah/Index
 * ## getRequestURL(): http://localhost:8080/ptah/Index
 * ## getQueryString(): cas=lol
 * @author Wonka
 */
public class AuthenticationController extends HttpServlet {

    private RequestDispatcher jsp;

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
        jsp = request.getRequestDispatcher("Login.jsp");
        String action = request.getParameter("action");

        if (action == null) {
            jsp.forward(request, response);
        } else {
            Server.invokeAction(this, action,
                    request, response);
        }
    }

    /**
     * Tries to create a session object from the specified login credentials.
     * @param request The request object;
     * @param response The response object;
     * @throws IOException if an I/O exception occurs;
     * @throws ServletException if a Servlet Exception occurs.
     */
    public void login(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        HttpSession session = request.getSession();
        String lastLocation = (String) session.getAttribute("lastLocation");
        String name = request.getParameter("txtName");
        String pass = request.getParameter("txtPassword");
        String[] requiredFields = {name, pass};

        if (Util.areEmpty(requiredFields)) {
            request.setAttribute("allFieldsRequired", true);
            jsp.forward(request, response);
        } else {
            User user = User.login(name, pass, new Query());
            if (User.isValid(user)) {
                user.setLocale(request.getLocale());
                session.setAttribute("userSession", user);
                if (lastLocation == null) {
                    response.sendRedirect("./Accounts");
                } else {
                    response.sendRedirect(lastLocation);
                    session.setAttribute("lastLocation", null);
                }
            } else {
                request.setAttribute("loginFail", true);
                jsp.forward(request, response);
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
