<%--Created on : 01/04/2010, 11:54:12
    Author     : Wonka--%>
<%@tag description="Creates a group list of Chars. This group can have a leader." pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="of" required="true" rtexprvalue="true" type="java.util.List" description="The list containing a the group listing."%>
<%@attribute name="subtitle" required="true" rtexprvalue="true" description="The element title." %>
<%@attribute name="href" required="false" rtexprvalue="true" description="Where to send the user." %>
<%@attribute name="leader" required="false" rtexprvalue="true" description="The group leader." %>
<%@attribute name="showZeny" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether the zeny amount should be shown."%>

<c:if test="${of != null}">
    <h4>${subtitle}</h4>
    <div class="section" id="char-container">
        <c:forEach var="member" items="${of}">
            <div class="${member.online? "online " : ""}trans char-info${leader == member.id? " leader" : ""}">
                ${member.nameClassGuild}
                <span class="level">${member.baseLevel}/${member.jobLevel}</span><br>
                <div class="bar-container">
                    <div class="char-hp" style="width: ${member.currentHPPercentage}px" title="${member.HP} HP"></div>
                    <div class="char-sp" style="width: ${member.currentSPPercentage}px" title="${member.SP} SP"></div>
                </div>
                <span class="icon">☼${member.lastMap}</span>
                <c:if test="${showZeny}"><span class="icon">$${member.zeny}</span></c:if>
            </div>
        </c:forEach>
    </div>
</c:if>