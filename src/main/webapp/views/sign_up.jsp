<%--
    Document   : sign-in
    Created on : 28.10.2014, 0:17:44
    Author     : denis
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html public>

<c:if test="${not empty sessionScope.userentity}">
    <jsp:forward page="Controller?command=findtickets" />
</c:if>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Sign In in JSP</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/meyer-reset/2.0/reset.min.css">
    <link rel='stylesheet prefetch' href='http://fonts.googleapis.com/css?family=Roboto:400,100,300,500,700,900|RobotoDraft:400,100,300,500,700,900'>
    <link rel='stylesheet prefetch' href='http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css'>
    <link rel="stylesheet" href="css/style.css">
</head>

<body>
<a href="Controller?command=findtickets">
    <div class="logo"></div>
</a>

<div class="container">

    <form class="singupform" method="POST" action="Controller?command=signupaction">
        <div class="input-container">
            <input type="text" id="Name" name="firstname" value="${entered_firstname}" required="required"/>
            <label for="Name">First Name</label>
            <div class="bar">${firstname_error}</div>
        </div>
        <div class="input-container">
            <input type="text" id="LastName" name="lastname" value="${entered_lastname}" required="required"/>
            <label for="LastName">Last Name</label>
            <div class="bar">${lastname_error}</div>
        </div>
        <div class="input-container">
            <input type="text" id="Username" name="email" value="${entered_email}" required="required"/>
            <label for="Username">Email</label>
            <div class="bar">${email_error}</div>
        </div>
        <div class="input-container">
            <input type="password" id="Password" name="password" value="${entered_password}" required="required"/>
            <label for="Password">Password</label>
            <div class="bar">${password_error}</div>
        </div>
        <div class="input-container">
            <input type="password" id="Repeat Password" name="repeatpassword" value="${entered_repeat_password}" required="required"/>
            <label for="Repeat Password">Repeat Password</label>
            <div class="bar">${repeat_password_error}</div>
        </div>


        <div class="button-container">
            <button><span>Sign up</span></button>
        </div>
    </form>

    <div class="redirect_sign_in"> <a href="Controller?command=signin"> Already registered? | Sign in</a> </div>

</div>

<script src='http://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js'></script>

<script src="js/index.js"></script>


</body>
</html>