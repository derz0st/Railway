<%-- 
    Document   : settings
    Created on : 28.11.2016, 22:53:42
    Author     : denis
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/WEB-INF/footerTag.tld" prefix="m"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Settings</title>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
        <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
        <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
          
        <div class="container">
            <jsp:include page="headerpanel.jsp" />
            
            <form class="orderform" id="settingsform" action="Controller">
                <input type="hidden" name="command" value="settingssave"/>
                <div class="input-container">
                    <input type="text" id="Firstname" name="firstname" value="${user.firstname}" required="required"/>
                    <label for="Firstname"> <fmt:message key="first_name" /> </label>
                    <div class="bar"></div>
                </div>
                <div class="input-container">
                    <input type="text" id="Lastname" name="lastname" value="${user.lastname}" required="required"/>
                        <label for="Lastname">  <fmt:message key="last_name" />  </label>
                        <div class="bar"></div>
                </div>
                <div class="input-container">
                        <input type="text" id="Email" name="email" value="${user.email}" required="required"/>
                        <label for="Email">  <fmt:message key="email" />  </label>
                        <div class="bar"></div>
                </div>
                <div class="input-container">
                    <input type="password" id="Password" name="password" value="${user.password}" required="required"/>
                        <label for="Password">  <fmt:message key="password" />  </label>
                        <div class="bar"></div>
                </div>
                <select class="minimal" name="language">
                    <option value="${language}" selected> <fmt:message key="interface_language" /> </option>
                          <option value="ru"> <fmt:message key="russian" /> </option>
                          <option value="en"> <fmt:message key="english" /> </option>
                        </select>
                <div class="button-container">
                        <button><span> <fmt:message key="save" /> </span></button>
                </div>
            </form>
	
	
        
	
        </div>

        
        <script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

        <script src="js/index.js"></script>
        
        <m:footertag/>
    </body>
</html>
