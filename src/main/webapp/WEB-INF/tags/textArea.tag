<%--Created on : Jun 11, 2010, 12:54:56 AM
    Author     : wonka--%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@tag description="Creates a textarea element." pageEncoding="UTF-8"%>

<%@attribute name="id" required="true" rtexprvalue="true"
             description="This element's id string"%>
<%@attribute name="label" required="false" rtexprvalue="true"
             description="A i18n label to be displayed right above this element."%>
<%@attribute name="cols" rtexprvalue="true"
             description="Number of columns. Note: this may vary among browsers."%>
<%@attribute name="maxLength" required="true" rtexprvalue="true"
             description="Max amount of characters allowed. 0 for 'unlimited'"%>
<%@attribute name="required" required="false" rtexprvalue="true"
             description="Whether this is a required field."%>

<c:if test="${not empty label}"><label for="${id}"><fmt:message key="${label}"/></label></c:if><div id="char-count">0</div>
<textarea <c:if test="${required}">class="required-field" </c:if>id="${id}" name="${id}"
                                          cols="${empty cols? '50' : cols}" rows="2" maxlength="${maxLength}"
                                          onkeypress="limit(this)"><jsp:doBody/></textarea>