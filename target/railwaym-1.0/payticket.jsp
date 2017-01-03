<%-- 
    Document   : payticket
    Created on : 24.11.2016, 15:32:18
    Author     : denis
--%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/footerTag.tld" prefix="m"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Sign In</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
        <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        <div class="container">
	<a href="Controller?command=findtickets">
            <div class="logo"></div>
        </a>
        
        
        
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
                            <input type="text" id="CVV" required="required"/>
                            <label for="CVV">CVV</label>
                            <div class="bar"></div>
                        </div>

                        <div class="button-container">
                    <button><span>Купить билет</span></button>
                </div>
            </div>
		
		
	    </form>
        
        
        
        
         <m:footertag/>
    </body>
</html>
