<%-- 
    Document   : Paginating
    Created on : Jul 4, 2011, 12:35:58 AM
    Author     : Wonka
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <ul>
            <c:forEach items="${accounts}" var="account">
                <li>${account.name}</li>
            </c:forEach>
        </ul>

        <a href="./LazyListingController?action=previous">Previous page</a>
            <c:forEach items="${pages}" var="page">
                <a href="./LazyListingController?action=goToPage&pageNum=${page}">${page}</a> | 
            </c:forEach>
        <a href="./LazyListingController?action=next">Next page</a> | 
    </body>
</html>
