package com.dvlcube.controller;

import com.dvlcube.i18n.I18n;
import com.dvlcube.model.Group;
import com.dvlcube.model.GroupMembership;
import com.dvlcube.model.Request;
import com.dvlcube.model.User;
import com.dvlcube.model.UserContact;
import com.dvlcube.persistence.DAO;
import com.dvlcube.persistence.Query;
import com.dvlcube.util.Util;
import java.io.IOException;
import java.util.List;
import java.util.Locale;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Wonka
 */
public class UserController extends HttpServlet {

    /**
     * Checks if the requested User is already part of the user making the request, or if the Request was already accepted
     * by the given User.
     * @param user The User session;
     * @param requestedUser The requested User id (as String) or the Request Object.
     * @return <code>true</code> if that User was previously added, <code>false</code> otherwise.
     */
    private boolean friendAlreadyAdded(User user, Object requestedUser) {
        List<UserContact> friendList = null;
        User friendToBe = null;
        try {
            DAO dao = new Query();
            friendList = user.getFriends(dao);
            dao.open();
            if (requestedUser instanceof String) {
                friendToBe = (User) dao.get(User.class, (String) requestedUser);
            } else if (requestedUser instanceof Request) {
                Request request = (Request) requestedUser;
                friendToBe = (User) dao.get(User.class, request.getFromId());
            }
            dao.close();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        if (friendList != null && friendToBe != null) {
            if (friendList.contains(friendToBe.asContact())) {
                return true;
            }
        }
        return false;
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
     * Checks if the specified group name is a valid name.
     * @param groupName The group name.
     * @return <code>true</code> if the name is valid, <code>false</code> otherwise.
     */
    private boolean isValid(String groupName) {
        if (groupName != null) {
            if (groupName.length() > 1) {
                return true;
            }
        }
        return false;
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
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");
        response.setContentType("text;charset=UTF-8");
        Locale locale = request.getLocale();
        String action = request.getParameter("action");
        User user = Server.getSession(request);

        if (User.isValid(user)) {
            if (action == null) {
                //redirect to somewhere
            } else if (action.equals("friend")) {
                String requestedUserId = request.getParameter("id");
                String groupNames = request.getParameter("groups");
                String requestMessage = request.getParameter("message");
                String[] requiredFields = {requestedUserId, groupNames};
                if (!Util.areEmpty(requiredFields)) {
                    if (friendAlreadyAdded(user, requestedUserId)) {
                        String message = I18n.getString(locale, "request.AlreadyCompleted");
                        response.getWriter().write("fail;".concat(message));
                    } else {
                        Group.process(
                                user,
                                groupNames, new Query());
                        Request friendRequest = new Request(
                                Request.FRIENDSHIP,
                                user.getId(),
                                requestedUserId, requestMessage, groupNames);
                        if (friendRequest.isDuplicate(new Query())) {
                            String message = I18n.getString(locale, "request.isDuplicate");
                            response.getWriter().write("fail;".concat(message));
                        } else {
                            DAO dao = new Query();
                            dao.open();
                            try {
                                dao.set(friendRequest);
                                dao.commit();
                                String message = I18n.getString(locale, "request.success");
                                response.getWriter().write("success;".concat(message));
                            } catch (Exception e) {
                                dao.rollBack();
                            } finally {
                                dao.closeSession();
                            }
                        }
                    }
                } else {
                    response.getWriter().write(HttpServletResponse.SC_NO_CONTENT);
                }
            } else if (action.equals("acceptFriend")) {
                String requestId = request.getParameter("requestId");
                String groupCSV = request.getParameter("groups");
                String[] requiredFields = {requestId, groupCSV};

                if (!Util.areEmpty(requiredFields)) {
                    DAO dao = new Query();
                    dao.open();
                    try {
                        Request friendRequest = (Request) dao.get(
                                Request.class,
                                requestId);
                        if (friendRequest != null) {
                            if (user.getId() == friendRequest.getToId()) {
                                if (friendRequest.getType().equals(Request.FRIENDSHIP)) {
                                    if (friendAlreadyAdded(user, friendRequest)) {
                                        String message = I18n.getString(locale, "request.AlreadyCompleted");
                                        response.getWriter().write("fail;".concat(message) + ";".concat(friendRequest.getIdString()));
                                        dao.commit();
                                    } else {
                                        User sender = (User) friendRequest.getFrom();
                                        for (String groupNameFrom : friendRequest.getGroupNamesArray()) {
                                            if (isValid(groupNameFrom)) {
                                                GroupMembership membershipFrom = new GroupMembership(
                                                        user,
                                                        Group.get(sender, groupNameFrom, new Query()));
                                                dao.set(membershipFrom);
                                            }
                                        }
                                        String[] groupNamesTo = groupCSV.split(";");
                                        for (String groupNameTo : groupNamesTo) {
                                            if (isValid(groupNameTo)) {
                                                GroupMembership membershipTo = new GroupMembership(
                                                        sender,
                                                        Group.get(user, groupNameTo, new Query()));
                                                dao.set(membershipTo);
                                            }
                                        }
                                        String message = I18n.getString(locale, "request.accepted");
                                        response.getWriter().write("success;".concat(message).concat(" ".concat(sender.getName())) + ";".concat(friendRequest.getIdString()));
                                        dao.delete(friendRequest);
                                        dao.commit();
                                    }
                                } else {
                                    Server.failed(response, locale);
                                }
                            }
                        } else {
                            Server.failed(response, locale);
                        }
                    } catch (Exception e) {
                        //e.printStackTrace();
                        dao.rollBack();
                    } finally {
                        dao.closeSession();
                    }
                } else {
                    response.getWriter().write(HttpServletResponse.SC_NO_CONTENT);
                }
            } else if (action.equals("rejectFriend")) {
                String requestId = request.getParameter("requestId");
                if (requestId != null) {
                    DAO dao = new Query();
                    dao.open();
                    try {
                        Request friendRequest = (Request) dao.get(Request.class, requestId);
                        if (friendRequest != null) {
                            if (friendRequest.getToId() == user.getId()) {
                                if (friendRequest.getType().equals(Request.FRIENDSHIP)) {
                                    String message = I18n.getString(locale, "request.Rejected");
                                    response.getWriter().write("success;".concat(message) + ";".concat(friendRequest.getIdString()));
                                    dao.delete(friendRequest);
                                    return;
                                }
                            }
                        }
                        String message = I18n.getString(locale, "request.fail");
                        response.getWriter().write("fail;".concat(message) + ";".concat(friendRequest.getIdString()));
                    } catch (Exception e) {
                        //e.printStackTrace();
                    } finally {
                        dao.close();
                    }
                }
            } else if (action.equals("unfriend")) {
                String friendName = request.getParameter("id");
                if (user.unfriend(friendName, new Query())) {
                    String message = I18n.getString(locale, "request.Unfriend");
                    response.getWriter().write("success;".concat(message));
                } else {
                    Server.failed(response, locale);
                }
            } else {
                Server.failed(response, locale);
            }
        } else {
            Util.redirectToLogin(request, response);
        }
    }

    /**
     * Gets the Group chosen by the User in the current session. Or the default Group if none was specified.
     * @param dao The Data Access Object;
     * @param groupId The Group Id;
     * @param user The User Object.
     * @return A Group Object.
     */
    private Group getGroup(String groupId, User user, DAO dao) {
        Group group;
        if (!groupId.isEmpty()) {
            group = (Group) dao.get(
                    Group.class,
                    groupId);
        } else {
            group = user.getFriendsGroup(new Query());
        }
        return group;
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
