<%@ page import="com.mishamba.project.model.Hometask" %><%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 3.10.20
  Time: 23:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="ft" uri="former-tags"%>
<%@ taglib prefix="bt" uri="button-tags"%>
<html>
<head>
    <title>Hometask</title>
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
<h3>Hometask</h3>
<br>
<jsp:useBean id="hometask" scope="request" type="com.mishamba.project.model.Hometask"/>
<ft:hometask-former hometask="${hometask}"/>
<br>
<h3>Answer</h3>
<br>
<form action="${pageContext.request.contextPath}/hometask" method="get">
    <label>
        <input type="text" name="answer">
    </label>
    <input type="hidden" name="hometask_id" value=<%= request.getParameter("hometask_id")%>>
    <input type="hidden" name="command" value="enter_hometask_answer">
    <input type="submit" value="Send answer">
</form>
</body>
</html>