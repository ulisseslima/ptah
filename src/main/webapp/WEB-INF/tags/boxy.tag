<%--Created on : 28/07/2010, 18:25:23
    Author     : Wonka--%>
<%@tag description="Creates a box thingy." pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="label" required="true" rtexprvalue="true" description="The box label's i18n key."%>
<%@attribute name="test" required="true" rtexprvalue="true" type="java.lang.Boolean" description="This test determines whether the box will be visible" %>
<%@attribute name="title" required="false" rtexprvalue="true" description="The element's title attribute." %>

<c:if test="${test}">
    <div class="round boxy" <c:if test="${not empty title}">title="${title}"</c:if>>
        <span class="round-top counter"><fmt:message key="${label}"/></span>
        <jsp:doBody/>
    </div>
</c:if>