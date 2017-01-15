<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 02.01.17
  Time: 17:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
    <c:when test="${sessionScope.language == 'ru'}">
        <fmt:setBundle basename="locale/lang_RU" />
    </c:when>
    <c:when test="${sessionScope.language == 'en'}">
        <fmt:setBundle basename="locale/lang_EN" />
    </c:when>
    <c:otherwise>
        <fmt:setBundle basename="locale/lang_EN" />
    </c:otherwise>
</c:choose>

<a href="Controller?command=findtickets">
    <div class="logo"></div>
</a>

<div class="usermenu">

    <c:choose>

        <c:when test="${sessionScope.userentity.adminid == 1}">
            <a href="Controller?command=ticketsonreturn" class="usermenupoint" id="myticketsicon"> <fmt:message key="return_tickets" /> </a>
            <a href="Controller?command=administration" class="usermenupoint" id="administrate"> <fmt:message key="administrate" /> </a>

        </c:when>

        <c:otherwise>
            <c:choose>
                <c:when test="${not empty sessionScope.ticketInShoppingCart}">
                    <a href="Controller?command=usertickets&status=actual" class="usermenupoint" id="myticketsiconnew"> <fmt:message key="my_tickets" /> </a>
                </c:when>
                <c:otherwise>
                    <a href="Controller?command=usertickets&status=actual" class="usermenupoint" id="myticketsicon"> <fmt:message key="my_tickets" /> </a>
                </c:otherwise>
            </c:choose>
        </c:otherwise>
    </c:choose>

    <a href="Controller?command=settings" class="usermenupoint" id="settings"> <fmt:message key="settings" /> </a>
    <a href="Controller?command=logout" class="usermenupoint" id="logout"> <fmt:message key="logout" /> </a>

</div>
