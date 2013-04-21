<%--Created on : Sep 20, 2010, 11:32:36 AM
    Author     : wonka--%>
<%@tag description="Creates an anchor element with i18n facilities." pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@attribute name="href" required="true" rtexprvalue="true" description="Where to send the user to."%>
<%@attribute name="tip" required="true" rtexprvalue="true" description="A i18n key for the title attribute."%>
<%@attribute name="key" required="false" rtexprvalue="true" description="Should the body of this tag be a i18n key, use this property. Use tag body otherwise." %>
<%@attribute name="style" required="false" description="A css class for this element." %>

<a <c:if test="${not empty style}">class="${style}" </c:if>href="${href}" <c:if test="${not empty tip}">title=${"'"}<fmt:message key="${tip}"/>${"'"}</c:if>>
    <c:if test="${not empty key}"><fmt:message key="${key}"/></c:if><jsp:doBody/>
</a>