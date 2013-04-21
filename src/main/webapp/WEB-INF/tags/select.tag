<%--Created on : Jun 8, 2010, 6:33:09 PM
    Author     : wonka--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@tag description="Creates a select element from a list of objects sent from a Servlet" pageEncoding="UTF-8"%>

<%@attribute name="id" required="true" rtexprvalue="true"
             description="Id of the element."%>
<%@attribute name="label" required="true" rtexprvalue="true"
             description="A i18n key representing this element's label"%>
<%@attribute name="items" required="true" rtexprvalue="true" type="java.util.List"
             description="The list of objects to be made selectable."%>
<%@attribute name="check" required="false" rtexprvalue="true"
             description="Specifies an id that will be compared to each item specified in the <strong>items</strong> attribute.
             If that String matches an option, that option will appear pre-selected."%>

<c:if test="${items != null}">
    <label for="${id}"><fmt:message key="${label}"/></label>
    <select id="${id}" name="${id}">
        <c:forEach var="item" items="${items}">
            <option value="${item.name}" ${item.id == check? 'selected' : ''}>
                ${item.name}
            </option>
        </c:forEach>
    </select>
</c:if>