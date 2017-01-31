<%--
  Created by IntelliJ IDEA.
  User: denis
  Date: 03.01.17
  Time: 14:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

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

<c:if test="${empty tickets}">
    <div class="errortextblock"> <fmt:message key="error_actual_tickets" /> </div>
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

                <h5> archive </h5>


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

            <a href="Controller?command=deleteticket&status=archive&ticketid=${ticket.id}">
                <div class="delete_ticket"></div>
            </a>


        </div>


    </form>

</c:forEach>