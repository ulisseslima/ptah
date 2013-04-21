<%--Created on : Dec 31, 2009, 7:46:35 PM
    Author     : Wonka--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="security" tagdir="/WEB-INF/tags/security/" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<dvl:page titleKey="signup.title" ajax="true" aidScript="Signup.js">
    <div id="main">
        <dvl:body key="signup.subtitle">
            <form action="./Signup" id="frmSignup" method="post" onsubmit="return checkForm('btnSignup')">
                <dvl:textInput id="txtName" label="signup.name" style="required-field" maxLength="23" function="onblur=\"checkAvailability('name', 'txtName')\""
                               hintKey="hint.signup.name"/>
                <dvl:textInput id="txtMail" label="signup.mail" style="required-field" maxLength="39" function="onblur=\"evalEmailAddress('mail', 'txtMail')\""
                               hintKey="hint.signup.mail"/>
                <dvl:textInput id="txtPassword1" label="user.password" style="required-field" maxLength="32" password="true"
                               hintKey="hint.signup.password"/>
                <dvl:textInput id="txtPassword2" label="user.passwordConfirmation" style="required-field" maxLength="32" password="true" function="onkeyup=\"checkMatch()\""/>

                <label><fmt:message key="user.gender"/>
                    <input type="radio" value="F" name="gender"><fmt:message key="user.gender.female"/>
                    <input type="radio" value="M" name="gender"><fmt:message key="user.gender.male"/>
                    <input type="radio" value="U" name="gender" checked><fmt:message key="user.gender.undefined"/>
                </label><br>
                
                <security:deleteCaptcha/>
                <hr>
                <dvl:submit action="Signup" label="signup.submit"/>

                <dvl:exception test="${invalidCaptcha}" type="fail" i18nMessage="captcha.invalidCaptcha"/>
                <dvl:exception test="${nameTooShort}" type="fail" i18nMessage="signup.exception.nameTooShort"/>
                <dvl:exception test="${nameExists}" type="fail" i18nMessage="signup.exception.nameExists"/>
                <dvl:exception test="${mailExists}" type="fail" i18nMessage="signup.exception.mailExists"/>
                <dvl:exception test="${invalidMail}" type="fail" i18nMessage="signup.exception.invalidMail"/>
                <dvl:exception test="${weakPassword}" type="fail" i18nMessage="signup.exception.weakPassword"/>
                <dvl:exception test="${allFieldsRequired}" type="fail" i18nMessage="forms.exception.allFieldsRequired"/>
                <dvl:exception test="${passwordsDontMatch}" type="fail" i18nMessage="signup.exception.passwordsDontMatch"/>
            </form>
        </dvl:body>
    </div>
</dvl:page>