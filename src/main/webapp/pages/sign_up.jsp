<%--
  Created by IntelliJ IDEA.
  User: mishamba
  Date: 10/4/20
  Time: 5:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="bt" uri="button-tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="signs.sign"/>
<html>
<head>
    <title><fmt:message key="sign_up_sign"/></title>
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
<form action="${pageContext.request.contextPath}/university" method="post">
<p><fmt:message key="first_name_sign"/></p>
    <label>
        <input type="text" name="first_name">
    </label>
    <br>
<p><fmt:message key="last_name_sign"/></p>
    <label>
        <input type="text" name="last_name">
    </label>
<p><fmt:message key="email_sign"/></p>
<label>
    <input type="email" name="email">
</label>
<br>
<p><fmt:message key="password_sign"/></p>
<label>
    <input type="password" name="password">
</label>
<br>
<p><fmt:message key="birthday_sign"/>(enter like YYYY-MM-DD)</p>
<label>
    <input type="text" name="birthday">
</label>
<br>
    <% if (request.getSession().getAttribute("role").equals("admin")) {%>
        <p><fmt:message key="role_definer_sign"/></p>
        <br>
        <label>
            <input type="text" name="role">
        </label>
        <br>
    <%} else {%>
        <input type="hidden" name="role" value="student">
        <br>
    <%}%>
<br>
    <input type="hidden" name="command" value="sign_up_check">
    <input type="submit" value=<fmt:message key="sign_up_sign"/>>
</form>
<br>
<br>
<bt:change-locale/>
</body>
</html>
