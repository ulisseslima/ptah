<%--Created on : 15/04/2010, 15:19:03
    Author     : Wonka--%>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="userId" required="true" rtexprvalue="true" description="The id of the User." %>
<%@attribute name="userName" required="false" rtexprvalue="true" description="The name of the User." %>
<%@attribute name="imageElement" required="true" rtexprvalue="true" description="The image element provided by the User class."%>
<%@attribute name="viewId" required="true" rtexprvalue="true" description="The view id used to display the requested profile." %>
<%@attribute name="servlet" required="true" rtexprvalue="true" description="The servlet name." %>

<div class="picture">${imageElement}</div>
<c:choose>
    <c:when test="${userSession.valid}">
        <c:choose>
            <c:when test="${userSession.id == userId}">
                <h3 class="tip"><fmt:message key="profile.YourProfile"/></h3>
            </c:when>
            <c:when test="${isWaitingFriendApproval}">
                <h3 class="tip"><fmt:message key="request.Pending"/></h3>
            </c:when>
            <c:when test="${hasFriendRequestPending}">
                <h3 class="warning"><a href="./Home"><fmt:message key="request.RespondTo"/></a></h3>
            </c:when>
            <c:when test="${alreadyFriends}">
                <dvl:button id="btnUnfriend" label="profile.Unfriend" function="unfriend('${userName}')"></dvl:button>
            </c:when>
            <c:otherwise>
                <dvl:button id="btnFriendship" label="request.friendship" function="requestFor('friendship')"/>
                <div id="groups"></div>
                <div id="confirm-request">
                    <hr>
                    <dvl:button id="btnRequestFriendship" label="request.Confirm" function="confirmRequest('friendship', '${userId}')">
                        <dvl:or key="cancel" url="./${servlet}?id=${viewId}"/>
                    </dvl:button>
                </div>
            </c:otherwise>
        </c:choose>
    </c:when>
    <c:otherwise>
        <a class="gray" href="./Accounts"><fmt:message key="request.friendship"/></a>
    </c:otherwise>
</c:choose>