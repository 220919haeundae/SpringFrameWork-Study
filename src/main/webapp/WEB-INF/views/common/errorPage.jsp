<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ERROR</title>
</head>
<body>
    <%-- header 포함 --%>
    <jsp:include page="header.jsp" />

    <br>
        <div align="center">
            <img src="https://img.freepik.com/free-photo/red-button-with-admiration_1156-659.jpg?w=1060&t=st=1712604252~exp=1712604852~hmac=8b44b1d2e8d28f55d36b1c150eb6e752213a1cf867188ae093fa0f191189f6a5" alt="ERROR" width="350">
            <br><br>
            <h1 style="font-weight: bold;">${ errorMsg }</h1>
        </div>
    <br>

    <%-- footer 포함 --%>
    <jsp:include page="footer.jsp" />
</body>
</html>