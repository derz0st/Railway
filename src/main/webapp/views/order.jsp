<%-- 
    Document   : order
    Created on : 24.11.2016, 13:47:59
    Author     : denis
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/footerTag.tld" prefix="m"%>
<%@ taglib uri="/WEB-INF/timeTag.tld" prefix="calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
    <title>Find tickets</title>
    <script type="text/javascript" src="js/jquery-1.11.1.min.js"></script>
    <script type="text/javascript" src="js/moment-with-locales.min.js"></script>
    <script type="text/javascript" src="js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/bootstrap-datetimepicker.min.js"></script>
    <link rel="stylesheet" href="css/bootstrap.min.css" />
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css" />
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
    <link rel="stylesheet" href="css/style.css">

</head>

<body>

<div class="container">
    <jsp:include page="headerpanel.jsp" />

    <form class="orderform" method="POST" action="Controller?command=order">
        <div class="input-container">
            <input type="text" id="From" name="departurecity" value="<c:choose><c:when test="${not empty departurecity}">${departurecity}</c:when></c:choose>" required="required"/>
            <label for="From"><fmt:message key="from" /></label>
            <div class="bar">${departure_error}</div>
        </div>
        <div class="input-container">
            <input type="text" id="To" name="destinationcity" value="<c:choose><c:when test="${not empty destinationcity}">${destinationcity}</c:when></c:choose>" required="required"/>
            <label for="To"><fmt:message key="to" /></label>
            <div class="bar">${destination_error}</div>
        </div>



        <div class="datepicker-container">
            <input type="text" class="form-control" name="traveldate" value="${traveldate}" placeholder="<fmt:message key="travel_date" />" id="datetimepicker2">
            <div class="date_error">${date_error}</div>
        </div>


        <div class="button-container">
            <button><span><fmt:message key="find_tickets" /></span></button>
        </div>
    </form>
    <center>
            <span>
                <br>
                ${error}
            </span>
    </center>
    <c:forEach var="train" items="${trains}">
        <a href="#" onclick="document.getElementById('${train.id}').submit(); return false;">
        <form id="${train.id}" action="Controller?command=pay&trainid=${train.id}" method="post">
            <input type="hidden" name="price" value="${train.price}">
            <input type="hidden" name="traveldate" value="${traveldate}">
            <input type="hidden" name="departurecity" value="${departurecity}">
            <input type="hidden" name="destinationcity" value="${destinationcity}">
            <input type="hidden" name="arrivaltime" value="<fmt:formatDate pattern="HH:mm" value="${train.arrivalTime}" />">
            <input type="hidden" name="departuretime" value="<fmt:formatDate pattern="HH:mm" value="${train.departureTime}" />">
            <div class="ticketblock">

                <div class="trainicon"> </div>

                <div class="traintopitem">
                    <div class="travelway"> <fmt:message key="train_direction" /> № <c:out value="${train.id}"/>
                        — ${train.trainTicketsOnDate.busySeats} / ${train.trainTicketsOnDate.totalSeats}
                    </div>
                        ${train.departureCity} – ${train.arrivalCity}
                </div>

                <div class="mainitem">
                    <div class="itemj">
                        <h4> <fmt:message key="departure_time" /> </h4>
                        <fmt:formatDate pattern="HH:mm" value="${train.departureTime}" />
                    </div>
                    <div class="itemj">
                        <h4> <fmt:message key="arrival_time" /> </h4>
                        <fmt:formatDate pattern="HH:mm" value="${train.arrivalTime}" />
                    </div>
                    <div class="itemj">
                        <h4> <fmt:message key="travel_time" /> </h4>
                        <calendar:timetag date="${train.travelTime}" />
                    </div>
                    <div class="itemj">
                        <h4> <fmt:message key="ticket_price" /> </h4>
                            ${train.price}
                    </div>
                </div>


                <div class="stationblock">
                    <div class="travelplan"><fmt:message key="route_of_the_train" /> </div>
                    <c:forEach var="station" end="0" items="${train.stations}">
                        <div class="stationitem">
                            <div class="stationtime">  </div> <div class="station-round"></div>
                            <div class="stationtime"> <fmt:formatDate pattern="HH:mm" value="${station.departureTime}" /> </div>
                            <c:choose>
                                <c:when test="${station.name == departurecity}">
                                    <h4> — &nbsp;&nbsp; <div class="stationhover"> ${station.name} </div> </h4>
                                </c:when>
                                <c:otherwise>
                                    <h4> — &nbsp;&nbsp; ${station.name} </h4>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </c:forEach>
                    <c:forEach var="station" begin="1" items="${train.stations}">
                    <div class="stationitem">

                        <div class="stationtime"> <fmt:formatDate pattern="HH:mm" value="${station.arrivalTime}" /> </div> <div class="station-round"><div class="vertical"> </div></div>
                        <div class="stationtime"> <fmt:formatDate pattern="HH:mm" value="${station.departureTime}" /> </div>
                        <c:choose>
                            <c:when test="${station.name == departurecity || station.name == destinationcity}">
                                <h4> — &nbsp;&nbsp; <div class="stationhover"> ${station.name} </div> </h4>
                            </c:when>
                            <c:otherwise>
                                <h4> — &nbsp;&nbsp; ${station.name} </h4>
                            </c:otherwise>
                        </c:choose>

                    </div>
                    </c:forEach>
                    <%--<div class="stationitem">--%>
                        <%--<div class="stationtime"> 07:21 </div> <div class="station-round"> <div class="vertical"> </div> </div>--%>
                        <%--<div class="stationtime"> 09:28 </div>--%>
                        <%--<h4> — &nbsp; Suzemka </h4>--%>
                    <%--</div>--%>

                </div>



            </div>


        </form>
        </a>

    </c:forEach>

</div>

<m:footertag/>


<script src="js/index.js"></script>

<script type="text/javascript">
    $(function () {
        $('#datetimepicker2').datetimepicker({language: 'ru', format: 'DD-MM-YYYY hh:mm', startDate: '28/12/2016', endDate: '29/12/2016'});
    });
</script>


</body>
</html>
