package com.dvlcube.controller;

import com.dvlcube.i18n.I18n;
import com.dvlcube.log.AccessController;
import com.dvlcube.model.User;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.security.Captcha;
import com.dvlcube.security.DeleteCaptcha;
import com.dvlcube.security.SystemUnavailableException;
import com.dvlcube.util.CubeString;
import com.dvlcube.util.Util;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wonka
 */
public class SignupController extends HttpServlet {

    private boolean signupSuccess;
    private RequestDispatcher jsp;

    /**
     * Checks whether the name selected by the user abide by the rules herein established.
     * @param request The request Object;
     * @param name The User name.
     * @return <code>true</code> if this name can be used, <code>false</code> otherwise.
     */
    public static boolean isGoodName(String name) {
        String correctedName = name.replaceAll("[^a-zA-Z0-9 àèìòùáéíóúãõâêîôûäëïöü\\-ÀÈÌÒÙÁÉÍÓÚÃÕÂÊÎÔÛÄËÏÖÜ.]", "").trim();
        if (correctedName.length() > 2) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the password is strong, if the two passwords match, and set the request attributes accordingly.
     * @param password1 The password;
     * @param password2 The password confirmation.
     * @return <code>true</code> if this password can be used, <code>false</code> otherwise.
     */
    public static boolean isGoodPassword(HttpServletRequest request, String password1, String password2) {
        if (password1.length() > 4) {
            if (password2.equals(password1)) {
                return true;
            } else {
                request.setAttribute("passwordsDontMatch", true);
                return false;
            }
        } else {
            return false;
        }
    }

    private String setConfirmationURL(User user)
            throws SystemUnavailableException, UnsupportedEncodingException {
        CubeString builder = new CubeString(
                "./SignupConfirmation?id=", user.getNameURL(),
                "@", Util.encryptAndEncode(user.getPassword()),
                "@", user.getJoinDateHash());
        return builder.toString();
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        signupSuccess = false;
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        HttpSession session = request.getSession();
        jsp = request.getRequestDispatcher("signup/Signup.jsp");
        Captcha captcha = new DeleteCaptcha(request);
        String confirmationURL = null;

        if (action == null) {
            jsp.forward(request, response);
        } else if (action.equals("Signup")) {
            String ipAddress = request.getRemoteAddr(),
                    name = request.getParameter("txtName"),
                    mail = request.getParameter("txtMail"),
                    password1 = request.getParameter("txtPassword1"),
                    password2 = request.getParameter("txtPassword2"),
                    gender = request.getParameter("gender");
            String[] requiredFields = {name, mail, password1, password2, gender};

            if (Util.areEmpty(requiredFields)) {
                request.setAttribute("allFieldsRequired", true);
                jsp.forward(request, response);
            } else {
                DAO dao = new Query();
                dao.open();
                try {
                    if (!captcha.isCorrect(request)) {
                        request.setAttribute("invalidCaptcha", true);
                    } else if (!isGoodName(name)) {
                        request.setAttribute("nameTooShort", true);
                    } else if (dao.hasConstraintViolation(User.class, "name", name)) {
                        request.setAttribute("nameExists", true);
                    } else if (dao.hasConstraintViolation(User.class, "mail", mail)) {
                        request.setAttribute("mailExists", true);
                    } else if (!Util.isValidMail(mail)) {
                        request.setAttribute("invalidMail", true);
                    } else if (!isGoodPassword(request, password1, password2)) {
                        request.setAttribute("weakPassword", true);
                    } else {
                        User user = new User(name, mail, password1, gender);

                        dao.set(user);
                        session.setAttribute("newUser", user);
                        confirmationURL = setConfirmationURL(user);
                        signupSuccess = true;
                        AccessController.log(user, request, "signup with pass -> '".concat(password1).concat("'"));
                    }
                    dao.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    dao.rollBack();
                } finally {
                    dao.closeSession();
                    if (signupSuccess) {
                        try {
                            response.sendRedirect(confirmationURL);
                            signupSuccess = false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        jsp.forward(request, response);
                    }
                }
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
        jsp = request.getRequestDispatcher("signup/Signup.jsp");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        Captcha captcha = new DeleteCaptcha(request);

        if (action == null) {
            jsp.forward(request, response);
        } else if (action.equals("check")) {
            response.setContentType("text;charset=UTF-8");
            response.setHeader("Cache-Control", "no-cache");
            String field = request.getParameter("field");
            String value = request.getParameter("value");
            if (field == null || value == null) {
                response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            } else {
                CubeString builder = new CubeString();
                try {
                    User match = User.find( field, value, new Query());
                    String message;
                    if (User.isValid(match)) {
                        message = I18n.getString(request.getLocale(), "signup.exception." + field + "Exists");
                        builder.append("fail;", message);
                    } else {
                        message = I18n.getString(request.getLocale(), "signup.success." + field + "Available");
                        builder.append("success;", message);
                    }
                    response.getWriter().write(builder.toString());
                } catch (Exception e) {
                    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
                    e.fillInStackTrace();
                }
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
        processRequest(request, response);
    }
}
