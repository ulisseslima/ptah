<%--Created on : 26/02/2010, 01:39:29
    Author     : Wonka--%>
<%@tag description="This tag creates an aside element on the right of the page, this element contains a list of items." pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="dvl" tagdir="/WEB-INF/tags/" %>
<%@taglib prefix="char" tagdir="/WEB-INF/tags/char/" %>
<%-- The list of normal or fragment attributes can be specified here: --%>
<%@attribute name="onclickHandlers" required="true" type="java.lang.Boolean" rtexprvalue="true"
             description="Whether onclick action handlers will be available."%>
<%@attribute name="items" required="true" type="java.util.List" rtexprvalue="true"
             description="The Items List."%>
<%@attribute name="key" required="true" rtexprvalue="true"
             description="The parent element name. eg: Storage."%>
<%@attribute name="isInventory" required="false" type="java.lang.Boolean" rtexprvalue="true"
             description="Defines whether this list will be an inventory list." %>
<%-- any content can be specified here --%>
<c:if test="${items != null}">
    <c:choose>
        <c:when test="${onclickHandlers}">
            <aside>
                <dvl:aside titleKey="${key}">
                    <input type="search" onkeyup="filter(this.value)" style="width: 90%" placeholder="Filter Storage">
                    <div id="storage">
                        <c:forEach var="item" items="${items}">
                            <div id="${item.id}_${item.detailedName}" class="round storage-item${item.identified? "":" unidentified"}" title="${item.title}">
                                <span class="round item-amount" onclick="box(this.parentNode, this.innerHTML)">${item.amount}</span>
                                <a target="_new" href="http://ragnadb.com.br/item.php?id=${item.itemId}">
                                    <c:choose >
                                        <c:when test="${item.itemId > 4000 and item.itemId < 4700}">
                                            <img src="http://ragnadb.com.br/img/small/carta.gif" alt="${item.itemId}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="http://www.ragnadb.com.br/img/small/${item.itemId}.gif" alt="${item.itemId}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                                <c:choose>
                                    <c:when test="${item.identified}">
                                        <span class="item-name">${item.detailedName}</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="item-name" id="${item.id}_unidentified"
                                              onclick="requestIdentification(this)">${item.detailedName}</span>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:forEach>
                    </div>
                </dvl:aside>
            </aside>
        </c:when>
        <c:otherwise>
            <aside>
                <dvl:aside titleKey="${key}">
                    <input type="search" onkeyup="filter(this.value)" style="width: 90%" placeholder="Filter Items">
                    <div id="storage">
                        <c:forEach var="item" items="${items}">
                            <div id="${item.id}_${item.detailedName}" class="round storage-item${item.identified? "":" unidentified"}" title="${item.title}">
                                <span class="round item-amount">
                                    <c:choose>
                                        <c:when test="${isInventory}">
                                            <c:choose>
                                                <c:when test="${item.equipLocation != null}">
                                                    <char:equip location="${item.equipLocation}"/>
                                                </c:when>
                                                <c:otherwise>
                                                    ${item.amount}
                                                </c:otherwise>
                                            </c:choose>
                                        </c:when>
                                        <c:otherwise>
                                            ${item.amount}
                                        </c:otherwise>
                                    </c:choose>
                                </span>
                                <a target="_new" href="http://ragnadb.com.br/item.php?id=${item.itemId}">
                                    <c:choose >
                                        <c:when test="${item.itemId > 4000 and item.itemId < 4700}">
                                            <img src="http://ragnadb.com.br/img/small/carta.gif" alt="${item.itemId}"/>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="http://www.ragnadb.com.br/img/small/${item.itemId}.gif" alt="${item.itemId}"/>
                                        </c:otherwise>
                                    </c:choose>
                                </a>
                                <span class="item-name">${item.detailedName}</span>
                            </div>
                        </c:forEach>
                    </div>
                </dvl:aside>
            </aside>
        </c:otherwise>
    </c:choose>
</c:if>