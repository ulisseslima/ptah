<%--Created on : 05/04/2010, 16:19:57
    Author     : Wonka--%>
<%@tag description="Shows all character assigned skills." pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="message"%>

<h4>Skills</h4>
<div class="center section">
    <c:forEach var="entry" items="${skills}">
        <div class="round hotkey">
            <span class="round-top counter">lv${entry.level}</span>
            <a target="_new" href="http://www.ragnadb.com.br/?p=skills&id=${entry.skill.id}">
                <img src="http://www.ragnadb.com.br/img/skill_icons/${entry.skill.id}.gif" title="${entry.skill.name} (lv${entry.level})" alt="skill icon"/>
            </a>
        </div>
    </c:forEach>
</div>