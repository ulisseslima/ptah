package com.dvlcube.controller;

import com.dvlcube.i18n.I18n;
import com.dvlcube.model.Login;
import com.dvlcube.model.User;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.security.DeleteCaptcha;
import com.dvlcube.util.CubeString;
import com.dvlcube.util.Util;
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
public class NewAccountController extends HttpServlet {

    private RequestDispatcher jsp;
    private boolean newAccountSuccess;

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

        if (action == null) {
            forward(request, response);
        } else {
            Server.invokeAction(this, action, request, response);
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
        newAccountSuccess = false;
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        User user = Server.getSession(request);

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
     * Checks if the specified field and value already exists in the DB.
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
            DAO dao = new Query();
            dao.open();
            try {
                CubeString builder = new CubeString();
                String message;
                if (dao.hasConstraintViolation(Login.class, field, value)) {
                    message = I18n.getString(request.getLocale(), "account.exception." + field + "Exists");
                    builder.append("fail;", message);
                } else {
                    message = I18n.getString(request.getLocale(), "account.success." + field + "Available");
                    builder.append("success;", message);
                }
                response.getWriter().write(builder.toString());
                dao.commit();
            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                e.fillInStackTrace();
                dao.rollBack();
            } finally {
                dao.closeSession();
            }
        }
    }

    /**
     * Creates a new Login object.
     * @param request The request object;
     * @param response The response object;
     * @param user The current session.
     * @throws IOException if an I/O exception occurs;
     * @throws ServletException if a Servlet exception occurs.
     */
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    public void newAccount(HttpServletRequest request, HttpServletResponse response, User user)
            throws IOException, ServletException {
        String name = request.getParameter("txtName");
        String gender = request.getParameter("gender");
        String password1 = request.getParameter("txtPassword1");
        String password2 = request.getParameter("txtPassword2");
        String[] requiredFields = {name, gender, password1, password2};

        if (Util.areEmpty(requiredFields)) {
            request.setAttribute("allFieldsRequired", true);
        } else {
            Login account = null;
            DAO dao = new Query();
            dao.open();
            try {
                if (user.canCreateNewAccount()) {
                    if (!SignupController.isGoodName(name)) {
                        request.setAttribute("nameTooShort", true);
                    } else if (dao.hasConstraintViolation(Login.class, "userId", name)) {
                        request.setAttribute("nameExists", true);
                    } else if (!SignupController.isGoodPassword(request, password1, password2)) {
                        request.setAttribute("weakPassword", true);
                    } else {
                        if (user.getName().length() > 3) {
                            account = new Login(user.getMail(), name, password1, gender, request.getRemoteAddr());
                            dao.set(account);
                            user.setAccounts(account.getAccountId());
                            dao.update(user);
                        }
                        newAccountSuccess = true;
                    }
                    dao.commit();
                } else {
                    request.setAttribute("accountLimitReached", true);
                }
            } catch (Exception e) {
                e.printStackTrace();
                dao.rollBack();
            } finally {
                dao.closeSession();
                if (newAccountSuccess) {
                    response.sendRedirect("./NewChar?account=" + account.getUserId());
                } else {
                    forward(request, response);
                }
            }
        }
    }

    /**
     * Forwards to the default jsp page.
     * @param request The request object;
     * @param response The response object;
     */
    private void forward(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        jsp = request.getRequestDispatcher("account/NewAccount.jsp");
        jsp.forward(request, response);
    }
}
