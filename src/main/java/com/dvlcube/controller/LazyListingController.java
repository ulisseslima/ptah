package com.dvlcube.controller;

import com.dvlcube.model.Item;
import com.dvlcube.model.User;
import com.dvlcube.persistence.LazyList;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.criterion.Order;

/**
 *
 * @author Wonka
 */
public class LazyListingController extends HttpServlet {

    List<Item> page = new ArrayList<Item>();
    long rowCount = Query.countRows("Item");
    int pageNumber = 1;
    int pageResults = 5;
    long pages = rowCount / pageResults;
    List<Long> pagess = new ArrayList<Long>();
    List<Item> accounts = new LazyList<Item>(
            new Query(),
            Item.class,
            Order.asc("japaneseName"),
            pageResults,
            rowCount);

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
        User user = Server.getSession(request);
        String action = request.getParameter("action");

        if (User.isValid(user)) {
            if (action == null) {
                page.clear();
                for (int i = 0; i < pageResults; i++) {
                    page.add(accounts.get(i));
                }
                request.setAttribute("accounts", page);
            } else {
                Server.invokeAction(this, action, request);
            }
            pagess.clear();
            for (int i = 0; i < pages; i++) {
                pagess.add(i, new Long(i + 1));
            }
            System.out.println("Pages? " + pagess.size() + " pages: " + pages);
            request.getSession().setAttribute("pages", pagess);
            request.getRequestDispatcher("admin/tests/Paginating.jsp").forward(request, response);
        } else {
            Util.redirectToLogin(request, response);
        }
    }

    /**
     * 
     * @param request
     * @param response 
     */
    public void next(HttpServletRequest request) {
        pageNumber++;
        page.clear();
        for (int i = (pageNumber * pageResults) - pageResults; i < (pageNumber * pageResults); i++) {
            page.add(accounts.get(i));
        }
        request.setAttribute("accounts", page);
    }

    /**
     * 
     * @param request
     * @param response 
     */
    public void previous(HttpServletRequest request) {
        pageNumber--;
        page.clear();
        for (int i = (pageNumber * pageResults) - pageResults; i < (pageNumber * pageResults); i++) {
            page.add(accounts.get(i));
        }
        request.setAttribute("accounts", page);
    }

    public void goToPage(HttpServletRequest request) {
        pageNumber = (int) Server.getLong(request.getParameter("pageNum"));
        page.clear();
        for (int i = (pageNumber * pageResults) - pageResults; i < (pageNumber * pageResults); i++) {
            page.add(accounts.get(i));
        }
        request.setAttribute("accounts", page);
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
