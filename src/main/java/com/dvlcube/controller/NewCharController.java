package com.dvlcube.controller;

import com.dvlcube.i18n.I18n;
import com.dvlcube.model.Login;
import com.dvlcube.model.User;
import com.dvlcube.model.character.Char;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.security.Captcha;
import com.dvlcube.security.DeleteCaptcha;
import com.dvlcube.util.Util;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This class controls the creation of new Chars.
 * @author Wonka
 */
public class NewCharController extends HttpServlet {

    private RequestDispatcher jsp;
    private boolean newCharSuccess;
    private Captcha captcha;

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
        String action = request.getParameter("action");
        captcha = new DeleteCaptcha(request);
        newCharSuccess = false;

        if (action == null) {
            //listar as contas pra escolher em qual quer criar
            String accountName = request.getParameter("account");
            User user = Server.getSession(request);
            if (User.isValid(user)) {
                if (accountName != null) {
                    if (user.hasAccountName(accountName, new Query())) {
                        Login account = user.getAccount(accountName, new Query());
                        int numChars = account.getNumChars(new Query());
                        if (numChars > Server.getMaxChars()) {
                            request.setAttribute("charLimitReached", true);
                            request.getRequestDispatcher("account/Accounts.jsp").forward(request, response);
                        } else {
                            forward(request, response);
                        }
                    } else {
                        response.sendRedirect("./Accounts");
                    }
                } else {
                    response.sendRedirect("./Accounts");
                }
            } else {
                Util.redirectToLogin(request, response);
            }
        } else {
            Server.invokeAction(this, action, request, response);
        }
    }

    /**
     * Used to check whether the specified field and value returns a match (to avoid duplicates).
     * @param request The request object;
     * @param response The response object.
     */
    public void check(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        String field = request.getParameter("field");
        String value = request.getParameter("value");

        if (field == null || value == null) {
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } else {
            StringBuilder builder = new StringBuilder();
            DAO dao = new Query();
            dao.open();
            try {
                String message;
                if (dao.hasConstraintViolation(Char.class, field, value)) {
                    message = "fail;" + I18n.getString(request.getLocale(), "char.exception." + field + "Exists");
                    builder.append(message);
                } else {
                    message = "success;" + I18n.getString(request.getLocale(), "char.success." + field + "Available");
                    builder.append(message);
                }
                response.getWriter().write(builder.toString());
                dao.commit();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                dao.rollBack();
            } finally {
                dao.closeSession();
            }
        }
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
        request.setCharacterEncoding("UTF-8");
        newCharSuccess = false;
        User user = Server.getSession(request);
        String action = request.getParameter("action");
        captcha = new DeleteCaptcha(request);

        if (User.isValid(user)) {
            if (action == null) {
                forward(request, response);
            } else {
                Server.invokeAction(this, action, request, response, user);
            }
        } else {
            Util.redirectToLogin(request, response);
        }
    }

    /**
     * Creates a new Char.
     * @param request The request object;
     * @param response The response object;
     * @param user The current session.
     * @throws IOException if an I/O exception occurs;
     * @throws ServletException if a Servlet exception occurs.
     */
    public void newChar(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException, ServletException {
        String serverName = Server.getName();
        long MAX_CHARS = Server.getMaxChars();
        String accountUserId = request.getParameter("account");
        Login account = user.getAccount(accountUserId, new Query());        

        if (account != null) {
            int charsInAccount = account.getNumChars(new Query());
            String name = request.getParameter("txtName");
            String stats = request.getParameter("stats");
            String[] requiredFields = {name, stats};
            DAO dao = new Query();
            dao.open();
            try {
                int[] attributeArray = Util.toIntArray(stats);
                if (charsInAccount >= MAX_CHARS) {
                    request.setAttribute("charLimitReached", true);
                } else if (!captcha.isCorrect(request)) {
                    request.setAttribute("invalidCaptcha", true);
                } else if (Util.areEmpty(requiredFields)) {
                    request.setAttribute("allFieldsRequired", true);
                } else if (name.contains(serverName) || dao.hasConstraintViolation(Char.class, "name", name)) {
                    request.setAttribute("nameExists", true);
                } else if (!SignupController.isGoodName(name)) {
                    request.setAttribute("nameTooShort", true);
                } else if (!Char.hasValid(attributeArray)) {
                    request.setAttribute("invalidAttributes", true);
                } else {
                    Char character = new Char(name, account, attributeArray, dao);
                    dao.set(character);
                    newCharSuccess = true;
                }
                dao.commit();
            } catch (Exception e) {
                dao.rollBack();
            } finally {
                dao.closeSession();
            }
        }
        if (newCharSuccess) {
            response.sendRedirect("./Account?id=" + account.getAccountId());
        } else {
            forward(request, response);
        }
    }

    /**
     * Forwards to the default jsp page.
     * @param request The request object;
     * @param response The response object;
     */
    private void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        jsp = request.getRequestDispatcher("account/NewChar.jsp");
        jsp.forward(request, response);
    }
}
