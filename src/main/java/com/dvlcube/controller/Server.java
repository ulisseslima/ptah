package com.dvlcube.controller;

import com.dvlcube.i18n.I18n;
import com.dvlcube.model.Login;
import com.dvlcube.model.Request;
import com.dvlcube.model.User;
import com.dvlcube.model.UserContact;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.CubeString;
import com.dvlcube.util.Util;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Wonka
 */
public class Server {

    public static org.slf4j.Logger logger = LoggerFactory.getLogger(Server.class);
    public static final String DOMAIN = "Domain";
    public static final String EMail = "EMail";
    public static final String MAX_CHARS = "MaxChars";
    public static final String SERVER_NAME = "ServerName";
    public static final String EATHENA_ROOT = "eAthenaRoot";
    public static Properties properties;
    /**
     * Default string code for a successful ajax response.
     */
    public static final String SUCCESS = "success;";
    /**
     * Default string code for an unsuccessful ajax response.
     */
    public static final String FAIL = "fail;";
    /**
     * Default string code for an ajax warning response.
     */
    public static final String WARNING = "warning;";
    /**
     * Default string code for an "illegal operation" ajax response
     */
    public static final String ILLEGAL = "illegal;";

    /**
     * Invokes a Servlet method based on its name.
     * @param servlet The Servlet to invoke actions from;
     * @param action The method name;
     * @param parameters The parameters.
     */
    public static void invokeAction(HttpServlet servlet, String action, Object... parameters) {
        try {
            Class[] classes = new Class[parameters.length];
            for (int i = 0; i < classes.length; i++) {
                if (parameters[i].getClass().getName().equals("org.apache.catalina.connector.RequestFacade")) {
                    classes[i] = Class.forName("javax.servlet.http.HttpServletRequest");
                } else if (parameters[i].getClass().getName().equals("com.jspbook.GZIPResponseWrapper")) {
                    classes[i] = Class.forName("javax.servlet.http.HttpServletResponse");
                } else if (parameters[i].getClass().getName().equals("org.apache.jasper.runtime.ServletResponseWrapperInclude")) {
                    classes[i] = Class.forName("javax.servlet.http.HttpServletResponse");
                } else if (parameters[i].getClass().getName().equals("org.netbeans.modules.web.monitor.server.MonitorRequestWrapper")) {
                    classes[i] = Class.forName("javax.servlet.http.HttpServletRequest");
                } else {
                    classes[i] = parameters[i].getClass();
                }
            }
            Method method = servlet.getClass().getMethod(
                    Util.initLower(action),
                    classes);
            method.invoke(servlet, parameters);
        } catch (NoSuchMethodException noSuchMethod) {
            error(noSuchMethod, "##" + noSuchMethod.getMessage() + "\n\tMake sure the mentioned class is added as an axception in the invokeAction method.");
        } catch (Exception e) {
            error(e);
        }
    }

    /**
     * Gets a ServletContext init parameter.
     * @param initParameter The parameter to get. One of the Server constants can be used;
     * @return The parameter value.
     */
    public static String get(String initParameter) {
        return properties.getProperty(initParameter);
    }

    /**
     * Gets the max number of chars allowed in one account.
     * @return the max number of chars allowed in one account.
     */
    public static int getMaxChars() {
        return Integer.parseInt(get(MAX_CHARS));
    }

    /**
     * Gets the Server Name.
     * @return the max number of chars allowd in one account.
     */
    public static String getName() {
        return get(SERVER_NAME);
    }

    /**
     * Gets the current User session.
     * @param request The request object.
     * @return The current User session.
     */
    public static User getSession(HttpServletRequest request) {
        return (User) request.getSession().getAttribute("userSession");
    }

    /**
     * Checks of the User in the current session has any pending requests with the given subjectUser and
     * checks if the User is already friends with the subjectUser.
     * @param request The request object;
     * @param subjectUser The user to check against;
     * @param friends The friends list.
     */
    public static void checkPendingRequest(HttpServletRequest request, User subjectUser) {
        try {
            User user = Server.getSession(request);
            if (User.isValid(user)) {
                if (subjectUser.hasRequestFrom(user.getId(), new Query())) {
                    request.setAttribute("isWaitingFriendApproval", true);
                }
                if (user.hasRequestFrom(subjectUser.getId(), new Query())) {
                    request.setAttribute("hasFriendRequestPending", true);
                }
                List<UserContact> friends = subjectUser.getFriends(new Query());
                if (friends != null) {
                    request.setAttribute("friends", friends);
                    if (friends.contains(user.asContact())) {
                        request.setAttribute("alreadyFriends", true);
                    }
                }
            }
        } catch (Exception e) {
            error(e, e.getMessage());
        }
    }

    /**
     * Gets the latest account used.
     * @param user The User session;
     * @param request The current request.
     */
    public static void getLastLogin(User user, HttpServletRequest request) {
        Login lastLogin = user.getLastUsedAccount(new Query());
        if (lastLogin != null) {
            request.setAttribute("lastLogin", lastLogin);
        }
    }

