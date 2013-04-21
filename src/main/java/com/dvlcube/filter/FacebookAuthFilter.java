package com.dvlcube.filter;

import com.dvlcube.model.FacebookContact;
import com.google.code.facebookapi.FacebookException;
import com.google.code.facebookapi.FacebookJsonRestClient;
import com.google.code.facebookapi.FacebookWebappHelper;
import com.google.code.facebookapi.Permission;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * The Facebook User Filter ensures that a Facebook client that pertains to
 * the logged in user is available in the session object named "facebook.user.client".
 *
 * The session ID is stored as "facebook.user.session". It's important to get
 * the session ID only when the application actually needs it. The user has to
 * authorise to give the application a session key.
 *
 * @author Dave & Wonka
 */
public class FacebookAuthFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(FacebookAuthFilter.class);
    private String apiKey;
    private String secret;
    private String nextPage;
    private static final String FACEBOOK_USER_CLIENT = "facebook.user.client";

    public void init(FilterConfig filterConfig) throws ServletException {
        apiKey = filterConfig.getInitParameter("api_key");
        secret = filterConfig.getInitParameter("secret_key");
        nextPage = filterConfig.getInitParameter("next_page");

        if (apiKey == null || secret == null) {
            throw new ServletException("Cannot initialise Facebook User Filter because the "
                    + "facebook api_key or facebook secret_key filter init "
                    + "params have not been set. Check that they're there "
                    + "in your filter descriptor.");
        } else if (nextPage == null) {
            throw new ServletException("Cannot initialize because the 'next_page' parameter was not set in the filter config file");
        } else {
            logger.info("Using facebook API key: " + apiKey);
        }
    }

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {            
            MDC.put("ipAddress", req.getRemoteAddr());

            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;

            if (request.getParameter("action").equals("start")) {
                System.out.println("$$ FacebookFilter: initializing facebook filter...");
                HttpSession session = request.getSession(true);
                FacebookJsonRestClient userClient = getUserClient(session);
                if (userClient == null) {
                    logger.debug("User session doesn't have a Facebook API client setup yet. Creating one and storing it in the user's session.");
                    userClient = new FacebookJsonRestClient(apiKey, secret);
                    session.setAttribute(FACEBOOK_USER_CLIENT, userClient);
                }

                logger.trace("Creating a FacebookWebappHelper, which copies fb_ request param data into the userClient");
                FacebookWebappHelper facebook = new FacebookWebappHelper(request, response, apiKey, secret, userClient);

                if (request.getParameter("ask") == null) {
                    System.out.println("$$ FacebookFilter: ask parameter is null");
                    logger.trace(nextPage);
                    boolean redirectOccurred = facebook.requireLogin(nextPage);
                    if (redirectOccurred) {
                        return;
                    }
                    redirectOccurred = facebook.requireFrame(nextPage);
                    if (redirectOccurred) {
                        return;
                    }

                    long facebookUserID;
                    try {
                        facebookUserID = userClient.users_getLoggedInUser();
                    } catch (FacebookException ex) {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error while fetching user's facebook ID");
                        logger.error("Error while getting cached (supplied by request params) value "
                                + "of the user's facebook ID or while fetching it from the Facebook service "
                                + "if the cached value was not present for some reason. Cached value = {}", userClient.getCacheUserId());
                        return;
                    }

                    MDC.put("facebookUserId", String.valueOf(facebookUserID));
                } else if (request.getParameter("ask").equals("email")) {
                    String authUrl = Permission.authorizationUrl(
                            apiKey,
                            Permission.EMAIL);
                    facebook.redirect(authUrl);
                    System.out.println("## auth url: " + authUrl);
                    return;
                }
            }
            chain.doFilter(request, response);
            logger.info("run by " + request.getRemoteHost());
        } catch (Exception e) {
            //logger.error(e.getMessage());
            e.printStackTrace();
        } finally {
            MDC.remove("ipAddress");
            MDC.remove("facebookUserId");
        }
    }

    public void destroy() {
    }

    public static FacebookJsonRestClient getUserClient(HttpSession session) {
        return (FacebookJsonRestClient) session.getAttribute(FACEBOOK_USER_CLIENT);
    }

    /**
     * @param session The current session.
     * @return a FacebookContact based on the current Facebook session, can be null.
     */
    public static FacebookContact getClient(HttpSession session) {
        FacebookJsonRestClient facebook = getUserClient(session);
        FacebookContact client = null;
        try {
            //client.auth_getSession(request.getParameter("auth_token"));
            long clientId = facebook.users_getLoggedInUser();
            client = new FacebookContact(clientId, facebook);
        } catch (FacebookException ex) {
            //logger.error(ex.getMessage());
            ex.printStackTrace();
        }
        return client;
    }

    /**
     * Ask for user extended permissions.
     * @param requestedPermission
     * @param facebook
     */
    private boolean askedExtendedPermissions(String requestedPermission, FacebookWebappHelper facebook) {
        if (requestedPermission != null) {
            if (requestedPermission.equals("email")) {
                String authUrl = Permission.authorizationUrl(
                        apiKey,
                        Permission.EMAIL)
                        + "&redirect_uri=" + nextPage + "&ask=email";
                facebook.redirect(authUrl);
                System.out.println("## auth url: " + authUrl);
                return true;
            }
        }
        return false;
    }
}
