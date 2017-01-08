<%-- 
    Document   : order
    Created on : 24.11.2016, 13:47:59
    Author     : denis
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/footerTag.tld" prefix="m"%>
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
        <title>Administrate</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
        <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
        
        <div class="container">
            <jsp:include page="headerpanel.jsp" />
                

                <c:forEach var="user" items="${users}">
                <a href="Controller?command=customertickets&userid=${user.id}">
                <div class="usualuser">
                    <div class="usericon"></div>
                    <div class="traintopitemname"> 
                            <div class="travelway"> Фамилия имя, id = ${user.id} </div>
                            ${user.lastname} ${user.firstname}
                    </div>	
                    <div class="traintopemail"> 
                            <div class="travelway"> E-mail </div>
                            ${user.email}
                    </div>
                    <a href="Controller?command=blockuser&userid=${user.id}">
                        <c:choose>
                            <c:when test="${user.isBlocked == 1}">
                                <div class="blocked_block" id="unlock"></div>
                            </c:when>
                            <c:otherwise>
                                <div class="blocked_block"></div>
                            </c:otherwise>
                        </c:choose>

                    </a>

                </div>
                </a>
                    
                    
                </c:forEach>

        </div>

  <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

    <script src="js/index.js"></script>
	

    <m:footertag/>
    </body>
</html>