    /**
     * Gets all friend requests made to this User.
     * @param user The User session;
     * @param request The current request.
     */
    public static void getRequests(User user, HttpServletRequest request) {
        List<Request> userRequests = user.getRequests(new Query());
        request.setAttribute("userRequests", userRequests);
        if (userRequests != null) {
            request.setAttribute("hasRequests", true);
        }
    }

    /**
     * Gets this User's friends list.
     * @param user The User session;
     * @param request The current request.
     */
    public static void getFriends(User user, HttpServletRequest request) {
        try {
            List<UserContact> friends = user.getFriends(new Query());
            if (friends != null) {
                request.setAttribute("friends", friends);
            }
        } catch (Exception e) {
            error(e, e.getMessage());
        }
    }

    /**
     * Default process for returning a failed request message.
     * @param response The response object;
     * @param locale The Locale object.
     * @throws IOException if an I/O exception occurs.
     */
    public static void failed(HttpServletResponse response, Locale locale)
            throws IOException {
        String message = I18n.getString(locale, "request.fail");
        response.getWriter().write("fail;".concat(message));
    }

    /**
     * Writes a message to an ajax request.
     * @param response The response object;
     * @param locale The Locale object;
     * @param key The i18n key;
     * @param code The response code (whether the response was successful or special, and to control the style of the css message. A class constant should be used);
     * @throws IOException if an I/O exception occurs.
     */
    public static void write(HttpServletResponse response, Locale locale, String code, String key)
            throws IOException {
        String message = I18n.getString(locale, key);
        response.getWriter().write(new CubeString(code, message).toString());
    }

    /**
     * Writes a message to an ajax request.
     * @param response The response object;
     * @param locale The Locale object;
     * @param key The i18n key;
     * @param id The element id is used to determine which DOM element will be updated after the response is sent;
     * @throws IOException if an I/O exception occurs.
     */
    public static void writeSuccess(HttpServletResponse response, Locale locale, String key, long id)
            throws IOException {
        String message = I18n.getString(locale, key);
        response.getWriter().write(new CubeString(
                SUCCESS,
                message, ";",
                id).toString());
    }

    /**
     * Writes a message to an ajax request.
     * @param response The response object;
     * @param locale The Locale object;
     * @param key The i18n key;
     * @param id The element id is used to determine which DOM element will be updated after the response is sent;
     * @throws IOException if an I/O exception occurs.
     */
    public static void writeFail(HttpServletResponse response, Locale locale, String key, long id)
            throws IOException {
        String message = I18n.getString(locale, key);
        response.getWriter().write(new CubeString(
                FAIL,
                message, ";",
                id).toString());
    }

    /**
     * Writes a message to an ajax request.
     * @param response The response object;
     * @param locale The Locale object;
     * @param key The i18n key;
     * @param id The element id is used to determine which DOM element will be updated after the response is sent;
     * @param richContent Any content that doesn't contain safe characters.
     * @throws IOException if an I/O exception occurs.
     */
    public static void writeSuccess(HttpServletResponse response, Locale locale, String key, long id, String richContent)
            throws IOException {
        String message = I18n.getString(locale, key);
        response.getWriter().write(new CubeString(
                SUCCESS,
                message, ";",
                id,
                ";_dvlmsg:", richContent).toString());
    }

    /**
     * Writes a message to an ajax request.
     * @param response The response object;
     * @param locale The Locale object;
     * @param key The i18n key;
     * @param id The element id is used to determine which DOM element will be updated after the response is sent;
     * @param richContent Any content that doesn't contain safe characters.
     * @throws IOException if an I/O exception occurs.
     */
    public static void writeFail(HttpServletResponse response, Locale locale, String key, long id, String richContent)
            throws IOException {
        String message = I18n.getString(locale, key);
        response.getWriter().write(new CubeString(
                FAIL,
                message, ";",
                id,
                ";_dvlmsg:", richContent).toString());
    }

    public static void setConfiguration(Properties properties) {
        Server.properties = properties;
    }

    /**
     * Prints an error message.
     * @param throwable The throwable that was thrown;
     * @param message The optional message.
     */
    public static void error(Throwable throwable, String message) {
        message = message != null ? message
                : throwable.getMessage() != null ? throwable.getMessage()
                : "(no message) ";
        logger.error(throwable.toString() + message);
    }

    /**
     * Prints an error message.
     * @param throwable The throwable that was thrown;
     */
    public static void error(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * Redirects an unlogged User to the Login page, when the page in question requires authentication.
     * After login, the User is redirected to the page he tried to access.
     * @param request The request object;
     * @param response The response object.
     * @throws IOException if an I/O error occurs.
     */
    public static void redirectToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Util.redirectToLogin(request, response);
    }

    /**
     * @param parameter The String representation of a number.
     * @return The long value represented by the parameter string; 0 if a number cannot be extracted from the String.
     */
    static long getLong(String parameter) {
        return Util.getLong(parameter);
    }
}
