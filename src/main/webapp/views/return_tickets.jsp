<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 04.01.17
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/footerTag.tld" prefix="m"%>
<%@ taglib uri="/WEB-INF/timeTag.tld" prefix="time"%>
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

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>My tickets</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>

<div class="container">

    <jsp:include page="headerpanel.jsp" />

    <c:if test="${empty tickets}">
        <div class="errortextblock"> <fmt:message key="error_return_tickets" /> </div>
    </c:if>

    <c:forEach var="ticket" items="${tickets}">

        <form class="payblock"  method="POST"  action="Controller?command=buyticket">
            <input type="hidden">
            <div class="trainicon"> </div>
            <div class="traintopitem">
                <div class="travelway"> <fmt:message key="train_direction" /> № ${ticket.trainNumber}</div>
                    ${ticket.departureCity} - ${ticket.destinationCity}
            </div>
            <div class="mainitem">
                <div class="itemj">
                    <h4> <fmt:message key="departure_time" /> </h4>
                    <fmt:formatDate pattern="HH:mm" value="${ticket.startDateTime}" />

                </div>
                <div class="itemj">
                    <h4> <fmt:message key="arrival_time" /> </h4>
                    <fmt:formatDate pattern="HH:mm" value="${ticket.endDateTime}" />
                </div>

                <div class="itemj">
                    <h4> <fmt:message key="ticket_price" /> </h4>
                        ${ticket.price}

                </div>
                <div class="itemj">
                    <h4> <fmt:message key="ticket_status" /> </h4>
                    <h5> waiting </h5>

                </div>

            </div>

            <div class="mainitem">
                <div class="customerblock">
                    <div class="topname"> <fmt:message key="customer" /> </div>
                        ${ticket.userName} ${ticket.userLastName}
                </div>

                <div class="customerblock" id="startdate">
                    <div class="topname"> <fmt:message key="travel_date" /></div>
                    <fmt:formatDate pattern="dd-MM-yyyy" value="${ticket.startDateTime}" />
                </div>

                <a href="Controller?command=acceptreturn&ticketid=${ticket.id}">
                    <div class="return_ticket"></div>
                </a>

            </div>


        </form>

    </c:forEach>

</div>

<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script src="js/index.js"></script>


<m:footertag/>
</body>
</html>

