<jsp:useBean id="hometasks" scope="request" type="java.util.ArrayList"/>
<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/7/20
  Time: 11:23 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@taglib prefix="ft" uri="former-tags"%>
<html>
<head>
    <title>Hometasks</title>
</head>
<body>
<h3>Menu</h3>
<br>

<%-- forming menu --%>
<% String role = (String) request.getSession().getAttribute("role"); %>
<% if (role == null)
    role = "anonym";
%>
<% switch (role) {
    case "anonym": %>
<bt:course-catalog/>
<bt:main-page/>
<%
        break;
    case "teacher":
    case "student":
%>
<bt:main-page/>
<bt:course-catalog/>
<bt:user-profile/>
<bt:sign-out/>
<%
        break;
    case "admin":
%>
<bt:main-page/>
<bt:sign-out/>
<bt:user-profile/>
<bt:create-user/>
<%
            break;
    }
%>
<br>
<h2>Hometask</h2>
<br>
<c:forEach var="hometask" items="${hometasks}">
    <ft:hometask-former hometask="${hometask}"/>
</c:forEach>
<br>
</body>
</html>
