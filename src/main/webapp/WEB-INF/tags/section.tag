<%--Created on : 13/04/2010, 12:09:36
    Author     : Wonka--%>
<%@tag description="This tag generates a conditional section on the page. A section is a div element which uses the 'section' css class." pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="test" required="true" rtexprvalue="true" type="java.lang.Boolean"
             description="The condition to test. If this condition evals to true, the section will be rendered."%>
<%@attribute name="key" required="true" rtexprvalue="true"
             description="The key corresponding to the message that should be rendered as the title of this section." %>

<c:if test="${test == true}">
    <h4><fmt:message key="${key}"/></h4>
    <div class="section">
        <jsp:doBody/>
    </div>
</c:if>