<%--Created on : Dec 31, 2009, 7:59:23 PM
    Author     : Wonka--%>
<%@tag description="This thag generates a label and a text input field." pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="id" required="true" rtexprvalue="true"
             description="The id and name of the field."%>
<%@attribute name="password" required="false" rtexprvalue="true" type="java.lang.Boolean"
             description="Defines whether this input field is a password field."%>
<%@attribute name="label" required="true" rtexprvalue="true"
             description="The key name of the field."%>
<%@attribute name="function" required="false" rtexprvalue="true"
             description="A Javascript function to be called. The event must be passed as well, e.g. onclick=\"function()\"." %>
<%@attribute name="maxLength" required="true" rtexprvalue="true"
             description="The maximum length for this field."%>
<%@attribute name="size" required="false" rtexprvalue="true"
             description="The element size"%>
<%@attribute name="hint" required="false" rtexprvalue="true"
             description="A hint to be displayed to the user when this input's label is hovered."%>
<%@attribute name="hintKey" required="false" rtexprvalue="true"
             description="A hint key to display an i18n text message to the user when this input's label is hovered."%>
<%@attribute name="required" required="false" rtexprvalue="true"
             description="Whether this is a required field."%>
<%@attribute name="style" required="false" rtexprvalue="true"
             description="The CSS class name for this element."%>
<%@attribute name="specialAction" required="false" rtexprvalue="true"
             description="A Javascript method that will be triggered when the user hits the button." %>
<%@attribute name="specialActionTitle" required="false" rtexprvalue="true"
             description="The description of the special action" %>
<%@attribute name="value" required="false" rtexprvalue="true"
             description="A default value." %>

<c:if test="${not empty specialAction}">
    <button class="special-action" type="button" onclick="${specialAction}" title=<fmt:message key="${specialActionTitle}"/>> > </button>
</c:if><jsp:doBody/>
<label id="${id}-label" for="${id}"><fmt:message key="${label}"/></label>
    <%--<c:if test="${not empty hintKey or not empty hint}">
        <span><c:if test="${not empty hintKey}"><fmt:message key="${hintKey}"/></c:if> ${hint}</span>
    </c:if>    --%>
<input type="${password? 'password' : 'text'}" id="${id}" name="${id}" 
       class="${required? 'required-field':' '}${style}" maxlength="${maxLength}" ${function}
       <c:if test="${not empty size}">size="${size}"</c:if>
       <c:if test="${not empty value}">value="${value}"</c:if>
       <c:if test="${not empty hintKey}">title=${"\""}<fmt:message key="${hintKey}"/>${hint}${"\""}</c:if>/>