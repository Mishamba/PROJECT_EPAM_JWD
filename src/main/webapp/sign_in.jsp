<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/24/20
  Time: 8:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<html>
<head>
    <title>Sign in</title>
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

<form action="/PROJECT_EPAM_JWD_war/university">
    <label>
        <input type="email" name="email">
    </label>
    <br>
    <label>
        <input type="password" name="password">
    </label>
    <br>
    <input type="hidden" name="command" value="sign_in_check">
    <br>
    <input type="submit" value="Sign in">
</form>
</body>
</html>
