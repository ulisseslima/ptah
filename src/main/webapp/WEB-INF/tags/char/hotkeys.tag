<%--Created on : 02/04/2010, 13:48:03
    Author     : Wonka--%>
<%@tag description="put the tag description here" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@attribute name="slots"%>

<h4>Hotkeys</h4>
<div class="short section">
    <c:forEach var="hotkey" items="${hotkeys}" varStatus="i">
        <div class="round hotkey">
            <span class="round-top counter">${i.count}</span>
            <c:choose>
                <c:when test="${hotkey == null}"></c:when>
                <c:when test="${hotkey.type == 1}">
                    <a target="_new" href="http://www.ragnadb.com.br/?p=skills&id=${hotkey.itemOrSkillId}">
                        <img src="http://www.ragnadb.com.br/img/skill_icons/${hotkey.itemOrSkillId}.gif" title="[${hotkey.hotkey +1}] ${hotkey.entry.name} (lv${hotkey.skillLevel == 0? "max" : hotkey.skillLevel})" alt="icon"/>
                    </a>
                </c:when>
                <c:otherwise>
                    <a target="_new" href="http://ragnadb.com.br/item.php?id=${hotkey.itemOrSkillId}">
                        <img src="http://www.ragnadb.com.br/img/small/${hotkey.itemOrSkillId}.gif" title="[${hotkey.hotkey +1}] ${hotkey.entry.name}" alt="${hotkey.itemOrSkillId}"/>
                    </a>
                </c:otherwise>
            </c:choose>
        </div>
    </c:forEach>
</div>