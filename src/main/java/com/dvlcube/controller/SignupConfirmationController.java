package com.dvlcube.controller;

import com.dvlcube.i18n.I18n;
import com.dvlcube.model.Login;
import com.dvlcube.model.User;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.security.PasswordService;
import com.dvlcube.security.SystemUnavailableException;
import com.dvlcube.util.GmailMailer;
import java.io.IOException;
import java.util.Locale;
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
public class SignupConfirmationController extends HttpServlet {

    private final String SERVER_NAME = Server.getName();
    private final String CONFIRMATION_MAIL_SENT = "confirmationMailSent";
    private final int NICKNAME = 0;
    private final int ENCRYPTED_PASSWORD = 1;
    private final int DATE_HASHCODE = 2;

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
        RequestDispatcher jsp = request.getRequestDispatcher("signup/SignupConfirmation.jsp");
        RequestDispatcher jsp_signupComplete = request.getRequestDispatcher("signup/SignupComplete.jsp");
        HttpSession session = request.getSession();
        User user_confirmation = (User) session.getAttribute("newUser");
        String requestToken = request.getParameter("id");
        String action = request.getParameter("action");

        if (requestToken != null) {
            String[] token = requestToken.split("@");
            if (action == null) {
                if (session.getAttribute(CONFIRMATION_MAIL_SENT) != null) {
                    //email ja foi enviado, a página jsp se encarrega de informar
                    //talvez checar se a senha ainda não foi modificada pra possíveis re-envios de confirmação?
                    System.out.println("## SignupConfirmationController: IP" + request.getRemoteAddr() + " entrou novamente na pagina de confirmação de cadastro.");
                } else {
                    try {
                        GmailMailer mailer = new GmailMailer(getServletContext());
                        String[] mailToUser = {user_confirmation.getMail(), "john_thefisherman@msn.com"};
                        mailer.sendEmail(
                                mailToUser,
                                setGreeting(request.getLocale(), user_confirmation, SERVER_NAME),
                                mailer.getDefaultMessage(user_confirmation, token, request));
                        session.setAttribute(CONFIRMATION_MAIL_SENT, true);
                        System.out.println("## SignupConfirmationController: a confirmation mail was sent to IP" + request.getRemoteAddr());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                jsp.forward(request, response);
            } else if (action.equals("confirm")) {
                DAO dao = new Query();
                User newUser = User.find( "name", token[NICKNAME], dao);
                dao.open();
                try {
                    if (isLegitConfirmation(token[ENCRYPTED_PASSWORD], token[DATE_HASHCODE], newUser)) {
                        /*FacebookContact facebookAccount = FacebookAuthFilter.getClient(session);
                        if (facebookAccount != null) {
                        newUser.setDisplayImageSource(new Query(), facebookAccount.getImage());
                        }*/
                        createFirstAccount(newUser, request, dao);
                        newUser.setNewPassword(token[ENCRYPTED_PASSWORD]);
                        newUser.setAbout(null);
                        dao.update(newUser);
                        dao.commit();
                    }
                    System.out.println("## SignupConfirmationController: " + newUser.getName() + " completed registration!");
                } catch (Exception e) {
                    dao.rollBack();
                    e.printStackTrace();
                } finally {
                    jsp_signupComplete.forward(request, response);
                    dao.closeSession();
                }
            }
        } else {
            response.sendRedirect("./Signup");
        }
    }

    /**
     * Creates a game account, if the User has a name greater than 3 characters long, has selected a gender and the account doesn't exist yet.
     * @param newUser The User object;
     * @param request The Servlet request;
     * @param dao The Data Access Object;
     */
    private void createFirstAccount(User newUser, HttpServletRequest request, DAO dao) {
        System.out.println("%% Checking if user is ellegible for a new account...");
        if (!newUser.hasNameTooShort()) {
            System.out.println("\t- name is not too short. OK;");
            if (!newUser.hasUndefinedGender()) {
                System.out.println("\t- gender is not undefined. OK;");
                if (!Login.exists( newUser.getName(), new Query())) {
                    System.out.println("\t- login account did not exist. OK;");
                    try {
                        Login login = new Login(newUser, newUser.getPassword(), request.getRemoteAddr());
                        dao.set(login);
                        newUser.setAccounts(login.getAccountId());
                        System.out.println("\t- first login created successfully.");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * @param encryptedPasswordToken The password chosen by the User, in its encrypted form;
     * @param dateHashToken The date hashcode of when this User signed up;
     * @param user The User object.
     * @return <code>true</code> if a user request for signup confirmation was legit, <code>false</code> otherwise.
     * @throws SystemUnavailableException
     */
    private boolean isLegitConfirmation(String encryptedPasswordToken, String dateHashToken, User user)
            throws SystemUnavailableException {
        if (User.isValid(user)) {
            String encryptedPassword = PasswordService.getInstance().encrypt(user.getPassword());
            if (encryptedPassword.equals(encryptedPasswordToken)) {
                if (dateHashToken.equals(user.getJoinDateHash())) {
                    System.out.println("$$ SignupConfirmation: confirmation url check successfull!");
                    return true;
                }
            }
        }
        System.out.println("## SignupConfirmation: confirmation check wasn't legit");
        return false;
    }

    /**
     * @param locale The User locale;
     * @param user The User object;
     * @param serverName The Server Name.
     * @return The right type of greeting according to the User's gender.
     */
    private String setGreeting(Locale locale, User user, String serverName) {
        if (user.getGender().equals("F")) {
            return user.getName() + I18n.getString(locale, "mail.title.f") + " " + serverName + "!";
        } else {
            return user.getName() + I18n.getString(locale, "mail.title.m") + " " + serverName + "!";
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
