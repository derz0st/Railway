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
    <title>Add tickets on date</title>
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

    <form class="addnewtrainform" method="post" action="Controller?command=addnewticketsondate" style="height: 250px;">
        <div class="input-container">


        </div>


        <div class="datepicker-container">
            <input type="text" class="form-control" name="date" placeholder="Дата" id="datetimepicker2">
            <div class="date_error"> </div>
        </div>



        <div class="simple-input">
            <input type="text" name="trainnumber" placeholder="№ поезда" required="required"/>
        </div>

        <div class="simple-input">
            <input type="text" name="totalseats" placeholder="Количество" required="required"/>
        </div>


        <div class="bar"> </div>



        <div class="button-container">
            <button><span>Добавить станцию</span></button>
        </div>
    </form>
    <center>
            <span>
                <br>
                ${error}
            </span>
    </center>


</div>

<m:footertag/>


<script src="js/index.js"></script>

<script type="text/javascript">
    $(function () {
        $('#datetimepicker2').datetimepicker({language: 'ru', format: 'DD-MM-YYYY', autoclose: true });
    });
</script>




</body>
</html>
