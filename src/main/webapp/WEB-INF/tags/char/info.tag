<%--Created on : 05/04/2010, 16:16:59
    Author     : Wonka--%>
<%@tag description="Shows generic info about this character, such as stats and relatives." pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>

<%@attribute name="isPrivate" required="false" rtexprvalue="true" type="java.lang.Boolean"
             description="Whether this info tag will include private information."%>

<h4>Info</h4>
<div class="center section">
    <jsp:doBody/>
    <dvl:boxy label="karma" test="${char.karma > 0}">${char.karma}</dvl:boxy>
    <dvl:boxy label="char.fame" test="${char.fame > 0}">${char.fame}</dvl:boxy>
    <dvl:boxy label="char.map" test="${not empty char.lastMap}">${char.lastMap}</dvl:boxy>
    <c:if test="${isPrivate}">
        <dvl:boxy label="char.saveMap" test="${not empty char.saveMap}">${char.saveMap}</dvl:boxy>
        <dvl:boxy label="zeny" test="${char.zeny > 0}">${char.zeny}</dvl:boxy>
        <br>
        <dvl:boxy label="char.str" test="${char.str > 0}">${char.str} +${char.strBonus}</dvl:boxy>
        <dvl:boxy label="char.agi" test="${char.agi > 0}">${char.agi} +${char.agiBonus}</dvl:boxy>
        <dvl:boxy label="char.vit" test="${char.vit > 0}">${char.vit} +${char.vitBonus}</dvl:boxy>
        <dvl:boxy label="char.int" test="${char.int > 0}">${char.int} +${char.intBonus}</dvl:boxy>
        <dvl:boxy label="char.dex" test="${char.dex > 0}">${char.dex} +${char.dexBonus}</dvl:boxy>
        <dvl:boxy label="char.luk" test="${char.luk > 0}">${char.luk} +${char.lukBonus}</dvl:boxy>
    </c:if>
    <br>
    <dvl:boxy label="char.father" test="${char.father != null}">${char.father.name}</dvl:boxy>
    <dvl:boxy label="char.mother" test="${char.mother != null}">${char.mother.name}</dvl:boxy>
    <dvl:boxy label="char.child" test="${char.child != null}">
        <a href="./View?id=${char.child.nameURL}">${char.child.name}</a>
    </dvl:boxy>
    <dvl:boxy label="char.partner" test="${char.partner != null}">
        <a href="./View?id=${char.partner.nameURL}">${char.partner.name}</a>
    </dvl:boxy>
    <dvl:boxy label="char.homunculus" test="${char.homunculus != null}" title="Class: ${char.homunculus.className} | lv ${char.homunculus.level} | ${char.homunculus.intimacy} | ${char.homunculus.hunger}">
        ${char.homunculus.name}
    </dvl:boxy>
    <dvl:boxy label="char.pet" test="${char.pet != null}" title="Intimacy: ${char.pet.intimacy} | Hunger: ${char.pet.hungerLevel}">
        ${char.pet.name}
    </dvl:boxy>
</div>