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

    <jsp:include page="existed_tickets.jsp" />

</div>

<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script src="js/index.js"></script>


<m:footertag/>
</body>
</html>

