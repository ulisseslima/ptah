<%--Created on : 11/01/2010, 13:16:46
    Author     : Wonka--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag description="This tag creates an aside element on the right side of the page." pageEncoding="UTF-8"%>

<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="titleKey" required="false" rtexprvalue="true"
             description="A i18n key to a String message."%>
<%@attribute name="title" required="false" rtexprvalue="true"
             description="The page title."%>

<div class="body">
    <h2>
        <c:if test="${not empty titleKey}">
            <fmt:message key="${titleKey}"/>
        </c:if>
        ${title}
    </h2>
    <jsp:doBody/>
</div>