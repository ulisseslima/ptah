<%--Created on : Sep 20, 2010, 11:12:51 AM
    Author     : wonka--%>
<%@tag description="Creates an account element. The account element shows information about an account, such as all the chars contained." pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>

<%@attribute name="chars" required="true" rtexprvalue="true" type="java.util.List" description="The List of Chars in this Account." %>
<%@attribute name="account" required="false" rtexprvalue="true" type="java.lang.Object" description="The Login object. Defaults to 'account'." %>
<%@attribute name="charPage" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether a link to the Char's admin page should be shown." %>
<%@attribute name="allowRandomCreation" required="false" rtexprvalue="true" type="java.lang.Boolean" description="Whether 'generate random chars' should be enabled."%>

<div class="account">
    <h4>
        <dvl:a style="add" href="./NewChar?account=${account.userId}" tip="accounts.createNewChar">+</dvl:a>
        <dvl:a href="./Account?id=${account.accountId}" tip="accounts.adminAccount">${account.userId}</dvl:a>
        <dvl:a style="remove" href="#not_implemented_yet" tip="accounts.setAsPrivate">-</dvl:a>
    </h4>
    <div class="section" id="char-container">
        <c:if test="${allowRandomCreation}">
            <div class="round item">
                <form action="./CharAction" method="post">
                    <input type="hidden" name="randomAccount" value="${account.accountId}">
                    <dvl:submit action="PopulateAccount" label="beta.randomChars"/>
                </form>
            </div>
        </c:if>
        <c:if test="${chars != null}">
            <c:forEach var="char" items="${chars}">
                <div class="trans char-info" id="${char.id}">
                    <a class="black" href="${not empty charPage? "./Char?id=" : "#"}${char.nameURL}">${char.nameClassGuild}</a> 
                    <span class="level">${char.baseLevel}/${char.jobLevel}</span><br>
                    <div class="bar-container">
                        <div class="char-hp" style="width: ${char.currentHPPercentage}px" title="${char.HP} HP"></div>
                        <div class="char-sp" style="width: ${char.currentSPPercentage}px" title="${char.SP} SP"></div>
                    </div>
                    <span class="icon">☼${char.lastMap}</span>
                    <span class="icon">$${char.zeny}</span>
                </div>
            </c:forEach>
        </c:if>
    </div>
</div>