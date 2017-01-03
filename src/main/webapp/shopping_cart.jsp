<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 03.01.17
  Time: 15:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
    <c:when test="${sessionScope.language == 'ru'}">
        <fmt:setBundle basename="langRu" />
    </c:when>
    <c:when test="${sessionScope.language == 'en'}">
        <fmt:setBundle basename="langEn" />
    </c:when>
    <c:otherwise>
        <fmt:setBundle basename="langEn" />
    </c:otherwise>
</c:choose>


<c:choose>
    <c:when test="${empty sessionScope.ticketInShoppingCart}">
        <div class="errortextblock"> <fmt:message key="error_new_ticket" /> </div>
    </c:when>
    <c:otherwise>

<form class="payblock"  method="POST"  action="Controller?command=buyticket">
    <input type="hidden">
    <div class="trainicon"> </div>
    <div class="traintopitem">
        <div class="travelway"> <fmt:message key="train_direction" /> № ${sessionScope.ticketInShoppingCart.trainNumber}</div>
        ${sessionScope.ticketInShoppingCart.departureCity} - ${sessionScope.ticketInShoppingCart.destinationCity}
    </div>
    <div class="mainitem">
        <div class="itemj">
            <h4> <fmt:message key="departure_time" /> </h4>
            <fmt:formatDate pattern="HH:mm" value="${sessionScope.ticketInShoppingCart.startDateTime}" />

        </div>
        <div class="itemj">
            <h4> <fmt:message key="arrival_time" /> </h4>
            <fmt:formatDate pattern="HH:mm" value="${sessionScope.ticketInShoppingCart.endDateTime}" />
        </div>
        <div class="itemj">
            <h4> <fmt:message key="travel_time" /> </h4>

        </div>
        <div class="itemj">
            <h4> <fmt:message key="ticket_price" /> </h4>
            ${sessionScope.ticketInShoppingCart.price}

        </div>

    </div>

    <div class="mainitem">
        <div class="customerblock">
            <div class="topname"> <fmt:message key="customer" /> </div>
            ${sessionScope.ticketInShoppingCart.userName} ${sessionScope.ticketInShoppingCart.userLastName}
        </div>

        <div class="customerblock" id="startdate">
            <div class="topname"> <fmt:message key="travel_date" /> </div>
            <fmt:formatDate pattern="dd-MM-yyyy" value="${sessionScope.ticketInShoppingCart.startDateTime}" />
        </div>

        <div class="input-container" id="cardinfonumber">
            <input type="text" id="Card" required="required"/>
            <label for="Card"><fmt:message key="card_number" /></label>
            <div class="bar"></div>
        </div>
        <div class="input-container">
            <input type="password" id="CVV" required="required"/>
            <label for="CVV">CVV</label>
            <div class="bar"></div>
        </div>

        <div class="button-container">
            <button><span><fmt:message key="buy_ticket" /></span></button>
        </div>
    </div>


    </c:otherwise>
</c:choose>