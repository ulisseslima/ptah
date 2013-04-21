<%--Created on : 11/01/2010, 14:42:15
    Author     : Wonka--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag description="Creates the main part of the page." pageEncoding="UTF-8"%>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="id" required="false" rtexprvalue="true" description="The id property of this element." %>
<%@attribute name="style" required="false" rtexprvalue="true" description="An additional css class." %>
<%@attribute name="key" required="false" rtexprvalue="true" description="A key to load a localized message."%>
<%@attribute name="subtitle" required="false" rtexprvalue="true" description="A simple message. Can alse be used in addition to a message key."%>
<%@attribute name="formAction" required="false" rtexprvalue="true" description="Should this element be a form, the servlet to which data will be sent" %>

<div class="${style}body"<c:if test="${not empty id}">id="${id}"</c:if>>
    <h2 onclick="collapse(this)">
        <c:if test="${not empty key}"><fmt:message key="${key}"/></c:if>
        ${subtitle}
    </h2>
    <c:choose>
        <c:when test="${not empty formAction}">
            <form action="./${formAction}" method="post" id="frm${formAction}">
                <jsp:doBody/>
            </form>
        </c:when>
        <c:otherwise>
            <jsp:doBody/>
        </c:otherwise>
    </c:choose>
</div>