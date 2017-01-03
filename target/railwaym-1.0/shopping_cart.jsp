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

<form class="payblock"  method="POST"  action="Controller?command=buyticket">
    <input type="hidden">
    <div class="trainicon"> </div>
    <div class="traintopitem">
        <div class="travelway"> Направление № ${sessionScope.ticketInShoppingCart.trainNumber}</div>
        ${sessionScope.ticketInShoppingCart.departureCity} - ${sessionScope.ticketInShoppingCart.destinationCity}
    </div>
    <div class="mainitem">
        <div class="itemj">
            <h4> Время <br> отправления </h4>
            <fmt:formatDate pattern="HH:mm" value="${sessionScope.ticketInShoppingCart.startDateTime}" />

        </div>
        <div class="itemj">
            <h4> Время <br> прибытия </h4>
            <fmt:formatDate pattern="HH:mm" value="${sessionScope.ticketInShoppingCart.endDateTime}" />
        </div>
        <div class="itemj">
            <h4> Время <br> в пути </h4>

        </div>
        <div class="itemj">
            <h4> Стоимость <br> билета </h4>
            ${sessionScope.ticketInShoppingCart.price}

        </div>

    </div>

    <div class="mainitem">
        <div class="customerblock">
            <div class="topname"> Заказчик </div>
            ${sessionScope.ticketInShoppingCart.userName} ${sessionScope.ticketInShoppingCart.userLastName}
        </div>

        <div class="customerblock" id="startdate">
            <div class="topname"> Дата отправления</div>
            <fmt:formatDate pattern="dd-MM-yyyy" value="${sessionScope.ticketInShoppingCart.startDateTime}" />
        </div>

        <div class="input-container" id="cardinfonumber">
            <input type="text" id="Card" required="required"/>
            <label for="Card">Номер банковской карты</label>
            <div class="bar"></div>
        </div>
        <div class="input-container">
            <input type="password" id="CVV" required="required"/>
            <label for="CVV">CVV</label>
            <div class="bar"></div>
        </div>

        <div class="button-container">
            <button><span>Купить билет</span></button>
        </div>
    </div>