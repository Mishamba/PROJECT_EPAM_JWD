<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/24/20
  Time: 8:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="signs.sign"/>
<html>
<head>
    <title><fmt:message key="sign_in_sign"/></title>
    <style>
        h2 {
            font-size: 28px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
        }

        h3 {
            font-size: 28px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
        }

        h2 form input {
            background-color: #8aa54d;
            height: 60px;
            width: 140px;
            font-size: 20px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
            border: 2px solid rgb(76, 102, 45);
            border-radius: 5px;
            position: relative;
        }

        form input {
            background-color: #8aa54d;
            height: 60px;
            width: 140px;
            font-size: 20px;
            font-family: 'Courier New', Courier, monospace;
            font-weight: bold;
            color: #051C0E;
            border: 2px solid rgb(76, 102, 45);
            border-radius: 5px;
            position: relative;
        }
    </style>
</head>
<body>
<h3><fmt:message key="menu_sign"/></h3>
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

<form action="${pageContext.request.contextPath}/university">
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
    <input type="submit" value=<fmt:message key="sign_in_sign"/>>
</form>
<br>
<br>
<bt:change-locale/>
</body>
</html>
