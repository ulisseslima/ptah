package com.dvlcube.controller;

import com.dvlcube.util.Util;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class handles Ajax requests that do not require the User to be logged-in.
 * @author Wonka
 */
public class GetController extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("text;charset=UTF-8");
        String action = request.getParameter("a");

        if (action == null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            Server.invokeAction(this, action, response);
        }
    }

    /**
     * Creates a random name.
     * @param response The response object.
     * @throws IOException if an I/O exception occurs.
     */
    public void randomName(HttpServletResponse response)
            throws IOException {
        StringBuilder builder = new StringBuilder(Util.getRandomName());
        builder.append(" ");
        builder.append(Util.getRandomName());
        response.getWriter().write(builder.toString());
    }

    /**
     * Gets the Server Name, as configured in web.xml.
     * @param response The response object.
     * @throws IOException if an I/O exception occurs.
     */
    public void serverName(HttpServletResponse response)
            throws IOException {
        response.getWriter().write(getServletContext().getInitParameter("ServerName"));
    }
}
